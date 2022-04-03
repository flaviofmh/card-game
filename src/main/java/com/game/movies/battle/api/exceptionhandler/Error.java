package com.game.movies.battle.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Error {

    private Integer status;
    private String type;
    private String title;
    private String detail;

}