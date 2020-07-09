package com.qfedu.service.impl;

import com.qfedu.mapper.UserMapper;
import com.qfedu.pojo.User;
import com.qfedu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jayus
 * @create 2020-07-09-5:51 下午
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean regist(User user) {
       int i = userMapper.insert(user);
       if(i == 0) {
           return false;
       } else {
           return true;
       }
    }
}
