package com.gen.springbootserver.service.bydefault;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gen.springbootserver.config.GgProperties;
import com.gen.springbootserver.dto.errors.CommonHttpException;
import com.gen.springbootserver.dto.resetpassword.RequestPasswordDTO;
import com.gen.springbootserver.dto.resetpassword.RestorePasswordTokenDto;
import com.gen.springbootserver.mybatis.model.RestorePassword;
import com.gen.springbootserver.mybatis.model.User;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class RequestPasswordService {
    private Logger logger = LoggerFactory.getLogger(RequestPasswordService.class);
    @Autowired
    UserService userService;
    @Autowired
    RestorePasswordService restorePasswordService;
    private String clientUrl;
    private Duration resetPasswordTokenExpiration;
    @Autowired
    public RequestPasswordService(GgProperties properties) {
        clientUrl = properties.getClient().getUrl();
        resetPasswordTokenExpiration=properties.getClient().getResetPasswordToken().getExpiration();
    }

    public void requestPassword(RequestPasswordDTO requestPasswordDTO) {
        User user;

        try {
            user = userService.findByEmail(requestPasswordDTO.getEmail());
        } catch (Exception exception) {
            throw new CommonHttpException("Email is invalid or doesn't registered", HttpStatus.FORBIDDEN);
        }

        // generate reset password token
        RestorePassword token = new RestorePassword();
        token.setToken(UUID.randomUUID().toString());
        token.setUserId(user.getId());
        token.setExpiresin(this.calculateExpirationDate(resetPasswordTokenExpiration));
        restorePasswordService.insertRestorePassword(token);

        // send reset password token via email
        try {
            String resetPasswordUrl = createResetUrl(token);
            // Reset Password Token should be sent via Email. You can use reset url in your
            // template
            logger.info("Reset url was created: {}", resetPasswordUrl);
        } catch (JsonProcessingException exception) {
            throw new CommonHttpException("Can't reset password, please, try again later",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private LocalDateTime calculateExpirationDate(Duration tokenExpirationDuration) {
        return LocalDateTime.now().plusMinutes(tokenExpirationDuration.toMinutes());
    }

    private String createResetUrl(RestorePassword restorePassword) throws JsonProcessingException {
        // create reset password token dto
        RestorePasswordTokenDto restorePasswordTokenDto = new RestorePasswordTokenDto();
        restorePasswordTokenDto.setExpiryDate(restorePassword.getExpiresin());
        restorePasswordTokenDto.setToken(restorePassword.getToken());

        // map token dto to json
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonToken = objectMapper.writeValueAsString(restorePasswordTokenDto);

        // encode with base64
        String encodedToken = Base64.getEncoder().encodeToString(jsonToken.getBytes(UTF_8));

        return clientUrl + "/auth/restore-pass?token=" + encodedToken;
    }
}
