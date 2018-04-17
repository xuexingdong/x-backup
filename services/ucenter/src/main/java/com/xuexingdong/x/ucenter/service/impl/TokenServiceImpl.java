package com.xuexingdong.x.ucenter.service.impl;

import com.xuexingdong.x.entity.User;
import com.xuexingdong.x.ucenter.dto.UserDTO;
import com.xuexingdong.x.ucenter.exception.BusinessException;
import com.xuexingdong.x.ucenter.exception.ClientException;
import com.xuexingdong.x.ucenter.model.Token;
import com.xuexingdong.x.ucenter.repository.UserRepository;
import com.xuexingdong.x.ucenter.service.TokenService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;


@Service
public class TokenServiceImpl implements TokenService {

    @Value("${token.secret-key}")
    private String secretKey;

    @Value("${token.expire-time}")
    private long expire;

    private final UserRepository userRepository;

    @Autowired
    public TokenServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Token generateToken(UserDTO userDTO) {
        if (StringUtils.isAnyEmpty(userDTO.getUsername(), userDTO.getPassword())) {
            throw new ClientException("用户名/密码不能为空");
        }
        User example = new User();
        example.setUsername(userDTO.getUsername());
        example.setPassword(userDTO.getPassword());
        userRepository.exists(Example.of(example));
        boolean exists = userRepository.exists(Example.of(example));
        if (!exists) {
            throw new BusinessException("用户名/密码错误");
        }
        byte[] encodedKey = Base64.decodeBase64(secretKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        JwtBuilder builder = Jwts.builder()
                .setSubject(userDTO.getUsername())
                .signWith(SignatureAlgorithm.HS512, key);
        if (expire > 0) {
            builder.setExpiration(new Date(expire));
        }
        Token token = new Token();
        token.setToken(builder.compact());
        token.setExpireTime(expire);
        return token;
    }
}
