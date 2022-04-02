package com.game.movies.battle.infrastructure.integration;

import com.game.movies.battle.infrastructure.dto.MovieItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "imdb", url = "${extern.imdb.baseUrl}")
public interface MoviesIMDB {

    @RequestMapping(method = RequestMethod.GET, value = "${extern.imdb.movies.top250.url}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    MovieItem getTopMoviesIMDB();

}
