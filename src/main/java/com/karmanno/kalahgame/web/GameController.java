package com.karmanno.kalahgame.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.karmanno.kalahgame.converter.GameConverter;
import com.karmanno.kalahgame.service.GameService;
import com.karmanno.kalahgame.web.dto.CreateGameResponse;
import com.karmanno.kalahgame.web.dto.MakeMovementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final GameConverter gameConverter;

    @PostMapping("")
    public ResponseEntity<CreateGameResponse> createGame() {
        CreateGameResponse response = gameConverter.convertGameCreated(
                gameService.create()
        );
        return ResponseEntity.created(URI.create(response.getUrl())).body(response);
    }

    @PutMapping("/{gameId}/{pitId}")
    public ResponseEntity<MakeMovementResponse> makeMove(
            @PathVariable Integer gameId,
            @PathVariable Integer pitId
    ) {
        try {
            MakeMovementResponse response = gameConverter.convertMovement(
                    gameService.makeMovement(gameId, pitId)
            );
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
