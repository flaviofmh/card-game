package com.game.movies.battle.infrastructure.contract;

import com.game.movies.battle.domain.entity.Round;

public interface RoundService {
    Round getRoundById(Long roundId);
}
