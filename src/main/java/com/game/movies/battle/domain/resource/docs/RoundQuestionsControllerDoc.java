package com.game.movies.battle.domain.resource.docs;

import com.game.movies.battle.domain.dto.AnswerQuestionDto;
import com.game.movies.battle.domain.dto.SequenceMoviesRoundDto;
import com.game.movies.battle.domain.entity.SequenceMoviesRound;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Round Questions")
public interface RoundQuestionsControllerDoc {

    @ApiOperation("Current Quiz of Round per user")
    public SequenceMoviesRoundDto currentQuiz(Long roundId);

    @ApiOperation("Send Answer about the Quiz")
    public SequenceMoviesRound sendAnswer(Long roundId, Long questionId, AnswerQuestionDto answerQuestionDto);

}
