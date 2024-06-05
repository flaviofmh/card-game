package com.game.movies.battle.domain.resource;

import com.game.movies.battle.domain.dto.AnswerQuestionDto;
import com.game.movies.battle.domain.dto.SequenceMoviesRoundDto;
import com.game.movies.battle.domain.entity.Round;
import com.game.movies.battle.domain.entity.SequenceMoviesRound;
import com.game.movies.battle.domain.resource.docs.RoundQuestionsControllerDoc;
import com.game.movies.battle.domain.service.RoundServiceImpl;
import com.game.movies.battle.domain.service.SequenceMoviesRoundServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("round/{roundId}/quiz")
public class RoundQuestionsController implements RoundQuestionsControllerDoc {

    @Autowired
    private SequenceMoviesRoundServiceImpl sequenceMoviesRoundServiceImpl;

    @Autowired
    private RoundServiceImpl roundServiceImpl;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public SequenceMoviesRoundDto currentQuiz(@PathVariable Long roundId) {
        final Round round = roundServiceImpl.getRoundById(roundId);
        roundServiceImpl.roundHasFinished(round);

        SequenceMoviesRound sequenceMoviesRound = sequenceMoviesRoundServiceImpl.currentQuiz(round);

        final SequenceMoviesRoundDto sequenceMoviesRoundDto = modelMapper.map(sequenceMoviesRound, SequenceMoviesRoundDto.class);

        sequenceMoviesRoundServiceImpl.setNameMovies(sequenceMoviesRoundDto);

        return sequenceMoviesRoundDto;
    }

    @PutMapping("{questionId}")
    public SequenceMoviesRound sendAnswer(@PathVariable Long roundId, @PathVariable Long questionId,
                                             @RequestBody AnswerQuestionDto answerQuestionDto) {

        final Round currentRound = roundServiceImpl.getRoundByIdAndPlayerId(roundId, answerQuestionDto.getPlayerId());
        final SequenceMoviesRound sequenceMoviesRoundCurrent =
                sequenceMoviesRoundServiceImpl.getSequenceMoviesRoundById(currentRound, questionId);

        var response = sequenceMoviesRoundServiceImpl.answerQuestion(sequenceMoviesRoundCurrent, answerQuestionDto);
        roundServiceImpl.updateAttempts(currentRound, sequenceMoviesRoundCurrent.getId());

        return response;
    }

}
