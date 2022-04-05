package com.game.movies.battle.infrastructure.integration;

import com.game.movies.battle.infrastructure.dto.MovieDetails;
import com.game.movies.battle.infrastructure.dto.MovieItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "imdb", url = "${extern.imdb.baseUrl}")
public interface MoviesIMDB {

    @RequestMapping(method = RequestMethod.GET, value = "${extern.imdb.movies.top250.url}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    MovieItem getTopMoviesIMDB();

    @RequestMapping(method = RequestMethod.GET, value = "${extern.imdb.movies.title.url}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    MovieDetails getDetailMovieIMDB(@PathVariable("id") String id);

    @RequestMapping(method = RequestMethod.GET, value = "${extern.imdb.movies.userratings.url}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    MovieDetails getRatingMovieIMDB(@PathVariable("id") String id);
}
