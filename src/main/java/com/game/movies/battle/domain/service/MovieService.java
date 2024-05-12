package com.game.movies.battle.domain.service;

import com.game.movies.battle.infrastructure.dto.MovieItemList;
import com.game.movies.battle.infrastructure.dto.ImodbMovieDto;
import com.game.movies.battle.infrastructure.dto.ParamsFilter;
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

    public List<ImodbMovieDto> getMoviesByTitleAndType(String title, String type) {
        var params = ParamsFilter.builder().type(type).s(title).build();
        MovieItemList topMovies = moviesIMDB.getMoviesByParameter(params);
        return topMovies.getSearch();
    }
}
