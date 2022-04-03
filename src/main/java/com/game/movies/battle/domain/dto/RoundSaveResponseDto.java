package com.game.movies.battle.domain.dto;

import com.game.movies.battle.domain.entity.Player;
import com.game.movies.battle.domain.entity.SequenceMoviesRound;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoundSaveResponseDto implements Serializable {

    private Long id;
    private PlayerResponseDto player;
    private List<SequenceMoviesRound> sequenceMoviesRounds;
    private Integer attempts;
    private Integer totalScore;

}
