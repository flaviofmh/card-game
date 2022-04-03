package com.game.movies.battle.domain.service;

import com.game.movies.battle.domain.dto.SequenceMoviesRoundDto;
import com.game.movies.battle.domain.entity.Round;
import com.game.movies.battle.domain.entity.SequenceMoviesRound;
import com.game.movies.battle.domain.repository.RoundRepository;
import com.game.movies.battle.infrastructure.dto.TopMovieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

@Service
public class SequenceMoviesRoundService {

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

        final List<TopMovieDto> topMovies = movieService.getTopMovies();

        SequenceMoviesRound nextQuestionQuiz = createNextQuestionQuiz(currentRound, topMovies);

        roundRepository.saveAndFlush(currentRound);
        return currentRound.getSequenceMoviesRounds().get(currentRound.getSequenceMoviesRounds().size()-1);
    }

    private SequenceMoviesRound createNextQuestionQuiz(Round currentRound, List<TopMovieDto> topMovies) {
        Random rand = new Random();
        SequenceMoviesRound sequenceMoviesRound = null;
        for (int i = 0; i < topMovies.size(); i++) {

            int randomIndex = rand.nextInt(topMovies.size());
            TopMovieDto firstMovie = topMovies.get(randomIndex);

            randomIndex = rand.nextInt(topMovies.size());
            TopMovieDto secondMovie = topMovies.get(randomIndex);

            if (checkElementDuplicated(currentRound.getSequenceMoviesRounds(), firstMovie.getId(), secondMovie.getId())) {
                topMovies.remove(randomIndex);
                i = 0;
                continue;
            }

            sequenceMoviesRound = new SequenceMoviesRound();
            sequenceMoviesRound.setIdFirstMovie(firstMovie.getId());
            sequenceMoviesRound.setIdSecondMovie(secondMovie.getId());
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
        movieService.populateDetails(sequenceMoviesRoundDto);
    }
}
