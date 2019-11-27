package com.karmanno.kalahgame.unit;

import com.karmanno.kalahgame.util.KalahBoard;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
public class KalahBoardTest {
    private KalahBoard kalahBoard;

    @Test
    @DisplayName("Should initialize board correctly")
    public void initBoardTest() {
        // given:
        kalahBoard = KalahBoard.init();

        // when:
        Map<Integer, Integer> board = kalahBoard.getBoard();

        // then:
        Assert.assertEquals(14, board.size());
        Assert.assertEquals(6, (int) board.get(1));
        Assert.assertEquals(6, (int) board.get(2));
        Assert.assertEquals(6, (int) board.get(3));
        Assert.assertEquals(6, (int) board.get(4));
        Assert.assertEquals(6, (int) board.get(5));
        Assert.assertEquals(6, (int) board.get(6));
        Assert.assertEquals(0, (int) board.get(7));
        Assert.assertEquals(6, (int) board.get(8));
        Assert.assertEquals(6, (int) board.get(9));
        Assert.assertEquals(6, (int) board.get(10));
        Assert.assertEquals(6, (int) board.get(11));
        Assert.assertEquals(6, (int) board.get(12));
        Assert.assertEquals(6, (int) board.get(13));
        Assert.assertEquals(0, (int) board.get(14));
    }

    @Test
    @DisplayName("Should make first move by first user correctly")
    public void makeFirstMove() {
        // given:
        kalahBoard = KalahBoard.init();
        kalahBoard.firstUserMoves(2);

        // when:
        Map<Integer, Integer> board = kalahBoard.getBoard();

        // then:
        Assert.assertEquals(14, board.size());
        Assert.assertEquals(6, (int) board.get(1));
        Assert.assertEquals(0, (int) board.get(2));
        Assert.assertEquals(7, (int) board.get(3));
        Assert.assertEquals(7, (int) board.get(4));
        Assert.assertEquals(7, (int) board.get(5));
        Assert.assertEquals(7, (int) board.get(6));
        Assert.assertEquals(1, (int) board.get(7));
        Assert.assertEquals(7, (int) board.get(8));
        Assert.assertEquals(6, (int) board.get(9));
        Assert.assertEquals(6, (int) board.get(10));
        Assert.assertEquals(6, (int) board.get(11));
        Assert.assertEquals(6, (int) board.get(12));
        Assert.assertEquals(6, (int) board.get(13));
        Assert.assertEquals(0, (int) board.get(14));
    }

    @Test
    @DisplayName("Should make first move by first user correctly")
    public void makeSecondMove() {
        // given:
        kalahBoard = KalahBoard.init();
        kalahBoard.secondUserMoves(11);

        // when:
        Map<Integer, Integer> board = kalahBoard.getBoard();

        // then:
        Assert.assertEquals(14, board.size());
        Assert.assertEquals(7, (int) board.get(1));
        Assert.assertEquals(7, (int) board.get(2));
        Assert.assertEquals(7, (int) board.get(3));
        Assert.assertEquals(6, (int) board.get(4));
        Assert.assertEquals(6, (int) board.get(5));
        Assert.assertEquals(6, (int) board.get(6));
        Assert.assertEquals(0, (int) board.get(7));
        Assert.assertEquals(6, (int) board.get(8));
        Assert.assertEquals(6, (int) board.get(9));
        Assert.assertEquals(6, (int) board.get(10));
        Assert.assertEquals(0, (int) board.get(11));
        Assert.assertEquals(7, (int) board.get(12));
        Assert.assertEquals(7, (int) board.get(13));
        Assert.assertEquals(1, (int) board.get(14));
    }

    @Test
    @DisplayName("Should load from status correctly")
    public void loadFromStatus() {
        // given:
        Map<Integer, Integer> initData = new HashMap<>();
        initData.put(1, 7);
        initData.put(2, 7);
        initData.put(3, 7);
        initData.put(4, 6);
        initData.put(5, 6);
        initData.put(6, 6);
        initData.put(7, 0);
        initData.put(8, 6);
        initData.put(9, 6);
        initData.put(10, 6);
        initData.put(11, 0);
        initData.put(12, 7);
        initData.put(13, 7);
        initData.put(14, 1);

        // when:
        kalahBoard = KalahBoard.load(initData);
        Map<Integer, Integer> board = kalahBoard.getBoard();

        // then:
        Assert.assertEquals(14, board.size());
        Assert.assertEquals(7, (int) board.get(1));
        Assert.assertEquals(7, (int) board.get(2));
        Assert.assertEquals(7, (int) board.get(3));
        Assert.assertEquals(6, (int) board.get(4));
        Assert.assertEquals(6, (int) board.get(5));
        Assert.assertEquals(6, (int) board.get(6));
        Assert.assertEquals(0, (int) board.get(7));
        Assert.assertEquals(6, (int) board.get(8));
        Assert.assertEquals(6, (int) board.get(9));
        Assert.assertEquals(6, (int) board.get(10));
        Assert.assertEquals(0, (int) board.get(11));
        Assert.assertEquals(7, (int) board.get(12));
        Assert.assertEquals(7, (int) board.get(13));
        Assert.assertEquals(1, (int) board.get(14));
    }
}
