package br.com.vidaldev.springscreenmatch.dtos.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeDto(
        @JsonAlias("Title") String title,
        @JsonAlias("Episode") Integer episode,
        @JsonAlias("imdbRating") String rating,
        @JsonAlias("Released") String date
) {
}
