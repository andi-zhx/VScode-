package com.gen.springbootserver.mybatis.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RestorePassword implements Serializable {
    private Long id;

    private LocalDateTime expiresin;

    private String token;

    private Long userId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getExpiresin() {
        return expiresin;
    }

    public void setExpiresin(LocalDateTime expiresin) {
        this.expiresin = expiresin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", expiresin=").append(expiresin);
        sb.append(", token=").append(token);
        sb.append(", userId=").append(userId);
        sb.append("]");
        return sb.toString();
    }
}