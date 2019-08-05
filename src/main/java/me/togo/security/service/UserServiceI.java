package me.togo.security.service;

import me.togo.security.domain.User;

public interface UserServiceI {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 保存
     * @param user
     * @return
     */
    User save(User user);
}
