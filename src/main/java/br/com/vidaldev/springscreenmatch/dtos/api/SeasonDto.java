package br.com.vidaldev.springscreenmatch.dtos.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonDto(
        @JsonAlias("totalSeasons") Integer season,
        @JsonAlias("Episodes") List<EpisodeDto> episodes
) {
}
