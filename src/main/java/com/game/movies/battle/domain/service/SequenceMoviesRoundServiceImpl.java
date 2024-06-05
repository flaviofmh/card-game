package com.game.movies.battle.domain.service;

import com.game.movies.battle.api.exceptionhandler.exception.AnswerDoesntMatch;
import com.game.movies.battle.api.exceptionhandler.exception.EntityNotFoundException;
import com.game.movies.battle.domain.dto.AnswerQuestionDto;
import com.game.movies.battle.domain.dto.SequenceMoviesRoundDto;
import com.game.movies.battle.domain.entity.Round;
import com.game.movies.battle.domain.entity.SequenceMoviesRound;
import com.game.movies.battle.domain.repository.RoundRepository;
import com.game.movies.battle.infrastructure.contract.SequenceMoviesRoundService;
import com.game.movies.battle.infrastructure.dto.ImodbMovieDto;
import com.game.movies.battle.infrastructure.dto.MovieDetails;
import com.game.movies.battle.infrastructure.message.base.NextQuestionMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

@Transactional
@Service
public class SequenceMoviesRoundServiceImpl implements SequenceMoviesRoundService {

    public static final String MESSAGE_QUESTION_ID = "Não há quiz para esse código %d";

    @Autowired
    private MovieService movieService;

    @Autowired
    private RoundRepository roundRepository;

    public SequenceMoviesRound currentQuiz(final Round currentRound) {
        if (!CollectionUtils.isEmpty(currentRound.getSequenceMoviesRounds())) {
            Optional<SequenceMoviesRound> sequenceMoviesRoundCurrent = currentRound.getSequenceMoviesRounds().stream().filter(sequenceMoviesRound ->
                    sequenceMoviesRound.isPendingAnswer()).findFirst();

            if (sequenceMoviesRoundCurrent.isPresent()) {
                return sequenceMoviesRoundCurrent.get();
            }
        }
        return createNewSequenceMovies(currentRound);
    }

    private SequenceMoviesRound createNewSequenceMovies(final Round currentRound) {

        final List<ImodbMovieDto> topMovies = movieService.getMoviesByTitleAndType(currentRound.getTitle(), currentRound.getType());

        createNextQuestionQuiz(currentRound, topMovies);

        roundRepository.save(currentRound);
        var currentQuiz = currentRound.getSequenceMoviesRounds().stream().filter(sequenceMoviesRound -> sequenceMoviesRound.isPendingAnswer()).findFirst();
        return currentQuiz.get();
    }

    private void createNextQuestionQuiz(Round currentRound, List<ImodbMovieDto> topMovies) {
        Random rand = new Random();
        SequenceMoviesRound sequenceMoviesRound = null;
        for (int i = 0; i < topMovies.size(); i++) {

            Collections.shuffle(topMovies);
            ImodbMovieDto firstMovie = topMovies.get(i);

            ImodbMovieDto secondMovie = topMovies.get(i+1);

            if (checkElementDuplicated(currentRound.getSequenceMoviesRounds(), firstMovie.getImdbID(), secondMovie.getImdbID())) {
                topMovies.remove(i);
                topMovies.remove(i+1);
                i = 0;
                continue;
            }

            sequenceMoviesRound = new SequenceMoviesRound();
            sequenceMoviesRound.setIdFirstMovie(firstMovie.getImdbID());
            sequenceMoviesRound.setIdSecondMovie(secondMovie.getImdbID());
            sequenceMoviesRound.setScore(0);
            sequenceMoviesRound.setOrderSequence(currentRound.getSequenceMoviesRounds().size()+1);
            currentRound.getSequenceMoviesRounds().add(sequenceMoviesRound);
            break;
        }
    }

    private boolean checkElementDuplicated(List<SequenceMoviesRound> sequenceMoviesRounds,
                                           String firstMovieId, String secondMovieId) {

        Predicate<SequenceMoviesRound> sequenceMoviesRoundPredicateCompare = sequenceMoviesRound ->
                sequenceMoviesRound.getIdFirstMovie().equals(firstMovieId)
                        && sequenceMoviesRound.getIdSecondMovie().equals(secondMovieId);

        Predicate<SequenceMoviesRound> sequenceMoviesRoundPredicateCompareInverse = sequenceMoviesRound ->
                sequenceMoviesRound.getIdFirstMovie().equals(secondMovieId)
                        && sequenceMoviesRound.getIdSecondMovie().equals(firstMovieId);

        Optional<SequenceMoviesRound> first = sequenceMoviesRounds.stream().filter(sequenceMoviesRoundPredicateCompare).findFirst();
        Optional<SequenceMoviesRound> second = sequenceMoviesRounds.stream().filter(sequenceMoviesRoundPredicateCompareInverse).findFirst();

        return first.isPresent() || second.isPresent();
    }

