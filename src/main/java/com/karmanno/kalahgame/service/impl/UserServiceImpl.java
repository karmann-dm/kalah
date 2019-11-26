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
    public User loadById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("Unable to find user with id = " + userId));
    }

    @Override
    public User register(String username, String password) {
        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        return userRepository.save(user);
    }
}
