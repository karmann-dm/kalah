package com.karmanno.kalahgame.unit;

import com.karmanno.kalahgame.entity.Game;
import com.karmanno.kalahgame.entity.User;
import com.karmanno.kalahgame.entity.UserTurn;
import com.karmanno.kalahgame.repository.GameRepository;
import com.karmanno.kalahgame.service.GamePermissionsService;
import com.karmanno.kalahgame.service.impl.GamePermissionsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class GamePermissionServiceTest {
    private GamePermissionsService gamePermissionsService;

    @Mock
    private GameRepository gameRepository;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        gamePermissionsService = new GamePermissionsServiceImpl(gameRepository);
    }

    @Test
    @DisplayName("Should check if user can move")
    public void canUserMoveTestPositive() {
        // given:
        Game game = setUpGame();
        when(gameRepository.save(game)).thenReturn(game);
        when(gameRepository.findById(1)).thenReturn(Optional.of(game));

        // when:
        boolean result = gamePermissionsService.canUserMove(1, 1);

        // then:
        Assert.assertTrue(result);
    }

    @Test
    @DisplayName("Should check if user can't move'")
    public void canUserMoveTestNegative() {
        // given:
        Game game = setUpGame();
        when(gameRepository.save(game)).thenReturn(game);
        when(gameRepository.findById(1)).thenReturn(Optional.of(game));

        // when:
        boolean result = gamePermissionsService.canUserMove(2, 1);

        // then:
        Assert.assertFalse(result);
    }

    @Test
    @DisplayName("Should check if user has access")
    public void hasUserAccessTestPositive() {
        // given:
        Game game = setUpGame();
        when(gameRepository.findById(1)).thenReturn(Optional.of(game));

        // when:
        boolean firstResult = gamePermissionsService.hasUserAccess(1, 1);
        boolean secondResult = gamePermissionsService.hasUserAccess(2, 1);

        // then:
        Assert.assertTrue(firstResult);
        Assert.assertTrue(secondResult);
    }

    @Test
    @DisplayName("Should check if user has no access")
    public void hasUserAccessTestNegative() {
        // given:
        Game game = setUpGame();
        when(gameRepository.findById(1)).thenReturn(Optional.of(game));

        // when:
        boolean result = gamePermissionsService.hasUserAccess(5, 1);

        // then:
        Assert.assertFalse(result);
    }

    private Game setUpGame() {
        Game game = new Game();
        game.setId(1);
        game.setFirstUserId(1);
        game.setSecondUserId(2);
        game.setCurrentTurn(UserTurn.FIRST_USER);
        return game;
    }
}
