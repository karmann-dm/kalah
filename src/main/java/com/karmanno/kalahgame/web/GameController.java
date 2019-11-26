package com.karmanno.kalahgame.web;

import com.karmanno.kalahgame.config.security.CurrentUser;
import com.karmanno.kalahgame.config.security.UserPrincipal;
import com.karmanno.kalahgame.service.GameService;
import com.karmanno.kalahgame.util.GameConverter;
import com.karmanno.kalahgame.web.dto.CreateGameResponse;
import com.karmanno.kalahgame.web.dto.JoinGameResponse;
import com.karmanno.kalahgame.web.dto.MakeMovementResponse;
import lombok.RequiredArgsConstructor;
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
            @PathVariable Integer pitId,
            @CurrentUser UserPrincipal userPrincipal
    ) {
        MakeMovementResponse response = gameConverter.convertMovement(
                gameService.makeMovement(gameId, userPrincipal.getId(), pitId)
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<JoinGameResponse> joinGame(
            @PathVariable Integer gameId,
            @CurrentUser UserPrincipal userPrincipal
    ) {
        gameService.joinGame(gameId, userPrincipal.getId());
        return ResponseEntity.ok(JoinGameResponse.builder().success(true).build());
    }
}
