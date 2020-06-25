package com.gen.springbootserver.service.bydefault;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import com.gen.springbootserver.config.GgProperties;
import com.gen.springbootserver.dto.authentication.AuthenticationToken;
import com.gen.springbootserver.dto.authentication.Tokens;
import com.gen.springbootserver.dto.errors.AuthenticationException;
import com.gen.springbootserver.mybatis.model.Role;
import com.gen.springbootserver.mybatis.model.User;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class TokenService {
    private String accessTokenSecretKey;
    private String refreshTokenSecretKey;
    private long accessTokenValidityInMilliseconds;
    private long refreshTokenValidityInMilliseconds;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationTokenService authenticationTokenService;

    @Autowired
    public TokenService(GgProperties properties) {
        accessTokenSecretKey = properties.getJwt().getAccessTokenSecretKey();
        accessTokenValidityInMilliseconds = properties.getJwt().getAccessTokenValidityInMilliseconds();
        refreshTokenSecretKey = properties.getJwt().getRefreshTokenSecretKey();
        refreshTokenValidityInMilliseconds = properties.getJwt().getRefreshTokenValidityInMilliseconds();
    }

    public Tokens createToken(User user) {
        Tokens token = new Tokens();
        long expiresIn = expiration(accessTokenValidityInMilliseconds);

        token.setAccessToken(createAccessToken(user));
        token.setRefreshToken(createRefreshToken(user));
        token.setExpiresIn(expiresIn);

        return token;
    }

    public Authentication getAuthentication(AuthenticationToken token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(token.getEmail());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getAccessTokenSecretKey() {
        return accessTokenSecretKey;
    }

    public AuthenticationToken resolveToken(HttpServletRequest req) throws AuthenticationException {
        String bearer = "Bearer ";
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken == null) {
            throw new AuthenticationException("Authorization header should be present");
        }
        if (!bearerToken.startsWith(bearer)) {
            throw new AuthenticationException("Authorization header should begin with Bearer");
        }

        return authenticationTokenService.createToken(bearerToken.substring(bearer.length()));
    }

    String getEmailFromRefreshToken(String token) throws JwtException {
        return Jwts.parser().setSigningKey(refreshTokenSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    private String createAccessToken(User user) {
        long expiresIn = expiration(accessTokenValidityInMilliseconds);

        return createToken(user, expiresIn, accessTokenSecretKey);
    }

    private String createRefreshToken(User user) {
        long expiresIn = expiration(refreshTokenValidityInMilliseconds);

        return createToken(user, expiresIn, refreshTokenSecretKey);
    }

    private List<String> getRoleNames(Set<Role> roles) {
        return roles.stream().map(Role::getName).collect(toList());
    }

    private String createToken(User user, long expiresIn, String key) {
        Claims claims = Jwts.claims();

        claims.setSubject(user.getEmail());
        claims.put("fullName", String.join(" ", user.getFirstName(), user.getLastName()));
        claims.put("createdAt", user.getCreatedAt());
        claims.put("role", getRoleNames(user.getRoles()));
        Date now = new Date();
        Date expirationDate = new Date(expiresIn);

        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, key).compact();
    }

    private long expiration(long validity) {
        Date now = new Date();
        return now.getTime() + validity;
    }

}
