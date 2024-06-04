package com.game.movies.battle.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class NextQuestion {

    private String version;
    private String userId;
    private String roundId;

}
