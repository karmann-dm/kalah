package com.karmanno.kalahgame.web;

import com.karmanno.kalahgame.config.security.CurrentUser;
import com.karmanno.kalahgame.config.security.UserPrincipal;
import com.karmanno.kalahgame.service.GameService;
import com.karmanno.kalahgame.util.GameConverter;
import com.karmanno.kalahgame.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final GameConverter gameConverter;

    @PostMapping("")
    public ResponseEntity<CreateGameResponse> createGame(@CurrentUser UserPrincipal userPrincipal) {
        CreateGameResponse response = gameConverter.convertGameCreated(
                gameService.create(userPrincipal.getId())
        );
        return ResponseEntity.created(URI.create(response.getUrl())).body(response);
    }

    @GetMapping("")
    public ResponseEntity<MyGamesResponse> myGames(@CurrentUser UserPrincipal userPrincipal) {
        List<GameResponse> gameResponses = gameService.fetchGames(userPrincipal.getId())
                .stream()
                .map(game ->
                        GameResponse.builder()
                                .id(game.getId())
                                .firstUserId(game.getFirstUserId())
                                .secondUserId(game.getSecondUserId())
                                .status(game.getStatus())
                                .build())
                .collect(Collectors.toList());
        MyGamesResponse myGamesResponse = MyGamesResponse.builder().games(gameResponses).build();
        return ResponseEntity.ok(myGamesResponse);
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
    public ResponseEntity<ApiResponse> joinGame(
            @PathVariable Integer gameId,
            @CurrentUser UserPrincipal userPrincipal
    ) {
        gameService.joinGame(gameId, userPrincipal.getId());
        return ResponseEntity.ok(ApiResponse.builder().success(true).build());
    }
}
