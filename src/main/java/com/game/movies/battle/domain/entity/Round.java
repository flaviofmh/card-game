package com.game.movies.battle.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Round extends BaseEntity {

    @ManyToOne
    private Player player;

    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<SequenceMoviesRound> sequenceMoviesRounds;

    private Integer attempts;

    private boolean finished;

    private String type;

    private String title;

}
