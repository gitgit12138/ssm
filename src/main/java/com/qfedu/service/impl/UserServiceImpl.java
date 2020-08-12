package com.qfedu.service.impl;

import com.qfedu.mapper.UserMapper;
import com.qfedu.pojo.User;
import com.qfedu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jayus
 * @create 2020-08-10-2:41 下午
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public User selectUserAndRolesByUsername(User user) {
        return userMapper.selectUserAndRolesByUsername(user);
    }

    @Override
    public User selectUserByUsername(User user) {
        return userMapper.selectUserByUsername(user);
    }
}
