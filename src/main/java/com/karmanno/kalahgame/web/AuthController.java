package com.karmanno.kalahgame.web;

import com.karmanno.kalahgame.config.security.JwtTokenProvider;
import com.karmanno.kalahgame.service.UserService;
import com.karmanno.kalahgame.web.dto.ApiResponse;
import com.karmanno.kalahgame.web.dto.AuthRequest;
import com.karmanno.kalahgame.web.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> register(@RequestBody AuthRequest request) {
        if (userService.existsByUsername(request.getUsername())) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(false)
                    .message("User has already registered").build()
            );
        }
        userService.register(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        return ResponseEntity.ok(ApiResponse.builder().success(true).build());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(
                jwtTokenProvider.generateToken(authentication)
        );
    }
}
