package com.game.movies.battle.domain.entity;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Player extends BaseEntity {

    private String nickName;

    private String fullName;

}
