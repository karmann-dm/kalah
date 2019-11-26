package com.karmanno.kalahgame.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JoinGameResponse {
    private boolean success;
}
