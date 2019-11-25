package com.karmanno.kalahgame.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlBuilder {
    @Value("${server.port}")
    private Integer port;

    public String buildUrl(String entity, String id) {
        return String.format(
                "http://%s:%s/%s/%s",
                "somehost",
                port,
                entity,
                id
        );
    }
}