    public SequenceMoviesRound getSequenceMoviesRoundById(final Round round, Long sequenceMoviesRoundId) {

        Predicate<SequenceMoviesRound> currentQuiz = sequenceMoviesRound ->
                sequenceMoviesRound.getId().equals(sequenceMoviesRoundId)
                        && sequenceMoviesRound.isPendingAnswer();

        final SequenceMoviesRound sequenceMoviesRoundLoad = round.getSequenceMoviesRounds().stream()
                .filter(currentQuiz)
                .findFirst().orElseThrow(() -> new EntityNotFoundException(
                        String.format(MESSAGE_QUESTION_ID, sequenceMoviesRoundId)));

        return sequenceMoviesRoundLoad;
    }

    public SequenceMoviesRound answerQuestion(final SequenceMoviesRound sequenceMoviesRoundCurrent,
                                                 AnswerQuestionDto answerQuestionDto) {

        MovieDetails firstMovieDetails = movieService.getMoviesByTitleAndType(sequenceMoviesRoundCurrent.getIdFirstMovie());
        MovieDetails SecondMovieDetails = movieService.getMoviesByTitleAndType(sequenceMoviesRoundCurrent.getIdSecondMovie());

        BigDecimal totalRatingFirst = BigDecimal.valueOf(Double.valueOf(firstMovieDetails.getRating()));
        BigDecimal totalRatingVotesFirst = BigDecimal.valueOf(Double.valueOf(firstMovieDetails.getVotes().replaceAll(",", "")));

        BigDecimal totalFirst = totalRatingFirst.multiply(totalRatingVotesFirst);

        BigDecimal totalRatingSecond = BigDecimal.valueOf(Double.valueOf(SecondMovieDetails.getRating().toString()));
        BigDecimal totalRatingVotesSecond = BigDecimal.valueOf(Double.valueOf(SecondMovieDetails.getVotes().replaceAll(",", "")));

        BigDecimal totalSecond = totalRatingSecond.multiply(totalRatingVotesSecond);

        if (!answerQuestionDto.getMovieId().equals(sequenceMoviesRoundCurrent.getIdFirstMovie()) &&
                !answerQuestionDto.getMovieId().equals(sequenceMoviesRoundCurrent.getIdSecondMovie())) {
            throw new AnswerDoesntMatch(String.format("A resposta fornecida é invalida para as opcoes apresentadas"));
        }

        if (answerQuestionDto.getMovieId().equals(sequenceMoviesRoundCurrent.getIdFirstMovie())) {
            if (totalFirst.compareTo(totalSecond) == 1) {
                sequenceMoviesRoundCurrent.setScore(sequenceMoviesRoundCurrent.getScore()+1);
            }
        }

        if (answerQuestionDto.getMovieId().equals(sequenceMoviesRoundCurrent.getIdSecondMovie())) {
            if (totalSecond.compareTo(totalFirst) == 1) {
                sequenceMoviesRoundCurrent.setScore(sequenceMoviesRoundCurrent.getScore()+1);
            }
        }

        sequenceMoviesRoundCurrent.setAnswer(answerQuestionDto.getMovieId());
        sequenceMoviesRoundCurrent.setPendingAnswer(false);
        return sequenceMoviesRoundCurrent;
    }

    public void setNameMovies(SequenceMoviesRoundDto sequenceMoviesRoundDto) {
        MovieDetails firstMovieDetails = movieService.getMoviesByTitleAndType(sequenceMoviesRoundDto.getIdFirstMovie());
        MovieDetails SecondMovieDetails = movieService.getMoviesByTitleAndType(sequenceMoviesRoundDto.getIdSecondMovie());

        sequenceMoviesRoundDto.setTitleFirstMovie(firstMovieDetails.getFullTitle());
        sequenceMoviesRoundDto.setTitleSecondMovie(SecondMovieDetails.getFullTitle());
    }
}
