package com.game.movies.battle.infrastructure.message;

import com.game.movies.battle.domain.dto.NextQuestion;
import com.game.movies.battle.domain.entity.Round;
import com.game.movies.battle.infrastructure.contract.RoundService;
import com.game.movies.battle.infrastructure.contract.SequenceMoviesRoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InternalConsumer {

    @Autowired
    private SequenceMoviesRoundService sequenceMoviesRoundService;

    @Autowired
    private RoundService roundService;

    @KafkaListener(groupId = "my-game-id", topics = "create-new-question", containerFactory = "kafkaListenerContainerFactory")
    public void pollResults(NextQuestion nextQuestion) {
        Round round = roundService.getRoundById(nextQuestion.getRoundId());
        sequenceMoviesRoundService.currentQuiz(round);
    }

}
