package com.karmanno.kalahgame.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(int gameId) {
        super(errorMessage(gameId));
    }

    private static String errorMessage(int gameId) {
        return String.format("Game with id %d not found", gameId);
    }
}
