package com.karmanno.kalahgame.unit;

import com.karmanno.kalahgame.config.security.CustomUserDetailsService;
import com.karmanno.kalahgame.config.security.UserPrincipal;
import com.karmanno.kalahgame.entity.User;
import com.karmanno.kalahgame.exception.UserNotFoundException;
import com.karmanno.kalahgame.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CustomUserDetailsServiceTest {
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        customUserDetailsService = new CustomUserDetailsService(userRepository);
    }

    @Test
    @DisplayName("Should load user by id")
    public void loadUserById() {
        // given:
        User user = new User(1, "user", "pass");
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        // when:
        UserPrincipal userDetails = (UserPrincipal) customUserDetailsService.loadUserById(1);

        // then:
        Assert.assertEquals(user.getId(), userDetails.getId());
        Assert.assertEquals(user.getUsername(), userDetails.getUsername());
        Assert.assertEquals(user.getPassword(), userDetails.getPassword());
    }

    @Test(expected = UserNotFoundException.class)
    @DisplayName("Should throw exception while loading")
    public void loadUserByIdNegative() {
        // given:
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // when:
        customUserDetailsService.loadUserById(1);
    }

    @Test
    @DisplayName("Should load user by username")
    public void loadUserByUsername() {
        // given:
        User user = new User(1, "user", "pass");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        // when:
        UserPrincipal userDetails = (UserPrincipal) customUserDetailsService.loadUserByUsername("user");

        // then:
        Assert.assertEquals(user.getId(), userDetails.getId());
        Assert.assertEquals(user.getUsername(), userDetails.getUsername());
        Assert.assertEquals(user.getPassword(), userDetails.getPassword());
    }

    @Test(expected = UserNotFoundException.class)
    @DisplayName("Should throw exception while loading")
    public void loadUserByUsernameNegative() {
        // given:
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        // when:
        customUserDetailsService.loadUserByUsername("user");
    }
}
