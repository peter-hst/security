package me.togo.security.service;

import me.togo.security.domain.User;
import me.togo.security.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserServiceI {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        if (exist(user.getUsername())) {
            throw new RuntimeException("用户已存在");
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 判断用户是否存在
     */
    private boolean exist(String username) {
        return null != userRepository.findByUsername(username);
    }
}
