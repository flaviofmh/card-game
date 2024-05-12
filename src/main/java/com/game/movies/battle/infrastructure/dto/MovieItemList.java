package com.game.movies.battle.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MovieItemList {

    @JsonProperty("Search")
    private List<ImodbMovieDto> search;
    private Long totalResults;
    @JsonProperty("Response")
    private String response;

}
