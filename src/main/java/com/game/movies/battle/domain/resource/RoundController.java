package com.game.movies.battle.domain.resource;

import com.game.movies.battle.domain.dto.RoundSaveResponseDto;
import com.game.movies.battle.domain.dto.StartRoundDto;
import com.game.movies.battle.domain.entity.Player;
import com.game.movies.battle.domain.entity.Round;
import com.game.movies.battle.domain.resource.docs.RoundControllerDoc;
import com.game.movies.battle.domain.service.PlayerService;
import com.game.movies.battle.domain.service.RoundService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("round")
public class RoundController implements RoundControllerDoc {

    @Autowired
    private RoundService roundService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoundSaveResponseDto startRound(@RequestBody StartRoundDto startRoundDto) {
        Player playerLoad = playerService.getPlayerId(startRoundDto.getPlayerId());
        Round round = roundService.startGame(playerLoad);

        RoundSaveResponseDto roundSaveResponseDto = modelMapper.map(round, RoundSaveResponseDto.class);

        return roundSaveResponseDto;
    }

    @DeleteMapping("/{roundId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void stopRound(@PathVariable Long roundId) {
        roundService.stop(roundId);
    }

    @GetMapping("/{roundId}")
    @ResponseStatus(HttpStatus.OK)
    public Round getGameById(@PathVariable Long roundId) {
        return roundService.getRoundById(roundId);
    }

}
