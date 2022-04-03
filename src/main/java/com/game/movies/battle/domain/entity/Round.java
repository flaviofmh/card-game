package com.game.movies.battle.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Round extends BaseEntity {

    @ManyToOne
    private Player player;

    private Integer number;

    private String idFirstMovie;

    private String idSecondMovie;

}
