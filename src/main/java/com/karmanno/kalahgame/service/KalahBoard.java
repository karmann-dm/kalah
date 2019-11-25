package com.karmanno.kalahgame.service;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class KalahBoard {
    private List<Integer> boardPits;
    private static final int FIRST_KALAH = 0;
    private static final int SECOND_KALAH = 7;
    private static final int INITIAL_CAPACITY = 13;
    private static final int INITIAL_PIT_SIZE = 6;

    private KalahBoard() {
        boardPits = new ArrayList<>(INITIAL_CAPACITY);
        for (int index = 0; index < INITIAL_CAPACITY; index++) {
            if (index == FIRST_KALAH || index == SECOND_KALAH) {
                boardPits.set(index, 0);
            }
            else {
                boardPits.set(index, INITIAL_PIT_SIZE);
            }
        }
    }

    private KalahBoard(Map<Integer, Integer> boardStatus) {

    }

    public static KalahBoard load(Map<Integer, Integer> boardStatus) {
        return new KalahBoard(boardStatus);
    }

    public static KalahBoard init() {
        return new KalahBoard();
    }

    public boolean firstUserMoves(int pitId) {
        int pitIndex = pitId - 1;
        int stepsCount = boardPits.get(pitIndex);
        boardPits.set(pitIndex, 0);
        int endIndex = pitIndex;
        for (int index = pitIndex; index < pitIndex + stepsCount; index++) {
            boardPits.set(index, boardPits.get(index) + 1);
            endIndex = index;
        }
        return endIndex == FIRST_KALAH;
    }

    public boolean secondUserMoves(int pitId) {
        int pitIndex = pitId - 1;
        int stepsCount = boardPits.get(pitIndex);
        boardPits.set(pitIndex, 0);
        int endIndex = pitIndex;
        for (int index = pitIndex; index >= pitIndex + stepsCount; index--) {
            boardPits.set(index, boardPits.get(index) + 1);
            endIndex = index;
        }
        return endIndex == SECOND_KALAH;
    }
}
