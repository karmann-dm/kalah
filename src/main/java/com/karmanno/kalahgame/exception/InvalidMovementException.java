package com.karmanno.kalahgame.exception;

public class InvalidMovementException extends RuntimeException {
    public InvalidMovementException(int userId, int gameId) {
        super(errorMessage(userId, gameId));
    }

    private static String errorMessage(int userId, int gameId) {
        return String.format(
                "User with id = %d is unable to perform movement in game id = %d",
                userId,
                gameId
        );
    }
}
