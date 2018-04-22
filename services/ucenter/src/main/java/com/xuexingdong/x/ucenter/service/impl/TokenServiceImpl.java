package com.xuexingdong.x.ucenter.service.impl;

import com.xuexingdong.x.entity.User;
import com.xuexingdong.x.ucenter.config.JwtConfig;
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
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Optional;


@Service
public class TokenServiceImpl implements TokenService {

    private final JwtConfig jwtConfig;

    private final UserRepository userRepository;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public TokenServiceImpl(JwtConfig jwtConfig, UserRepository userRepository) {
        this.jwtConfig = jwtConfig;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Token> generateToken(Mono<UserDTO> userDTOMono) {
        return userDTOMono.map(userDTO -> {
            if (StringUtils.isAnyEmpty(userDTO.getUsername(), userDTO.getPassword())) {
                throw new ClientException("用户名/密码不能为空");
            }
            User example = new User();
            example.setUsername(userDTO.getUsername());
            example.setPassword(userDTO.getPassword());
            Optional<User> user = userRepository.findOne(Example.of(example));
            if (!user.isPresent()) {
                throw new BusinessException("用户名/密码错误");
            }
            byte[] encodedKey = Base64.decodeBase64(jwtConfig.getSecretKey());
            SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
            JwtBuilder builder = Jwts.builder()
                    .setSubject(userDTO.getUsername())
                    .signWith(SignatureAlgorithm.HS512, key);
            if (jwtConfig.getExpireTime() > 0) {
                builder.setExpiration(new Date(jwtConfig.getExpireTime()));
            }
            Token token = new Token();
            token.setToken(builder.compact());
            token.setExpireTime(jwtConfig.getExpireTime());
            // 缓存userId
            stringRedisTemplate.opsForValue().set(token.getToken(), user.get().getId());
            return token;
        });
    }
}
