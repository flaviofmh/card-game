package com.game.movies.battle.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Round extends BaseEntity {

    @ManyToOne
    private Player player;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SequenceMoviesRound> sequenceMoviesRounds = new ArrayList<>();

    private Integer attempts;

    private boolean finished;

    private String type;

    private String title;

}
