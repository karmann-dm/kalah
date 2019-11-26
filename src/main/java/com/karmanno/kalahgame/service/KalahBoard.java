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
        int endIndex = cycleShift(pitIndex, SECOND_KALAH);
        return endIndex == FIRST_KALAH;
    }

    boolean secondUserMoves(int pitId) {
        int pitIndex = pitId - 1;
        int endIndex = cycleShift(pitIndex, FIRST_KALAH);
        return endIndex == SECOND_KALAH;
    }

    private int cycleShift(int pitIndex, int prohibitedPit) {
        int weight = boardPits.get(pitIndex);
        boardPits.set(pitIndex, 0);
        int currentIndex;
        do {
            currentIndex = nextIndex(pitIndex);
            if (currentIndex != prohibitedPit) {
                boardPits.set(currentIndex, boardPits.get(currentIndex) + 1);
            }
            weight--;
        } while (weight > 0);
        return currentIndex;
    }

    private int nextIndex(int currentIndex) {
        return (currentIndex + 1) % INITIAL_CAPACITY;
    }
}
