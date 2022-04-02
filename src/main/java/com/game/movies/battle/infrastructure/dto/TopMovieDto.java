package com.game.movies.battle.infrastructure.dto;

import lombok.Data;

@Data
public class TopMovieDto {

    private String id;
    private String rank;
    private String fullTitle;
    private Double imDbRating;
    private Long imDbRatingCount;

}
