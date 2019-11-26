package com.karmanno.kalahgame.service;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class KalahBoard {
    private List<Integer> boardPits;
    private static final int FIRST_KALAH = 6;
    private static final int SECOND_KALAH = 13;
    private static final int INITIAL_CAPACITY = 14;
    private static final int INITIAL_PIT_SIZE = 6;

    private KalahBoard() {
        boardPits = new ArrayList<>();
        for (int index = 0; index < INITIAL_CAPACITY; index++) {
            if (index == FIRST_KALAH || index == SECOND_KALAH) {
                boardPits.add(0);
            }
            else {
                boardPits.add(INITIAL_PIT_SIZE);
            }
        }
    }

    private KalahBoard(Map<Integer, Integer> boardStatus) {
        boardPits = new ArrayList<>();
        boardStatus.forEach((key, value) -> boardPits.add(value));
    }

    static KalahBoard load(Map<Integer, Integer> boardStatus) {
        return new KalahBoard(boardStatus);
    }

    static KalahBoard init() {
        return new KalahBoard();
    }

    Map<Integer, Integer> getBoard() {
        Map<Integer, Integer> board = new HashMap<>();
        for (int index = 0; index < boardPits.size(); index++ ) {
            board.put(index + 1, boardPits.get(index));
        }
        return board;
    }

    boolean firstUserMoves(int pitId) {
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

    boolean secondUserMoves(int pitId) {
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
