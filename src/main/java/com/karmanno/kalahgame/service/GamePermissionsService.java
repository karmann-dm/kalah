package com.karmanno.kalahgame.service;

public interface GamePermissionsService {
    boolean canUserMove(Integer userId, Integer gameId);
    boolean hasUserAccess(Integer userId, Integer gameId);
}
