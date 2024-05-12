package com.game.movies.battle.infrastructure.integration;

import com.game.movies.battle.infrastructure.dto.FilterByMovieId;
import com.game.movies.battle.infrastructure.dto.MovieDetails;
import com.game.movies.battle.infrastructure.dto.MovieItemList;
import com.game.movies.battle.infrastructure.dto.ParamsFilter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "imdb", url = "${extern.imdb.baseUrl}")
public interface MoviesIMDB {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    MovieItemList getMoviesByParameter(@SpringQueryMap ParamsFilter params);

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    MovieDetails getMovieById(@SpringQueryMap FilterByMovieId params);
}
