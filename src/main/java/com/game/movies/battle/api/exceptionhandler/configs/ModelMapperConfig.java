package com.game.movies.battle.api.exceptionhandler.configs;

import com.game.movies.battle.domain.converters.RoundConverter;
import feign.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.addConverter(new RoundConverter());
        return modelMapper;
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
