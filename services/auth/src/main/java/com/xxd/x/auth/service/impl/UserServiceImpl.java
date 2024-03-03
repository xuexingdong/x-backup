package com.xxd.x.auth.service.impl;


import com.xxd.x.auth.constant.RedisKeys;
import com.xxd.x.auth.dto.PasswordLoginDTO;
import com.xxd.x.auth.exception.Exceptions;
import com.xxd.x.auth.service.UserService;
import com.xxd.x.common.crypto.XCrypto;
import com.xxd.x.entity.User;
import com.xxd.x.auth.model.Token;
import com.xxd.x.jwt.JWTConfig;
import com.xxd.x.mapper.UserMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @NotEmpty
    @Value("${salt}")
    private String salt;

    @Autowired
    private JWTConfig jwtConfig;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Token passwordLogin(PasswordLoginDTO passwordLoginDTO) {
        User user = userMapper.findByUsername(passwordLoginDTO.getUsername());
        if (Objects.isNull(user)) {
            throw Exceptions.USER_NOT_EXIST;
        }
        String password = XCrypto.BCrypt.encrypt(passwordLoginDTO.getPassword(), salt);
        if (!password.equals(user.getPassword())) {
            throw Exceptions.WRONG_PASSWORD;
        }
        logger.info("User {} logged in", user.getUsername());
        return genAndStorageToken(user.getId());
    }

    @Override
    public void logout(String userId) {
        logger.info("User id {} logged out", userId);
        clearToken(userId);
    }

    private Token genAndStorageToken(String userId) {
        String role = userMapper.getRoleByUserId(userId);
        byte[] encodedKey = Base64.getEncoder().encode(jwtConfig.getSecretKey().getBytes());
        Key key = Keys.hmacShaKeyFor(encodedKey);
        String tokenStr = Jwts.builder()
                .claim("role", role)
                .setSubject(userId)
                .setIssuedAt(new Date())
                .signWith(key)
                .compact();
        Token token = new Token(tokenStr, jwtConfig.getExpiration().getSeconds());
        token.setToken(tokenStr);
        stringRedisTemplate.opsForValue().set(RedisKeys.JWT_PREFIX + userId, token.getToken(), jwtConfig.getExpiration().getSeconds(), TimeUnit.SECONDS);
        return token;
    }

    private void clearToken(String userId) {
        if (StringUtils.isNotEmpty(userId)) {
            stringRedisTemplate.delete(RedisKeys.JWT_PREFIX + userId);
        }
    }
}
