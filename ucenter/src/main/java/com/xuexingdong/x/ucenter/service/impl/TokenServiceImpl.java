package com.xuexingdong.x.ucenter.service.impl;

import com.xuexingdong.x.ucenter.dto.UserDTO;
import com.xuexingdong.x.ucenter.exception.BusinessException;
import com.xuexingdong.x.ucenter.mapper.UserMapper;
import com.xuexingdong.x.ucenter.model.Token;
import com.xuexingdong.x.ucenter.model.User;
import com.xuexingdong.x.ucenter.service.TokenService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.Objects;


@Service
public class TokenServiceImpl implements TokenService {

    @Value("${token.secret-key}")
    private String secretKey;

    @Value("${token.expire-time}")
    @Min(0)
    private long expire;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Token generateToken(UserDTO userDTO) {
        User user = userMapper.login(userDTO.getUsername(), userDTO.getPassword());
        if (Objects.isNull(user)) {
            throw new BusinessException("账号密码错误");
        }
        byte[] encodedKey = Base64.decodeBase64(secretKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        JwtBuilder builder = Jwts.builder()
                .setSubject(user.getUsername())
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
