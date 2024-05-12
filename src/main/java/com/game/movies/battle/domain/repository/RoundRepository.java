package com.game.movies.battle.domain.repository;

import com.game.movies.battle.domain.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {

    boolean existsByPlayerIdAndFinishedFalse(Long playerId);

    Optional<Round> findByIdAndFinishedFalse(Long playerId);

    Optional<Round> findByIdAndPlayerId(Long roundId, Long playerId);

    List<Round> findByOrderByTotalScoreDesc();

}
