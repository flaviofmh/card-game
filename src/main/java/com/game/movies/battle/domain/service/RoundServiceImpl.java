package com.game.movies.battle.domain.service;

import com.game.movies.battle.api.exceptionhandler.exception.EntityNotFoundException;
import com.game.movies.battle.api.exceptionhandler.exception.ExistsGameException;
import com.game.movies.battle.api.exceptionhandler.exception.RoundHasFinished;
import com.game.movies.battle.domain.dto.NextQuestion;
import com.game.movies.battle.domain.entity.Player;
import com.game.movies.battle.domain.entity.Round;
import com.game.movies.battle.domain.entity.SequenceMoviesRound;
import com.game.movies.battle.domain.repository.RoundRepository;
import com.game.movies.battle.infrastructure.contract.RoundService;
import com.game.movies.battle.infrastructure.message.base.NextQuestionMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class RoundServiceImpl implements RoundService {

    public static final String MESSAGE_ROUND_ID = "Nã há um jogo em andamento de codigo %d";

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private NextQuestionMessageProducer nextQuestionMessageProducer;

    public Round startGame(Player player, String type, String baseTitle) {

        boolean exists = roundRepository.existsByPlayerIdAndFinishedFalse(player.getId());

        if (exists) {
            throw new ExistsGameException(
                    String.format("Já existe um jogo em andamento para o Player %s", player.getFullName()));
        }

        Round round = new Round();
        round.setPlayer(player);
        round.setFinished(false);
        round.setAttempts(0);
        round.setType(type.toLowerCase());
        round.setTitle(baseTitle.toLowerCase());

        Round roundSaved = roundRepository.save(round);
        nextQuestionMessageProducer.sendMessage("create-new-question", new NextQuestion(
                "1", roundSaved.getPlayer().getId(), roundSaved.getId()));
        return roundSaved;
    }

    public void stop(Long roundId) {
        Round roundCurrent = roundRepository.findByIdAndFinishedFalse(roundId).orElseThrow(() -> new ExistsGameException(
                String.format(MESSAGE_ROUND_ID, roundId)));

        var totalScore = calcTotalScore(roundCurrent);

        roundCurrent.setFinished(true);
        roundCurrent.setTotalScore(totalScore);
        roundRepository.save(roundCurrent);
    }

    private Double calcTotalScore(Round roundCurrent) {

        var totalQuestions = (double) roundCurrent.getSequenceMoviesRounds().size();
        var totalRight = roundCurrent.getSequenceMoviesRounds().stream().mapToDouble(SequenceMoviesRound::getScore).sum();

        var percentRight = (totalRight / totalQuestions) * 100;
        var totalFinal = percentRight * totalRight;

        return totalFinal;
    }

    @Override
    public Round getRoundById(Long roundId) {
        Round round = roundRepository.findById(roundId).orElseThrow(() -> new EntityNotFoundException(
                String.format(MESSAGE_ROUND_ID, roundId)));

        return round;
    }

    public Round getRoundByIdAndPlayerId(Long roundId, Long playerId) {
        Round round = roundRepository.findByIdAndPlayerId(roundId, playerId).orElseThrow(() -> new EntityNotFoundException(
                String.format(MESSAGE_ROUND_ID, roundId)));

        return round;
    }

    public List<Round> getAll() {
        return roundRepository.findAll();
    }

    public List<Round> getAllRoundsOrderedByScore() {
        return roundRepository.findByOrderByTotalScoreDesc();
    }

    public void updateAttempts(Round currentRound, Long sequenceMoviesRoundId) {
        var sequenceMoviesRound = currentRound.getSequenceMoviesRounds().stream().filter(movies -> movies.getId().equals(sequenceMoviesRoundId)).findFirst();
        if (sequenceMoviesRound.isPresent()) {
            if (sequenceMoviesRound.get().getScore().equals(0)) {
                currentRound.setAttempts(currentRound.getAttempts()+1);
                if (currentRound.getAttempts().equals(3)) {
                    stop(currentRound.getId());
                }
            }
        }

        roundRepository.save(currentRound);
    }

    public void roundHasFinished(Round round) {
        if (round.isFinished()) {
            throw new RoundHasFinished(String.format("O round foi finalizado por maximas(%s) de tentativas erradas", round.getAttempts()));
        }
    }
}
