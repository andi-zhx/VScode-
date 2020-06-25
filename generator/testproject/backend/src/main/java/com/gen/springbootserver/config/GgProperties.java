package com.gen.springbootserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.time.Duration;

@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "gg", ignoreUnknownFields = false)
public class GgProperties {
    private final Client client = new Client();
    private final Jwt jwt = new Jwt();
    private final User user = new User();

    public Client getClient() {
        return client;
    }

    public Jwt getJwt() {
        return jwt;
    }

    public User getUser() {
        return user;
    }

    public static class Client {

        private String url;

        private ResetPasswordToken resetPasswordToken;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public ResetPasswordToken getResetPasswordToken() {
            return resetPasswordToken;
        }

        public void setResetPasswordToken(ResetPasswordToken resetPasswordToken) {
            this.resetPasswordToken = resetPasswordToken;
        }
    }

    public static class ResetPasswordToken {
        private Duration expiration;
        private String clearJob;

        public Duration getExpiration() {
            return expiration;
        }

        public void setExpiration(Duration expiration) {
            this.expiration = expiration;
        }

        public String getClearJob() {
            return clearJob;
        }

        public void setClearJob(String clearJob) {
            this.clearJob = clearJob;
        }

    }

    public static class Jwt {

        private String accessTokenSecretKey;

        private String refreshTokenSecretKey;

        private long accessTokenValidityInMilliseconds;

        private long refreshTokenValidityInMilliseconds;

        public void setAccessTokenSecretKey(String accessTokenSecretKey) {
            this.accessTokenSecretKey = Base64.getEncoder().encodeToString(accessTokenSecretKey.getBytes(UTF_8));
        }

        public void setRefreshTokenSecretKey(String refreshTokenSecretKey) {
            this.refreshTokenSecretKey = refreshTokenSecretKey;
        }

        public void setAccessTokenValidityInMilliseconds(long accessTokenValidityInMilliseconds) {
            this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        }

        public void setRefreshTokenValidityInMilliseconds(long refreshTokenValidityInMilliseconds) {
            this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
        }

        public String getAccessTokenSecretKey() {
            return accessTokenSecretKey;
        }

        public String getRefreshTokenSecretKey() {
            return refreshTokenSecretKey;
        }

        public long getAccessTokenValidityInMilliseconds() {
            return accessTokenValidityInMilliseconds;
        }

        public long getRefreshTokenValidityInMilliseconds() {
            return refreshTokenValidityInMilliseconds;
        }
    }

    public static class User {
        private String defaultImage;

        public String getDefaultImage() {
            return defaultImage;
        }

        public void setDefaultImage(String defaultImage) {
            this.defaultImage = defaultImage;
        }
    }

}