package br.com.vidaldev.springscreenmatch.dtos.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeDto(
        @JsonAlias("Title") String title,
        @JsonAlias("Episode") Integer episode,
        @JsonAlias("imdbRating") String rating,
        @JsonAlias("Released") String date,
        Integer season
) {

    public EpisodeDto withSeason(Integer season){
        return new EpisodeDto(title(), episode(), rating(), date(), season);
    }

    @Override
    public String toString() {
        return "\n Título: "+title+
                "\n Número: "+episode+
                "\n Nota: "+rating+
                "\n Data: "+ date+
                "\n";
    }
}
