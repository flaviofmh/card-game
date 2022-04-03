package com.game.movies.battle.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
public class SequenceMoviesRound extends BaseEntity {

    @ManyToOne
    @JsonBackReference
    private Round round;

    private String idFirstMovie;

    private String idSecondMovie;

    private String answer;

    private Boolean answerCorrect;

    private Integer orderSequence;

}
