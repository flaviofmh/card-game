package com.game.movies.battle.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NextQuestion {

    private String version;
    private Long userId;
    private Long roundId;

}
