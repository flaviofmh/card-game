package com.game.movies.battle.domain.service;

import com.game.movies.battle.domain.entity.Player;
import com.game.movies.battle.domain.entity.Round;
import com.game.movies.battle.domain.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoundService {

    @Autowired
    private RoundRepository roundRepository;

    public Round startGame(Player player) {
        Round round = new Round();
        round.setPlayer(player);
        round.setFinished(false);

        return roundRepository.save(round);
    }

}
