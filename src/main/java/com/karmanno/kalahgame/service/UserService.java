package com.karmanno.kalahgame.service;

import com.karmanno.kalahgame.entity.User;

public interface UserService {
    boolean existsByUsername(String username);
    User register(String username, String password);
}
