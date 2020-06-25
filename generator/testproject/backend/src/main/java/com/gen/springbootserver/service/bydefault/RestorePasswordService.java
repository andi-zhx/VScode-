package com.gen.springbootserver.service.bydefault;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import com.gen.springbootserver.dto.errors.CommonApiException;
import com.gen.springbootserver.dto.errors.CommonHttpException;
import com.gen.springbootserver.dto.resetpassword.RestorePasswordDTO;
import com.gen.springbootserver.dto.user.ChangePasswordRequest;
import com.gen.springbootserver.mybatis.dao.RestorePasswordMapper;
import com.gen.springbootserver.mybatis.model.RestorePassword;
import com.gen.springbootserver.mybatis.model.RestorePasswordExample;

@Service
public class RestorePasswordService {
    @Autowired
    RestorePasswordMapper restorePasswordMapper;
    @Autowired
    UserService userService;

    // #region
    @Transactional
    public RestorePassword create(RestorePassword restorePassword) throws CommonApiException {
        restorePasswordMapper.insert(restorePassword);
        try {
            restorePassword = get(restorePassword.getId());
        } catch (Exception e) {
        }
        return restorePassword;
    }

    @Transactional
    public RestorePassword update(Long id, RestorePassword restorePassword) throws CommonApiException {
        RestorePassword exist = restorePasswordMapper.selectByPrimaryKey(id);
        if (exist == null) {
            throw new CommonApiException("this RestorePassword not exixt");
        }
        restorePasswordMapper.updateByPrimaryKey(restorePassword);
        return get(restorePassword.getId());
    }

    @Transactional
    public void delete(Long id) {
        restorePasswordMapper.deleteByPrimaryKey(id);
    }

    public RestorePassword get(Long id) throws CommonApiException {
        RestorePassword restorePassword = restorePasswordMapper.selectByPrimaryKey(id);
        if (restorePassword == null) {
            throw new CommonApiException("this RestorePassword not exixt");
        }
        return restorePassword;
    }

    public List<RestorePassword> getList(RestorePasswordExample example) {
        List<RestorePassword> result = restorePasswordMapper.selectByExample(example);
        return result;
    }

    public Long getListCount(RestorePasswordExample example) {
        Long result = restorePasswordMapper.countByExample(example);
        return result;
    }

    // #endregion
    public RestorePassword findByToken(String token) {
        RestorePasswordExample example = new RestorePasswordExample();
        example.createCriteria().andTokenEqualTo(token);
        List<RestorePassword> result = restorePasswordMapper.selectByExample(example);
        if (result.size() == 1) {
            return restorePasswordMapper.selectByExample(example).get(0);
        } else {
            throw new CommonHttpException("Reset password request wasn't performed or already expired",
                    HttpStatus.FORBIDDEN);
        }
    }

    public void insertRestorePassword(RestorePassword restorePassword) {
        restorePasswordMapper.insert(restorePassword);
    }

    public void deleteExpiredRestorePasswordTokens(LocalDateTime localDateTime) {
        RestorePasswordExample example = new RestorePasswordExample();
        example.createCriteria().andExpiresinLessThan(localDateTime);
        restorePasswordMapper.deleteByExample(example);
    }

    public void restorePassword(RestorePasswordDTO restorePasswordDTO) {
        RestorePassword restorePassword = findByToken(restorePasswordDTO.getToken());
        changePassword(restorePasswordDTO, restorePassword);
        restorePasswordMapper.deleteByPrimaryKey(restorePassword.getId());
    }

    private void changePassword(RestorePasswordDTO restorePasswordDTO, RestorePassword restorePassword) {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setUser(userService.getUserbyUserid(restorePassword.getUserId()));
        changePasswordRequest.setPassword(restorePasswordDTO.getNewPassword());
        userService.changePassword(changePasswordRequest);
    }
}
