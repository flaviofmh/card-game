package com.game.movies.battle.infrastructure.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParamsFilter {
    private String type;
    private String s;
}
