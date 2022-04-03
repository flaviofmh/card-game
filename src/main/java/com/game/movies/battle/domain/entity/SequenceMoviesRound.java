package com.game.movies.battle.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
public class SequenceMoviesRound extends BaseEntity {

    @ManyToOne
    private Round round;

    private String idFirstMovie;

    private String idSecondMovie;

    private String answer;

    private Integer answerScore;

    private boolean answerCorrect;

}
