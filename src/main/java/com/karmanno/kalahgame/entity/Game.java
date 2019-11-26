package com.karmanno.kalahgame.entity;

import com.karmanno.kalahgame.service.KalahBoard;
import lombok.Data;

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

    @Enumerated
    @Column(name = "current_turn")
    private UserTurn currentTurn;

    @Column(name = "status")
    private String status;
}
