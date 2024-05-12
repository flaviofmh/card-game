package com.game.movies.battle.domain.entity;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class SequenceMoviesRound extends BaseEntity {

    private String idFirstMovie;

    private String idSecondMovie;

    private String answer;

    private boolean pendingAnswer = true;

    private Integer score;

    private Integer orderSequence;

}
