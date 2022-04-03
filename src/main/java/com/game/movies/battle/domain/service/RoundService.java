package com.game.movies.battle.domain.service;

import com.game.movies.battle.api.exceptionhandler.exception.ExistsGameException;
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

        boolean exists = roundRepository.existsByPlayerIdAndFinishedFalse(player.getId());

        if (exists) {
            throw new ExistsGameException(
                    String.format("Já existe um jogo em andamento para o Player %s", player.getFullName()));
        }

        Round round = new Round();
        round.setPlayer(player);
        round.setFinished(false);
        round.setAttempts(0);
        round.setTotalScore(0);

        return roundRepository.save(round);
    }

}
