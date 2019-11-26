package com.karmanno.kalahgame.repository;

import com.karmanno.kalahgame.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    @Query("from Game g where g.firstUserId = :userId or g.secondUserId = :userId")
    List<Game> fetchGamesByUserId(@Param("userId") Integer userId);
}
