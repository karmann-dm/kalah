package com.karmanno.kalahgame.integration;

import com.karmanno.kalahgame.KalahGameApplication;
import com.karmanno.kalahgame.entity.Game;
import com.karmanno.kalahgame.entity.User;
import com.karmanno.kalahgame.entity.UserTurn;
import com.karmanno.kalahgame.exception.InvalidGameAccessException;
import com.karmanno.kalahgame.exception.InvalidMovementException;
import com.karmanno.kalahgame.repository.GameRepository;
import com.karmanno.kalahgame.repository.UserRepository;
import com.karmanno.kalahgame.service.GameService;
import com.karmanno.kalahgame.util.StatusConverter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@SpringBootTest(classes = KalahGameApplication.class)
@RunWith(SpringRunner.class)
@Transactional
public class GameServiceTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    StatusConverter statusConverter;

    @Autowired
    GameService gameService;

    private User firstUser;
    private User secondUser;
    private User thirdUser;

    @Before
    public void before() {
        firstUser = new User(null, "firstUser", "pass");
        secondUser = new User(null, "secondUser", "pass");
        thirdUser = new User(null, "thirdUser", "pass");
        firstUser = userRepository.saveAndFlush(firstUser);
        secondUser = userRepository.saveAndFlush(secondUser);
        thirdUser = userRepository.saveAndFlush(thirdUser);
    }

    @After
    public void after() {
        gameRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create came correctly")
    public void testCreateGame() {
        // when:
        Game game = gameService.create(firstUser.getId());

        // then:
        Assert.assertNotNull(game.getId());
        Assert.assertNotNull(game.getFirstUserId());
        Assert.assertNull(game.getSecondUserId());
        Assert.assertNotNull(game.getStatus());
    }

    @Test
    @DisplayName("Should make movement correctly")
    public void testMakeMovementCorrectly() {
        // when:
        Game game = gameService.create(firstUser.getId());
        game.setCurrentTurn(UserTurn.FIRST_USER);
        game = gameService.makeMovement(firstUser.getId(), game.getId(), 2);
        Map<Integer, Integer> board = statusConverter.statusToBoard(game.getStatus());

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

    @Test(expected = InvalidGameAccessException.class)
    @DisplayName("Should receive error when user has no access")
    public void testReceiveErrorWhenUserHasNoAccess() {
        // given:
        Game game = gameService.create(firstUser.getId());
        game = gameService.joinGame(secondUser.getId(), game.getId());

        // when:
        gameService.makeMovement(thirdUser.getId(), game.getId(), 2);
    }

    @Test(expected = InvalidMovementException.class)
    @DisplayName("Should receive error when user can't move")
    public void testReceiveErrorWhenUserCantMove() {
        // given:
        Game game = gameService.create(firstUser.getId());

        // when:
        game = gameService.joinGame(secondUser.getId(), game.getId());

        // then:
        if (game.getCurrentTurn().equals(UserTurn.FIRST_USER)) {
            gameService.makeMovement(secondUser.getId(), game.getId(), 14);
        } else {
            gameService.makeMovement(firstUser.getId(), game.getId(), 2);
        }
    }

    @Test
    @DisplayName("Should join game successfull")
    public void testJoinGame() {
        // given:
        Game game = gameService.create(firstUser.getId());

        // when:
        game = gameService.joinGame(secondUser.getId(), game.getId());

        // then:
        Assert.assertEquals(secondUser.getId(), game.getSecondUserId());
    }

    @Test(expected = RuntimeException.class)
    @DisplayName("Should join game when both slots are busy")
    public void testJoinGameWithError() {
        // given:
        Game game = gameService.create(firstUser.getId());

        // when:
        game = gameService.joinGame(secondUser.getId(), game.getId());

        // then:
        gameService.joinGame(thirdUser.getId(), game.getId());
    }
}
