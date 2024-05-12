package com.game.movies.battle.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterByMovieId {

    @JsonProperty("i")
    private String i;

}
