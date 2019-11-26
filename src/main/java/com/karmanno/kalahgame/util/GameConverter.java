package com.karmanno.kalahgame.util;

import com.karmanno.kalahgame.entity.Game;
import com.karmanno.kalahgame.web.dto.CreateGameResponse;
import com.karmanno.kalahgame.web.dto.MakeMovementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameConverter {
    private static final String ENTITY = "games";
    private final UrlBuilder urlBuilder;

    public CreateGameResponse convertGameCreated(Game game) {
        CreateGameResponse response = new CreateGameResponse();
        response.setId(game.getId().toString());
        response.setUrl(urlBuilder.buildUrl(ENTITY, response.getId()));
        return response;
    }

    public MakeMovementResponse convertMovement(Game game) {
        MakeMovementResponse response = new MakeMovementResponse();
        response.setId(game.getId().toString());
        response.setUrl(urlBuilder.buildUrl(ENTITY, response.getId()));
        response.setPits(game.getStatus());
        return response;
    }
}
