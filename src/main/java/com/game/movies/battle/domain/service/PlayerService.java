package com.game.movies.battle.domain.service;

import com.game.movies.battle.api.exceptionhandler.exception.EntityNotFoundException;
import com.game.movies.battle.domain.entity.Player;
import com.game.movies.battle.domain.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerId(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new EntityNotFoundException(
                String.format("Não existe cadastro de player com o codigo %d", playerId)));
        return player;
    }
}
