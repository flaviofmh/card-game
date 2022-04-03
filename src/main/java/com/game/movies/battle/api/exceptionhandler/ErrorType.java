package com.game.movies.battle.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorType {

    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada");

    private String title;
    private String uri;

    ErrorType(String path, String title) {
        this.uri = "https://card-game.com.br" + path;
        this.title = title;
    }

}
