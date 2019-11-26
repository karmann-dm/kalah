package com.karmanno.kalahgame.service.impl;

import com.karmanno.kalahgame.entity.Game;
import com.karmanno.kalahgame.entity.UserTurn;
import com.karmanno.kalahgame.repository.GameRepository;
import com.karmanno.kalahgame.service.GamePermissionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GamePermissionsServiceImpl implements GamePermissionsService {
    private final GameRepository gameRepository;

    @Override
    public boolean canUserMove(Integer userId, Integer gameId) {
        Game game = fetchGame(gameId);
        return (game.getFirstUserId().equals(userId) && game.getCurrentTurn().equals(UserTurn.FIRST_USER)) ||
                (game.getSecondUserId().equals(userId) && game.getCurrentTurn().equals(UserTurn.SECOND_USER));
    }

    @Override
    public boolean hasUserAccess(Integer userId, Integer gameId) {
        Game game = fetchGame(gameId);
        return game.getFirstUserId().equals(userId) || game.getSecondUserId().equals(userId);
    }

    private Game fetchGame(Integer gameId) {
        return gameRepository.findById(gameId).orElseThrow(
                () -> new RuntimeException("Unable to find game with id = " + gameId)
        );
    }
}
