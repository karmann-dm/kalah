package com.karmanno.kalahgame.util;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class UrlBuilder {
    public String buildUrl(String entity, String id) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/{entity}/{gameId}")
                .buildAndExpand(
                        entity,
                        id
                ).toUriString();
    }
}
