package com.gen.springbootserver.dto.user;

import com.gen.springbootserver.mybatis.model.User;
import com.gen.springbootserver.service.bydefault.*;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserContextHolder {

    private UserContextHolder() {
    }

    public static User getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GGUserDetailsService.GGUserDetails userDetails = (GGUserDetailsService.GGUserDetails) principal;
        return userDetails.getUser();
    }
}
