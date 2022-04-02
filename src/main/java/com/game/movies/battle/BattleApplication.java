package com.game.movies.battle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BattleApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattleApplication.class, args);
	}

}
