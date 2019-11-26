package com.karmanno.kalahgame.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameResponse {
    private Integer id;
    private String status;
    private Integer firstUserId;
    private Integer secondUserId;
}
