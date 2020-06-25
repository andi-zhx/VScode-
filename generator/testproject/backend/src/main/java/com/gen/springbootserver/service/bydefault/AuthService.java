package com.gen.springbootserver.service.bydefault;

import com.gen.springbootserver.dto.authentication.LoginDTO;
import com.gen.springbootserver.dto.authentication.RefreshTokenDTO;
import com.gen.springbootserver.dto.authentication.SignUpDTO;
import com.gen.springbootserver.dto.authentication.Tokens;
import com.gen.springbootserver.dto.errors.CommonApiException;
import com.gen.springbootserver.dto.errors.CommonHttpException;
import com.gen.springbootserver.mybatis.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UserService userService;
    @Autowired
    SettingsService settingsService;
    private TokenService tokenService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    public Tokens register(SignUpDTO signUpDTO) throws CommonHttpException {
        try {
            User user = userService.register(signUpDTO);
            return createToken(user);
        } catch (CommonApiException exception) {
            throw new CommonHttpException("Email is invalid or already taken", HttpStatus.CONFLICT);
        }
    }

    public Tokens login(LoginDTO loginDTO) throws CommonHttpException {
        try {
            Authentication authentication = createAuthentication(loginDTO);
            GGUserDetailsService.GGUserDetails userDetails = (GGUserDetailsService.GGUserDetails) authenticationManager
                    .authenticate(authentication).getPrincipal();
            User user = userDetails.getUser();
            user.setSettings(settingsService.getSettingBySettingsId(user.getSettingsId()));
            return createToken(user);
        } catch (AuthenticationException exception) {
            throw new CommonHttpException("Incorrect email or password", HttpStatus.FORBIDDEN);
        }
    }

    public Tokens refreshToken(RefreshTokenDTO refreshTokenDTO) throws CommonHttpException {
        try {
            String email = tokenService.getEmailFromRefreshToken(refreshTokenDTO.getTokens().getRefreshToken());
            User user = userService.findByEmail(email);
            user.setSettings(settingsService.getSettingBySettingsId(user.getSettingsId()));
            return createToken(user);
        } catch (Exception e) {
            throw new CommonHttpException(HttpStatus.FORBIDDEN);
        }
    }

    private Authentication createAuthentication(LoginDTO loginDTO) {
        return new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
    }

    private Tokens createToken(User user) {
        return tokenService.createToken(user);
    }
}