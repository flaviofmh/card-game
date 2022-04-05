package com.game.movies.battle.infrastructure.dto;

import lombok.Data;

@Data
public class MovieDetails {

    private String imDbId;
    private String fullTitle;
    private String year;
    private String releaseDate;
    private String totalRating;
    private String totalRatingVotes;

}
