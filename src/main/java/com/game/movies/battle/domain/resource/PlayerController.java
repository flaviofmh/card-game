package com.game.movies.battle.domain.resource;

import com.game.movies.battle.domain.entity.Player;
import com.game.movies.battle.domain.resource.docs.PlayerControllerDoc;
import com.game.movies.battle.domain.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("player")
public class PlayerController implements PlayerControllerDoc {

    @Autowired
    private PlayerService playerService;

    @GetMapping
    public List<Player> allPlayers() {
        return playerService.getAllPlayers();
    }

}
