package com.game.movies.battle.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Round extends BaseEntity {

    @ManyToOne
    private Player player;

    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SequenceMoviesRound> sequenceMoviesRounds;

    private Integer attempts;

    private Integer totalScore;

    private boolean finished;

}
