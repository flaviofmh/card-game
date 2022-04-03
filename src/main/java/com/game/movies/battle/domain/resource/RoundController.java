package com.game.movies.battle.domain.resource;

import com.game.movies.battle.domain.dto.StartRoundDto;
import com.game.movies.battle.domain.entity.Player;
import com.game.movies.battle.domain.entity.Round;
import com.game.movies.battle.domain.service.PlayerService;
import com.game.movies.battle.domain.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("round")
public class RoundController {

    @Autowired
    private RoundService roundService;

    @Autowired
    private PlayerService playerService;

    @PostMapping
    public Round startRound(@RequestBody StartRoundDto startRoundDto) {
        Player playerLoad = playerService.getPlayerId(startRoundDto.getPlayerId());

        return roundService.startGame(playerLoad);
    }

}
