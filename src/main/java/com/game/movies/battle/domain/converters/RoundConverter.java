package com.game.movies.battle.domain.converters;

import com.game.movies.battle.domain.dto.RankingResponse;
import com.game.movies.battle.domain.entity.Round;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class RoundConverter implements Converter<Round, RankingResponse> {
    @Override
    public RankingResponse convert(MappingContext<Round, RankingResponse> mappingContext) {
        Round round = mappingContext.getSource();
        RankingResponse rankingResponse = new RankingResponse();
        rankingResponse.setFullName(round.getPlayer().getFullName());
        rankingResponse.setTotalScore(round.getTotalScore());
        return rankingResponse;
    }
}
