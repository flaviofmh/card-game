package com.game.movies.battle.domain.resource;

import com.game.movies.battle.domain.dto.AnswerQuestionDto;
import com.game.movies.battle.domain.dto.SequenceMoviesRoundDto;
import com.game.movies.battle.domain.entity.Round;
import com.game.movies.battle.domain.entity.SequenceMoviesRound;
import com.game.movies.battle.domain.resource.docs.RoundQuestionsControllerDoc;
import com.game.movies.battle.domain.service.RoundService;
import com.game.movies.battle.domain.service.SequenceMoviesRoundService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("round/{roundId}/quiz")
public class RoundQuestionsController implements RoundQuestionsControllerDoc {

    @Autowired
    private SequenceMoviesRoundService sequenceMoviesRoundService;

    @Autowired
    private RoundService roundService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public SequenceMoviesRoundDto currentQuiz(@PathVariable Long roundId) {
        final Round round = roundService.getRoundById(roundId);
        roundService.roundHasFinished(round);

        SequenceMoviesRound sequenceMoviesRound = sequenceMoviesRoundService.currentQuiz(round);

        final SequenceMoviesRoundDto sequenceMoviesRoundDto = modelMapper.map(sequenceMoviesRound, SequenceMoviesRoundDto.class);

        sequenceMoviesRoundService.setNameMovies(sequenceMoviesRoundDto);

        return sequenceMoviesRoundDto;
    }

    @PutMapping("{questionId}")
    public SequenceMoviesRound sendAnswer(@PathVariable Long roundId, @PathVariable Long questionId,
                                             @RequestBody AnswerQuestionDto answerQuestionDto) {

        final Round currentRound = roundService.getRoundByIdAndPlayerId(roundId, answerQuestionDto.getPlayerId());
        final SequenceMoviesRound sequenceMoviesRoundCurrent =
                sequenceMoviesRoundService.getSequenceMoviesRoundById(currentRound, questionId);

        var response = sequenceMoviesRoundService.answerQuestion(sequenceMoviesRoundCurrent, answerQuestionDto);
        roundService.updateAttempts(currentRound, sequenceMoviesRoundCurrent.getId());

        return response;
    }

}
