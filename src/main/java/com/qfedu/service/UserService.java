package com.qfedu.service;

import com.qfedu.pojo.User;

/**
 * @author Jayus
 * @create 2020-08-10-2:40 下午
 */
public interface UserService {
    public User selectByUsername(String username);

    public User selectUserAndRolesByUsername(User user);

    public User selectUserByUsername(User user);

}
