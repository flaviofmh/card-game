package com.game.movies.battle.domain.service;

import com.game.movies.battle.domain.dto.SequenceMoviesRoundDto;
import com.game.movies.battle.infrastructure.dto.MovieDetails;
import com.game.movies.battle.infrastructure.dto.MovieItem;
import com.game.movies.battle.infrastructure.dto.TopMovieDto;
import com.game.movies.battle.infrastructure.integration.MoviesIMDB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MovieService {

    @Autowired
    private MoviesIMDB moviesIMDB;

    public List<TopMovieDto> getTopMovies() {
        MovieItem topMovies = moviesIMDB.getTopMoviesIMDB();
        return topMovies.getItems();
    }

    public void populateDetails(final SequenceMoviesRoundDto sequenceMoviesRoundDto) {
        {
            MovieDetails detailMovieIMDB = moviesIMDB.getDetailMovieIMDB(sequenceMoviesRoundDto.getIdFirstMovie());
            sequenceMoviesRoundDto.setTitleFirstMovie(detailMovieIMDB.getFullTitle());
        }
        {
            MovieDetails detailMovieIMDB = moviesIMDB.getDetailMovieIMDB(sequenceMoviesRoundDto.getIdSecondMovie());
            sequenceMoviesRoundDto.setTitleSecondMovie(detailMovieIMDB.getFullTitle());
        }
    }
}
