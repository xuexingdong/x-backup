package com.xuexingdong.x.backend.service.impl;

import com.xuexingdong.x.backend.dto.RegisterDTO;
import com.xuexingdong.x.backend.exception.BusinessException;
import com.xuexingdong.x.backend.exception.Exceptions;
import com.xuexingdong.x.backend.service.UserService;
import com.xuexingdong.x.backend.vo.UserVO;
import com.xuexingdong.x.common.crypto.XCrypto;
import com.xuexingdong.x.common.utils.XRandomUtils;
import com.xuexingdong.x.entity.User;
import com.xuexingdong.x.mapper.UserMapper;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Objects;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Value("${point.register}")
    private int registerPoint;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    @Transactional
    public boolean register(RegisterDTO registerDTO) {
        String chatId = registerDTO.getChatId();
        // 未知好友，禁止注册
        Set<Object> chatIds = stringRedisTemplate.opsForHash().keys("chatbot:username_remark_name_mapping");
        if (!chatIds.contains(chatId)) {
            throw new BusinessException("未知好友不允许注册");
        }
        // 这个chatid已经注册过
        if (stringRedisTemplate.opsForHash().hasKey("backend:chatid_userid_mapping", chatId)) {
            throw new BusinessException("不允许重复注册");
        }
        // 用户名已存在
        if (Objects.nonNull(userMapper.findByUsername(registerDTO.getUsername()))) {
            throw Exceptions.USER_ALREADY_EXISTS;
        }
        // 密码加盐
        String salt = RandomStringUtils.randomAlphanumeric(16);
        String userId = XRandomUtils.randomUUID();
        User user = new User()
                .setId(userId)
                .setUsername("")
                .setRemarkName(registerDTO.getUsername())
                .setPassword(XCrypto.BCrypt.encrypt(registerDTO.getPassword(), salt))
                .setSalt(salt);
        userMapper.insert(user);
        // 注册送50积分
        boolean addSuccess = userMapper.plusPoints(userId, registerPoint);
        if (!addSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        // 存储chatId与userId的关系
        stringRedisTemplate.opsForHash().put("backend:chatid_userid_mapping", chatId, userId);
        logger.info("User {} registered with chatId {}", userId, chatId);
        return true;
    }

    @Override
    public UserVO getById(String id) {
        User user = userMapper.findById(id);
        if (Objects.isNull(user)) {
            throw Exceptions.USER_NOT_EXIST;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
