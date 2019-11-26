package com.karmanno.kalahgame.service.impl;

import com.karmanno.kalahgame.entity.Game;
import com.karmanno.kalahgame.entity.UserTurn;
import com.karmanno.kalahgame.repository.GameRepository;
import com.karmanno.kalahgame.service.GamePermissionsService;
import com.karmanno.kalahgame.service.GameService;
import com.karmanno.kalahgame.util.KalahBoard;
import com.karmanno.kalahgame.util.StatusConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final StatusConverter statusConverter;
    private final GamePermissionsService gamePermissionsService;

    @Override
    public Game create() {
        Game game = new Game();
        String initialStatus = statusConverter.boardToStatus(
                KalahBoard.init().getBoard());
        log.info("Game is being created... Initial status = {}", initialStatus);
        game.setStatus(initialStatus);
        return gameRepository.save(game);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE) // In case of main requirements of the game by which users should make their turn in defined order
    public Game makeMovement(Integer gameId, Integer userId, Integer pitId) {
        validateAccess(userId, gameId);
        Game game = fetchGame(gameId);
        move(game, pitId);
        return gameRepository.save(game);
    }

    @Override
    @Transactional
    public Game joinGame(Integer userId, Integer gameId) {
        Game game = fetchGame(gameId);
        if (game.getFirstUserId() == null) {
            game.setFirstUserId(userId);
        }
        else if (game.getSecondUserId() == null) {
            game.setSecondUserId(userId);
        } else {
            throw new RuntimeException("Could not join game. Both of users are set");
        }
        return gameRepository.save(game);
    }

    private void move(Game game, int pitId) {
        KalahBoard board = KalahBoard.load(
                statusConverter.statusToBoard(game.getStatus())
        );
        if (UserTurn.isFirstUsersTurn(game.getCurrentTurn())) {
            boolean result = board.firstUserMoves(pitId);
            if (!result) {
                game.setCurrentTurn(UserTurn.FIRST_USER);
            }
        }
        if (UserTurn.isSecondUsersTurn(game.getCurrentTurn())) {
            boolean result = board.secondUserMoves(pitId);
            if (!result) {
                game.setCurrentTurn(UserTurn.SECOND_USER);
            }
        }
        String newStatus = statusConverter.boardToStatus(board.getBoard());
        log.info("Movement performed. New status = {}", newStatus);
        game.setStatus(newStatus);
    }

    private void validateAccess(Integer userId, Integer gameId) {
        if (!gamePermissionsService.hasUserAccess(null, gameId)) {
            throw new RuntimeException("User does not have access to game");
        }
        if (!gamePermissionsService.canUserMove(null, gameId)) {
            throw new RuntimeException("User can't move right now (another user's turn)");
        }
    }

    private Game fetchGame(Integer gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() ->
                        new RuntimeException("Unable to find game with id = " + gameId)
                );
    }
}
