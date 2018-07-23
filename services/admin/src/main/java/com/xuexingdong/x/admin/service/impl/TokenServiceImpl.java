package com.xuexingdong.x.admin.service.impl;

import com.xuexingdong.x.admin.dto.UserDTO;
import com.xuexingdong.x.admin.model.Token;
import com.xuexingdong.x.admin.service.TokenService;
import com.xuexingdong.x.common.crypto.XCrypto;
import com.xuexingdong.x.entity.User;
import com.xuexingdong.x.jwt.JwtConfig;
import com.xuexingdong.x.jwt.JwtService;
import com.xuexingdong.x.mapper.UserMapper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Token generate(UserDTO userDTO) {
        User user = userMapper.findByUsername(userDTO.getUsername());

        boolean success = XCrypto.BCrypt.encrypt(userDTO.getPassword(), user.getSalt()).equals(user.getPassword());
        byte[] encodedKey = Base64.getEncoder().encode(jwtConfig.getSecretKey().getBytes());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        JwtBuilder builder = Jwts.builder()
                .setSubject(user.getId())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, key);
        Token token = new Token();
        token.setToken(builder.compact());
        token.setExpiration(jwtConfig.getExpiration().getSeconds());
        // 缓存token
        stringRedisTemplate.opsForValue().set("backend:jwt:" + user.getId(), token.getToken(), jwtConfig.getExpiration().getSeconds(), TimeUnit.SECONDS);
        return token;
    }

    @Override
    public boolean clear() {
        String userId = jwtService.getCurrentUserId();
        Objects.requireNonNull(userId, "登出失败");
        return BooleanUtils.isTrue(stringRedisTemplate.delete("backend:jwt:" + userId));
    }
}
