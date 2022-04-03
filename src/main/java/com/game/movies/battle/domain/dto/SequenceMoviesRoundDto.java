package com.game.movies.battle.domain.dto;

import lombok.Data;

@Data
public class SequenceMoviesRoundDto {

    private Long id;

    private String idFirstMovie;

    private String titleFirstMovie;

    private String idSecondMovie;

    private String titleSecondMovie;

}
