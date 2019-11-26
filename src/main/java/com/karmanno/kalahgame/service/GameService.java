package com.karmanno.kalahgame.service;

import com.karmanno.kalahgame.entity.Game;

import java.util.List;

public interface GameService {
    Game create(Integer userId);
    Game joinGame(Integer userId, Integer gameId);
    Game makeMovement(Integer userId, Integer gameId, Integer pitId);
    List<Game> fetchGames(Integer userId);
}
