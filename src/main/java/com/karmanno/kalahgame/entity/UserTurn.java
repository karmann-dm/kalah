package com.karmanno.kalahgame.entity;

public enum  UserTurn {
    FIRST_USER(1),
    SECOND_USER(2);

    private int value;

    UserTurn(int value) {
        this.value = value;
    }

    public static boolean isFirstUsersTurn(UserTurn turn) {
        return turn.equals(FIRST_USER);
    }

    public static boolean isSecondUsersTurn(UserTurn turn) {
        return turn.equals(SECOND_USER);
    }
}
