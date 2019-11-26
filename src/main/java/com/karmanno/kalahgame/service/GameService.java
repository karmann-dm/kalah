package com.karmanno.kalahgame.service;

import com.karmanno.kalahgame.entity.Game;

public interface GameService {
    Game create(Integer userId);
    Game joinGame(Integer userId, Integer gameId);
    Game makeMovement(Integer userId, Integer gameId, Integer pitId);
}
