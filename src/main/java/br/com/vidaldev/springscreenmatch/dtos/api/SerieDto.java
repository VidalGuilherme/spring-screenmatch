package br.com.vidaldev.springscreenmatch.dtos.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SerieDto(
        @JsonAlias("Title") String title,
        @JsonAlias("totalSeasons") String totalSeasons,
        @JsonAlias("imdbRating") String rating,
        @JsonAlias("Year") String year,
        @JsonAlias("Genre") String genre,
        @JsonAlias("Plot") String plot,
        @JsonAlias("Poster") String poster
) {

    @Override
    public String toString() {
        return "\n Nome: "+title+
            "\n Ano: "+year+
            "\n Gêneros: "+genre+
            "\n Nota: "+rating+
            "\n Temporadas: "+totalSeasons+
            "\n";
    }
}
