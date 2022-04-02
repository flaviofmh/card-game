package com.game.movies.battle.domain.service;

import com.game.movies.battle.infrastructure.dto.MovieItem;
import com.game.movies.battle.infrastructure.dto.TopMovieDto;
import com.game.movies.battle.infrastructure.integration.MoviesIMDB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {

    private MoviesIMDB moviesIMDB;

    public List<TopMovieDto> getTopMovies() {
        MovieItem topMovies = moviesIMDB.getTopMoviesIMDB();
        return topMovies.getItems();
    }
}
