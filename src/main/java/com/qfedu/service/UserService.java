package com.qfedu.service;

import com.qfedu.pojo.User;
import org.springframework.stereotype.Service;

/**
 * @author Jayus
 * @create 2020-07-09-5:51 下午
 */

public interface UserService {
    public boolean regist(User user);
}
