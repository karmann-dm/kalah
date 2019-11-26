package com.karmanno.kalahgame.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class LoginResponse {
    private String token;
    private Date expirationData;
}
