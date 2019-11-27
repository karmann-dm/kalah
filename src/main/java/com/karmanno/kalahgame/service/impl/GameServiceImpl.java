package com.karmanno.kalahgame.service.impl;

import com.karmanno.kalahgame.entity.Game;
import com.karmanno.kalahgame.entity.UserTurn;
import com.karmanno.kalahgame.exception.GameNotFoundException;
import com.karmanno.kalahgame.exception.InvalidGameAccessException;
import com.karmanno.kalahgame.exception.InvalidMovementException;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final StatusConverter statusConverter;
    private final GamePermissionsService gamePermissionsService;

    @Override
    public Game create(Integer userId) {
        Game game = new Game();
        String initialStatus = statusConverter.boardToStatus(
                KalahBoard.init().getBoard());
        game.setStatus(initialStatus);
        game.setFirstUserId(userId);
        log.info("Game is being created... {}", game.toString());
        return gameRepository.saveAndFlush(game);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE) // In case of main requirements of the game by which users should make their turn in defined order
    public Game makeMovement(Integer userId, Integer gameId, Integer pitId) {
        validateAccess(userId, gameId);
        Game game = fetchGame(gameId);
        move(game, pitId);
        log.info("Movement is being performed... {}", game);
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
        log.info("User with id={} has been joined to the game with id={} : {}", userId, gameId, game);
        return gameRepository.save(game);
    }

    @Override
    @Transactional
    public List<Game> fetchGames(Integer userId) {
        return gameRepository.fetchGamesByUserId(userId);
    }

    private void move(Game game, int pitId) {
        KalahBoard board = KalahBoard.load(
                statusConverter.statusToBoard(game.getStatus())
        );
        if (UserTurn.isFirstUsersTurn(game.getCurrentTurn())) {
            if (board.isPitOfSecondUser(pitId)) {
                throw new RuntimeException("Turn is not allowed");
            }
            boolean result = board.firstUserMoves(pitId);
            if (!result) {
                game.setCurrentTurn(UserTurn.FIRST_USER);
            }
        }
        if (UserTurn.isSecondUsersTurn(game.getCurrentTurn())) {
            if (board.isPitOfFirstUser(pitId)) {
                throw new RuntimeException("Turn is not allowed");
            }
            boolean result = board.secondUserMoves(pitId);
            if (!result) {
                game.setCurrentTurn(UserTurn.SECOND_USER);
            }
        }
        String newStatus = statusConverter.boardToStatus(board.getBoard());
        game.setStatus(newStatus);
    }

    private void validateAccess(Integer userId, Integer gameId) {
        if (!gamePermissionsService.hasUserAccess(userId, gameId)) {
            throw new InvalidGameAccessException(userId, gameId);
        }
        if (!gamePermissionsService.canUserMove(userId, gameId)) {
            throw new InvalidMovementException(userId, gameId);
        }
    }

    private Game fetchGame(Integer gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() ->
                        new GameNotFoundException(gameId)
                );
    }
}
