package com.karmanno.kalahgame.service.impl;

import com.karmanno.kalahgame.entity.User;
import com.karmanno.kalahgame.repository.UserRepository;
import com.karmanno.kalahgame.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User register(String username, String password) {
        User user = new User(
                null,
                username,
                password
        );
        return userRepository.save(user);
    }
}
