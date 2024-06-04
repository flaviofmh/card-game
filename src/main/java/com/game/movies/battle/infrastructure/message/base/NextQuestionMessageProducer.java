package com.game.movies.battle.infrastructure.message.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.movies.battle.infrastructure.dto.NextQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NextQuestionMessageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, NextQuestion nextQuestion) {
        ObjectMapper objectMapper = new ObjectMapper();
        String message = null;
        try {
            message = objectMapper.writeValueAsString(nextQuestion);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        kafkaTemplate.send("create-new-question", message);

        kafkaTemplate.send(topic, message);
    }

}
