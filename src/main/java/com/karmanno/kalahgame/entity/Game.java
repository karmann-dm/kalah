package com.karmanno.kalahgame.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @PrePersist
    public void prePersist() {
        currentTurn = UserTurn.randomTurn();
    }
}
