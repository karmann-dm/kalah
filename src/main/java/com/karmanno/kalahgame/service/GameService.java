package com.karmanno.kalahgame.service;

import com.karmanno.kalahgame.entity.Game;

public interface GameService {
    Game create();
    Game makeMovement(Integer gameId, Integer pitId);
}
