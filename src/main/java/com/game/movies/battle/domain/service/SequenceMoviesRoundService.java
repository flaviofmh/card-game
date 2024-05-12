package com.game.movies.battle.domain.service;

import com.game.movies.battle.api.exceptionhandler.exception.EntityNotFoundException;
import com.game.movies.battle.domain.dto.AnswerQuestionDto;
import com.game.movies.battle.domain.dto.SequenceMoviesRoundDto;
import com.game.movies.battle.domain.entity.Round;
import com.game.movies.battle.domain.entity.SequenceMoviesRound;
import com.game.movies.battle.domain.repository.RoundRepository;
import com.game.movies.battle.infrastructure.dto.MovieDetails;
import com.game.movies.battle.infrastructure.dto.ImodbMovieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

@Service
public class SequenceMoviesRoundService {

    public static final String MESSAGE_QUESTION_ID = "Não há quiz para esse código %d";

    @Autowired
    private MovieService movieService;

    @Autowired
    private RoundRepository roundRepository;

    public SequenceMoviesRound currentQuiz(final Round currentRound) {
        if (!CollectionUtils.isEmpty(currentRound.getSequenceMoviesRounds())) {
            Optional<SequenceMoviesRound> sequenceMoviesRoundCurrent = currentRound.getSequenceMoviesRounds().stream().filter(sequenceMoviesRound ->
                    sequenceMoviesRound.getAnswer() == null).findFirst();

            if (sequenceMoviesRoundCurrent.isPresent()) {
                return sequenceMoviesRoundCurrent.get();
            }
        }
        return createNewSequenceMovies(currentRound);
    }

    private SequenceMoviesRound createNewSequenceMovies(final Round currentRound) {

        final List<ImodbMovieDto> topMovies = null;//movieService.getMoviesByTitleAndType();

        SequenceMoviesRound nextQuestionQuiz = createNextQuestionQuiz(currentRound, topMovies);

        roundRepository.saveAndFlush(currentRound);
        return currentRound.getSequenceMoviesRounds().get(currentRound.getSequenceMoviesRounds().size()-1);
    }

    private SequenceMoviesRound createNextQuestionQuiz(Round currentRound, List<ImodbMovieDto> topMovies) {
        Random rand = new Random();
        SequenceMoviesRound sequenceMoviesRound = null;
        for (int i = 0; i < topMovies.size(); i++) {

            int randomIndex = rand.nextInt(topMovies.size());
            ImodbMovieDto firstMovie = topMovies.get(randomIndex);

            randomIndex = rand.nextInt(topMovies.size());
            ImodbMovieDto secondMovie = topMovies.get(randomIndex);

            if (checkElementDuplicated(currentRound.getSequenceMoviesRounds(), firstMovie.getImdbID(), secondMovie.getImdbID())) {
                topMovies.remove(randomIndex);
                i = 0;
                continue;
            }

            sequenceMoviesRound = new SequenceMoviesRound();
            sequenceMoviesRound.setIdFirstMovie(firstMovie.getImdbID());
            sequenceMoviesRound.setIdSecondMovie(secondMovie.getImdbID());
            sequenceMoviesRound.setRound(currentRound);
            if (currentRound.getSequenceMoviesRounds() == null) {
                currentRound.setSequenceMoviesRounds(new ArrayList<>());
            }
            currentRound.getSequenceMoviesRounds().add(sequenceMoviesRound);
            break;
        }
        return sequenceMoviesRound;
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

        return first.isPresent() && second.isPresent();
    }

    public void populateDetailsMovie(SequenceMoviesRoundDto sequenceMoviesRoundDto) {
        //movieService.populateDetails(sequenceMoviesRoundDto);
    }

    public SequenceMoviesRound getSequenceMoviesRoundById(Round round, Long sequenceMoviesRoundId) {
        SequenceMoviesRound sequenceMoviesRoundLoad = round.getSequenceMoviesRounds().stream()
                .filter(sequenceMoviesRound -> sequenceMoviesRound.getId().equals(sequenceMoviesRoundId))
                .findFirst().orElseThrow(() -> new EntityNotFoundException(
                        String.format(MESSAGE_QUESTION_ID, sequenceMoviesRoundId)));

        return sequenceMoviesRoundLoad;
    }

    public SequenceMoviesRound answerQuestion(final SequenceMoviesRound sequenceMoviesRoundCurrent,
                                                 AnswerQuestionDto answerQuestionDto) {

        MovieDetails firstMovieDetails = null;//movieService.loadRatingMovie(sequenceMoviesRoundCurrent.getIdFirstMovie());
        MovieDetails SecondMovieDetails = null;//movieService.loadRatingMovie(sequenceMoviesRoundCurrent.getIdSecondMovie());

        BigDecimal totalRatingFirst = BigDecimal.valueOf(Double.valueOf(firstMovieDetails.getTotalRating().toString()));
        BigDecimal totalRatingVotesFirst = BigDecimal.valueOf(Double.valueOf(firstMovieDetails.getTotalRatingVotes().toString()));

        BigDecimal totalFirst = totalRatingFirst.multiply(totalRatingVotesFirst);

        BigDecimal totalRatingSecond = BigDecimal.valueOf(Double.valueOf(SecondMovieDetails.getTotalRating().toString()));
        BigDecimal totalRatingVotesSecond = BigDecimal.valueOf(Double.valueOf(SecondMovieDetails.getTotalRatingVotes().toString()));

        BigDecimal totalSecond = totalRatingSecond.multiply(totalRatingVotesSecond);

        if (answerQuestionDto.getMovieId().equals(firstMovieDetails.getImDbId())) {
            sequenceMoviesRoundCurrent.setAnswerCorrect(totalFirst.compareTo(totalSecond) == 1);
        } else {
            sequenceMoviesRoundCurrent.setAnswerCorrect(false);
        }
        sequenceMoviesRoundCurrent.setAnswer(answerQuestionDto.getMovieId());
        return sequenceMoviesRoundCurrent;
    }
}
