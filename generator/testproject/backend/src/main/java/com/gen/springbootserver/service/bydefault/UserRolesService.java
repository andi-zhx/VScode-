package com.gen.springbootserver.service.bydefault;

import java.util.List;

import com.gen.springbootserver.dto.errors.CommonApiException;
import com.gen.springbootserver.mybatis.dao.*;
import com.gen.springbootserver.mybatis.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRolesService {
    @Autowired

    UserRolesMapper mapper;

    @Transactional
    public UserRoles create(UserRoles userRoles) throws CommonApiException {
        mapper.insert(userRoles);
        return userRoles;
    }

   
}