package com.karmanno.kalahgame.service;

import com.karmanno.kalahgame.entity.User;

public interface UserService {
    User loadById(Integer userId);
    User register(String username, String password);
}
