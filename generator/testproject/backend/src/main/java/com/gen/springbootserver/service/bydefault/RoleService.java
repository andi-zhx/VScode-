package com.gen.springbootserver.service.bydefault;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.gen.springbootserver.dto.errors.CommonApiException;
import com.gen.springbootserver.mybatis.dao.RoleMapper;
import com.gen.springbootserver.mybatis.dao.UserRolesMapper;
import com.gen.springbootserver.mybatis.model.Role;
import com.gen.springbootserver.mybatis.model.RoleExample;
import com.gen.springbootserver.mybatis.model.UserRoles;
import com.gen.springbootserver.mybatis.model.UserRolesExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    UserRolesMapper userRolesMapper;

    // #region
    @Transactional
    public Role create(Role role) throws CommonApiException {
        roleMapper.insert(role);
        try {
            role = get(role.getId());
        } catch (Exception e) {
        }
        return role;
    }

    @Transactional
    public Role update(Long id, Role role) throws CommonApiException {
        Role exist = roleMapper.selectByPrimaryKey(id);
        if (exist == null) {
            throw new CommonApiException("this Role not exixt");
        }
        roleMapper.updateByPrimaryKey(role);
        return get(role.getId());
    }

    @Transactional
    public void delete(Long id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    public Role get(Long id) throws CommonApiException {
        Role role = roleMapper.selectByPrimaryKey(id);
        if (role == null) {
            throw new CommonApiException("this Role not exixt");
        }
        return role;
    }

    public List<Role> getList(RoleExample example) {
        List<Role> result = roleMapper.selectByExample(example);
        return result;
    }

    public Long getListCount(RoleExample example) {
        Long result = roleMapper.countByExample(example);
        return result;
    }

    // #endregion
    public Set<Role> getUserRoleByUserId(Long userId) {
        UserRolesExample userRolesExample = new UserRolesExample();
        userRolesExample.createCriteria().andUserIdEqualTo(userId);
        List<Long> roleid = userRolesMapper.selectByExample(userRolesExample).stream()
                .map(userrole -> userrole.getRoleId()).collect(Collectors.toList());
        RoleExample example = new RoleExample();
        example.createCriteria().andIdIn(roleid);
        return new HashSet<Role>(roleMapper.selectByExample(example));
    }

    public Role setDefaultRoleByuserId(Long userId) {
        UserRoles userrole = new UserRoles();
        userrole.setUserId(userId);
        userrole.setRoleId(getDefaultRole().getId());
        userRolesMapper.insert(userrole);
        return getDefaultRole();
    }

    public void deleteByuserid(Long id) {
        UserRolesExample example = new UserRolesExample();
        example.createCriteria().andUserIdEqualTo(id);
        userRolesMapper.deleteByExample(example);
    }

    private Role getDefaultRole() {
        RoleExample example = new RoleExample();
        example.createCriteria().andIsDefaultEqualTo(true);
        return roleMapper.selectByExample(example).get(0);
    }

}