package com.game.movies.battle.domain.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MovieType {
    MOVIE("movie"),
    SERIES("series"),
    EPISODE("episode");

    private final String type;
}
