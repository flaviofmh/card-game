package com.game.movies.battle.infrastructure.contract;

import com.game.movies.battle.domain.dto.AnswerQuestionDto;
import com.game.movies.battle.domain.dto.SequenceMoviesRoundDto;
import com.game.movies.battle.domain.entity.Round;
import com.game.movies.battle.domain.entity.SequenceMoviesRound;

public interface SequenceMoviesRoundService {

    SequenceMoviesRound currentQuiz(final Round currentRound);
    SequenceMoviesRound getSequenceMoviesRoundById(final Round round, Long sequenceMoviesRoundId);
    SequenceMoviesRound answerQuestion(final SequenceMoviesRound sequenceMoviesRoundCurrent,
                                       AnswerQuestionDto answerQuestionDto);
    void setNameMovies(SequenceMoviesRoundDto sequenceMoviesRoundDto);

}
