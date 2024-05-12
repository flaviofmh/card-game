package com.game.movies.battle.domain.service;

import com.game.movies.battle.api.exceptionhandler.exception.EntityNotFoundException;
import com.game.movies.battle.api.exceptionhandler.exception.ExistsGameException;
import com.game.movies.battle.domain.entity.Player;
import com.game.movies.battle.domain.entity.Round;
import com.game.movies.battle.domain.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RoundService {

    public static final String MESSAGE_ROUND_ID = "Nã há um jogo em andamento de codigo %d";

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

    public void stop(Long roundId) {
        Round roundCurrent = roundRepository.findByIdAndFinishedFalse(roundId).orElseThrow(() -> new ExistsGameException(
                String.format(MESSAGE_ROUND_ID, roundId)));

        roundCurrent.setFinished(true);
        roundRepository.save(roundCurrent);
    }

    public Round getRoundById(Long roundId) {
        Round round = roundRepository.findById(roundId).orElseThrow(() -> new EntityNotFoundException(
                String.format(MESSAGE_ROUND_ID, roundId)));

        return round;
    }

    public List<Round> getAll() {
        return roundRepository.findAll();
    }
}
