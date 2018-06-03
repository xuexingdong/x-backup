package com.xuexingdong.x.backend.service.impl;

import com.xuexingdong.x.backend.dto.BindDTO;
import com.xuexingdong.x.backend.dto.UserDTO;
import com.xuexingdong.x.backend.exception.BusinessException;
import com.xuexingdong.x.backend.service.JwtService;
import com.xuexingdong.x.backend.service.UserService;
import com.xuexingdong.x.backend.vo.UserVO;
import com.xuexingdong.x.common.crypto.XCrypto;
import com.xuexingdong.x.common.utils.XRandomUtils;
import com.xuexingdong.x.entity.User;
import com.xuexingdong.x.mapper.UserMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User register(UserDTO userDTO) {
        if (Objects.nonNull(userMapper.findOneByUsername(userDTO.getUsername()))) {
            throw new BusinessException("用户已存在");
        }
        String salt = RandomStringUtils.randomAlphanumeric(16);
        User user = new User()
                .setId(XRandomUtils.randomUUID())
                .setUsername(userDTO.getUsername())
                .setPassword(XCrypto.BCrypt.encrypt(userDTO.getPassword(), salt))
                .setSalt(salt);
        try {
            userMapper.insert(user);
        } catch (DuplicateKeyException e) {
            throw new BusinessException("注册失败");
        }
        return user;
    }

    @Override
    public UserVO getById(String id) {
        User user = userMapper.findById(id);
        if (Objects.nonNull(user)) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }
        throw new BusinessException("用户不存在");
    }

    @Override
    public UserVO getByOpenid(String openid) {
        User user = userMapper.findByOpenid(openid);
        if (Objects.isNull(user)) {
            throw new BusinessException("用户不存在");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public void bind(BindDTO bindDTO) {
        // String redisKey = "chatbot:secret_key:" + bindDTO.getSecretKey();
        // // 密钥校验不通过
        // if (BooleanUtils.isNotTrue(stringRedisTemplate.hasKey(redisKey))) {
        //     throw new ServerException("密钥错误");
        // }
        // User user = new User()
        //         .setId(XRandomUtils.randomUUID())
        //         .setOpenid(bindDTO.getOpenid())
        //         .setCreatedAt(LocalDateTime.now());
        // try {
        //     userMapper.insert(user);
        // } catch (DuplicateKeyException e) {
        //     throw new ServerException("绑定失败");
        // }
        // // 存储openid与备注的映射
        // stringRedisTemplate.opsForHash().put("chatbot:openid_username_mapping", bindDTO.getOpenid(), bindDTO.getRemarkName());
        // // 存储备注与openid的映射
        // stringRedisTemplate.opsForHash().put("chatbot:username_openid_mapping", bindDTO.getRemarkName(), bindDTO.getOpenid());
    }

    @Override
    public void reBind(BindDTO bindDTO) {

    }

    @Override
    public void unBind(String userId) {
    }
}
