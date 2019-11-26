package com.karmanno.kalahgame.web;

import com.karmanno.kalahgame.service.UserService;
import com.karmanno.kalahgame.web.dto.AuthRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/signup")
    public void register(@RequestBody AuthRequest request) {

    }

    @PostMapping("/login")
    public void login() {

    }
}
