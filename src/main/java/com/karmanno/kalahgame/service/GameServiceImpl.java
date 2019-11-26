package com.karmanno.kalahgame.service;

import com.karmanno.kalahgame.entity.Game;
import com.karmanno.kalahgame.entity.UserTurn;
import com.karmanno.kalahgame.repository.GameRepository;
import com.karmanno.kalahgame.util.StatusConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final StatusConverter statusConverter;

    @Override
    public Game create() {
        Game game = new Game();
        String initialStatus = statusConverter.boardToStatus(
                KalahBoard.init().getBoard());
        log.debug("Game is being created... Initial status = {}", initialStatus);
        game.setStatus(initialStatus);
        return gameRepository.save(game);
    }

    @Override
    @Transactional
    public Game makeMovement(Integer gameId, Integer pitId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() ->
                        new RuntimeException("Unable to find game with id = " + gameId)
                );
        move(game, pitId);
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
        log.debug("Movement performed. New status = {}", newStatus);
        game.setStatus(newStatus);
    }
}
