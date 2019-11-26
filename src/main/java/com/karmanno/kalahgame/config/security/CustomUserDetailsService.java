package com.karmanno.kalahgame.config.security;

import com.karmanno.kalahgame.entity.User;
import com.karmanno.kalahgame.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s).orElseThrow(
                () -> new RuntimeException("User with name = " + s + " not found")
        );
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User with id = " + id + " not found")
        );
        return UserPrincipal.create(user);
    }
}
