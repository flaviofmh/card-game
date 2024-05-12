package com.game.movies.battle.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorType {

    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado, favor consultar documentação"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
    START_GAME("/round", "Erro Start Game"),
    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada");

    private String title;
    private String uri;

    ErrorType(String path, String title) {
        this.uri = "https://card-game.com.br" + path;
        this.title = title;
    }

}
