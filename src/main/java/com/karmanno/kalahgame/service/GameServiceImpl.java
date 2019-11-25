package com.karmanno.kalahgame.service;

import com.karmanno.kalahgame.entity.Game;
import com.karmanno.kalahgame.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    @Override
    public Game create() {
        return gameRepository.save(new Game());
    }

    @Override
    public Game makeMovement(Integer gameId, Integer pitId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() ->
                        new RuntimeException("Unable to find game with id = " + gameId)
                );
        game.move(pitId);
        return gameRepository.save(game);
    }
}
