package br.com.vidaldev.springscreenmatch.dtos.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SerieDto(
        @JsonAlias("Title") String title,
        @JsonAlias("totalSeasons") Integer totalSeasons,
        @JsonAlias("imdbRating") String rating,
        @JsonAlias("Year") String year,
        @JsonAlias("Genre") String genre,
        @JsonAlias("Plot") String plot,
        @JsonAlias("Poster") String poster
) {
}
