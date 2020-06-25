package com.gen.springbootserver.service.bydefault;

import java.util.List;

import com.gen.springbootserver.dto.bydefault.SettingsDTO;
import com.gen.springbootserver.dto.errors.CommonApiException;
import com.gen.springbootserver.dto.errors.CommonHttpException;
import com.gen.springbootserver.dto.user.UserContextHolder;
import com.gen.springbootserver.mybatis.dao.SettingsMapper;
import com.gen.springbootserver.mybatis.model.Settings;
import com.gen.springbootserver.mybatis.model.SettingsExample;
import com.gen.springbootserver.mybatis.model.User;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SettingsService {

    @Autowired
    SettingsMapper settingsMapper;
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;

    // #region
    @Transactional
    public Settings create(Settings settings) throws CommonApiException {
        settingsMapper.insert(settings);
        try {
            settings = get(settings.getId());
        } catch (Exception e) {
        }
        return settings;
    }

    @Transactional
    public Settings update(Long id, Settings settings) throws CommonApiException {
        Settings exist = settingsMapper.selectByPrimaryKey(id);
        if (exist == null) {
            throw new CommonApiException("this Settings not exixt");
        }
        settingsMapper.updateByPrimaryKey(settings);
        return get(settings.getId());
    }

    @Transactional
    public void delete(Long id) {
        settingsMapper.deleteByPrimaryKey(id);
    }

    public Settings get(Long id) throws CommonApiException {
        Settings settings = settingsMapper.selectByPrimaryKey(id);
        if (settings == null) {
            throw new CommonApiException("this Settings not exixt");
        }
        return settings;
    }

    public List<Settings> getList(SettingsExample example) {
        List<Settings> result = settingsMapper.selectByExample(example);
        return result;
    }

    public Long getListCount(SettingsExample example) {
        Long result = settingsMapper.countByExample(example);
        return result;
    }
    // #endregion

    public Settings getSettingBySettingsId(Long settings_id) {
        return settingsMapper.selectByPrimaryKey(settings_id);

    }

    public Settings getSettingsByUserId(Long userid) {
        Long settingsid = userService.getSettingsIdByUserId(userid);
        Settings result = new Settings();
        result = getSettingBySettingsId(settingsid);
        if (result.getId() != null) {
            return getSettingBySettingsId(settingsid);
        } else {

            throw new CommonHttpException("Setting with id: " + settingsid + " not found", HttpStatus.NOT_FOUND);
        }
    }

    public SettingsDTO getCurrentSettings() {
        User currentUser = UserContextHolder.getUser();
        Long id = currentUser.getId();
        Settings settings = getSettingsByUserId(id);
        return modelMapper.map(settings, SettingsDTO.class);
    }

    public SettingsDTO updateCurrentSettings(SettingsDTO settingsDTO) {
        User user = UserContextHolder.getUser();
        Long userid = user.getId();
        return updateSettingsByUserId(userid, settingsDTO.getThemeName());
    }

    public SettingsDTO updateSettingsByUserId(Long userid, String themeName) {
        Long settingsId = getsettingsidbyThemeName(themeName);
        SettingsDTO settingsDTO = new SettingsDTO();
        if (settingsId == null) {
            new CommonHttpException("Setting with ThemeName: " + themeName + " not found", HttpStatus.NOT_FOUND);
        } else {
            userService.updateUserSettingid(userid, settingsId);
        }
        settingsDTO.setThemeName(themeName);
        return settingsDTO;
    }

    private Long getsettingsidbyThemeName(String themeName) {
        SettingsExample example = new SettingsExample();
        example.createCriteria().andThemeNameEqualTo(themeName);
        Settings result = settingsMapper.selectByExample(example).get(0);
        return result.getId();
    }
}