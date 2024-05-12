package com.game.movies.battle.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImodbMovieDto {

    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year")
    private Integer year;
    private String imdbID;
    @JsonProperty("Type")
    private String type;

}
