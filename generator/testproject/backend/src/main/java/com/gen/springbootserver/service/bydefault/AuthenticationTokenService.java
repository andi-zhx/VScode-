package com.gen.springbootserver.service.bydefault;

import com.gen.springbootserver.config.GgProperties;
import com.gen.springbootserver.dto.authentication.AuthenticationToken;
import com.gen.springbootserver.dto.errors.TokenValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationTokenService {

    private GgProperties properties;

    @Autowired
    private AuthenticationTokenService(GgProperties properties) {
        this.properties = properties;
    }

    public AuthenticationToken createToken(String token) throws TokenValidationException {
        return new AuthenticationToken(token, properties.getJwt().getAccessTokenSecretKey());
    }
}