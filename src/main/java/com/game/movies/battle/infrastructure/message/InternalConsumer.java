package com.game.movies.battle.infrastructure.message;

import com.game.movies.battle.domain.entity.Round;
import com.game.movies.battle.infrastructure.contract.SequenceMoviesRoundService;
import com.game.movies.battle.infrastructure.dto.NextQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class InternalConsumer {

    @Autowired
    private SequenceMoviesRoundService sequenceMoviesRoundService;

    @KafkaListener(id = "pollResults", topics = "create-new-question", containerFactory = "kafkaListenerContainerFactory")
    public void pollResults(NextQuestion nextQuestion) {
        sequenceMoviesRoundService.currentQuiz(new Round());
    }

}
