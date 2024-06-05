package com.game.movies.battle.api.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.movies.battle.domain.dto.NextQuestion;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class NextQuestionDeserializer implements Deserializer<NextQuestion> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public NextQuestion deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, NextQuestion.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize NextQuestion", e);
        }
    }

    @Override
    public void close() {
    }
}

