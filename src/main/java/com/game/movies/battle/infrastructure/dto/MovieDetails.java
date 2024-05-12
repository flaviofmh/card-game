package com.game.movies.battle.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MovieDetails {

    private String imDbId;
    @JsonProperty("Title")
    private String fullTitle;
    @JsonProperty("Year")
    private String year;
    @JsonProperty("Released")
    private String releaseDate;
    @JsonProperty("imdbRating")
    private Double rating;
    @JsonProperty("imdbVotes")
    private String votes;

}
