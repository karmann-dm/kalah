package com.karmanno.kalahgame.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int userId) {
        super(errorMessage(userId));
    }

    public UserNotFoundException(String username) {
        super(errorMessage(username));
    }

    private static String errorMessage(int userId) {
        return String.format("User with id %d not found", userId);
    }

    private static String errorMessage(String username) {
        return String.format("User with username %s not found", username);
    }
}