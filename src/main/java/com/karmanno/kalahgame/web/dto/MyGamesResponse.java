package com.karmanno.kalahgame.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MyGamesResponse {
    private List<GameResponse> games;
}
