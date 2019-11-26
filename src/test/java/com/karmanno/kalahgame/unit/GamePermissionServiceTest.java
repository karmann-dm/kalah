package com.karmanno.kalahgame.unit;

import com.karmanno.kalahgame.entity.Game;
import com.karmanno.kalahgame.entity.User;
import com.karmanno.kalahgame.repository.GameRepository;
import com.karmanno.kalahgame.service.GamePermissionsService;
import com.karmanno.kalahgame.service.impl.GamePermissionsServiceImpl;
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
    public void canUserMoveTest() {
        // given:
        Game game = new Game();
        when(gameRepository.save(game)).thenReturn(game);
        // when:
        // then:
    }

    @Test
    @DisplayName("Should check if user has access")
    public void hasUserAccessTest() {

    }
}
