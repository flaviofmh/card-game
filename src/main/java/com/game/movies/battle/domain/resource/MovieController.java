package com.game.movies.battle.domain.resource;

import com.game.movies.battle.domain.service.MovieService;
import com.game.movies.battle.infrastructure.dto.TopMovieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<TopMovieDto> getAllTopMovies() {
        return movieService.getTopMovies();
    }

}
