package com.karmanno.kalahgame.exception;

public class InvalidGameAccessException extends RuntimeException {
    public InvalidGameAccessException(int userId, int gameId) {
        super(errorMessage(userId, gameId));
    }

    private static String errorMessage(int userId, int gameId) {
        return String.format("User with id %d has no access to the game %d", userId, gameId);
    }
}
