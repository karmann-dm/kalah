package com.karmanno.kalahgame.entity;

import java.util.Random;

public enum  UserTurn {
    FIRST_USER(1),
    SECOND_USER(2);

    private int value;

    UserTurn(int value) {
        this.value = value;
    }

    public static UserTurn randomTurn() {
        int value = new Random().nextInt(2) + 1;
        if (value == FIRST_USER.value)
            return FIRST_USER;
        else
            return SECOND_USER;
    }

    public static boolean isFirstUsersTurn(UserTurn turn) {
        return turn.equals(FIRST_USER);
    }

    public static boolean isSecondUsersTurn(UserTurn turn) {
        return turn.equals(SECOND_USER);
    }
}
