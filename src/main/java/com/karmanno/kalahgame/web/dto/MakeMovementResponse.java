package com.karmanno.kalahgame.web.dto;

import lombok.Data;

import java.util.Map;

@Data
public class MakeMovementResponse {
    private String id;
    private String url;
    private Map<Integer, Integer> pits;
}
