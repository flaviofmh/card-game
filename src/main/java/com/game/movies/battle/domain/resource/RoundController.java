package com.game.movies.battle.domain.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.game.movies.battle.domain.dto.RankingResponse;
import com.game.movies.battle.domain.dto.RoundSaveResponseDto;
import com.game.movies.battle.domain.dto.StartRoundDto;
import com.game.movies.battle.domain.entity.Player;
import com.game.movies.battle.domain.entity.Round;
import com.game.movies.battle.domain.resource.docs.RoundControllerDoc;
import com.game.movies.battle.domain.service.PlayerService;
import com.game.movies.battle.domain.service.RoundServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("round")
public class RoundController implements RoundControllerDoc {

    @Autowired
    private RoundServiceImpl roundServiceImpl;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoundSaveResponseDto startRound(@RequestBody @Valid StartRoundDto startRoundDto) throws JsonProcessingException {
        Player playerLoad = playerService.getPlayerId(startRoundDto.getPlayerId());
        Round round = roundServiceImpl.startGame(playerLoad, startRoundDto.getType(), startRoundDto.getBaseTitle());

        RoundSaveResponseDto roundSaveResponseDto = modelMapper.map(round, RoundSaveResponseDto.class);

        return roundSaveResponseDto;
    }

    @DeleteMapping("/{roundId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void stopRound(@PathVariable Long roundId) {
        roundServiceImpl.stop(roundId);
    }

    @GetMapping("/{roundId}")
    @ResponseStatus(HttpStatus.OK)
    public Round getGameById(@PathVariable Long roundId) {
        return roundServiceImpl.getRoundById(roundId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Round> getAll() {
        return roundServiceImpl.getAll();
    }

    @GetMapping("/ranking")
    @ResponseStatus(HttpStatus.OK)
    public List<RankingResponse> getRankingUsersOrdered() {
        var ranking = roundServiceImpl.getAllRoundsOrderedByScore();
        var rankingResponse = ranking.stream()
                .map(source -> modelMapper.map(source, RankingResponse.class))
                .collect(Collectors.toList());
        return rankingResponse;
    }

}
