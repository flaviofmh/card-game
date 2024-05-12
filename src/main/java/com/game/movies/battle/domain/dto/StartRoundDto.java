package com.game.movies.battle.domain.dto;

import com.game.movies.battle.domain.dto.validators.EnumValidator;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class StartRoundDto {

    @NotNull
    @EnumValidator(enumClazz = MovieType.class, message = "Invalid type")
    private String type;
    @NotNull
    @NotBlank
    private String baseTitle;
    @NotNull
    private Long playerId;

}
