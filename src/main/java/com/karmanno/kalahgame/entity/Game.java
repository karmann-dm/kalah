package com.karmanno.kalahgame.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.karmanno.kalahgame.service.KalahBoard;
import com.karmanno.kalahgame.util.StatusMapper;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Data
@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "first_user")
    private Integer firstUserId;

    @Column(name = "second_user")
    private Integer secondUserId;

    @Column(name = "current_turn")
    private Integer currentTurn;

    @Column(name = "status")
    private String status;

    @Transient
    private KalahBoard kalahBoard;

    public void move(int pitId) {
        if (currentTurn == 1) {
            boolean result = kalahBoard.firstUserMoves(pitId);
            if (!result) {
                currentTurn = 2;
            }
        }
        if (currentTurn == 2) {
            boolean result = kalahBoard.secondUserMoves(pitId);
            if (!result) {
                currentTurn = 1;
            }
        }
    }

    @PrePersist
    public void prePersist() {
        kalahBoard = KalahBoard.init();
    }

    @PreUpdate
    public void preUpdate() throws JsonProcessingException {
        kalahBoard = KalahBoard.load(
                StatusMapper.stringToMap(status)
        );
    }
}
