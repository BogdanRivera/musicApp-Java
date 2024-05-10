package com.musicmatch.musicApp.modelosapi;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ArtistaDatos(
        @JsonAlias("name") String nombre,
        @JsonAlias("mbid") String identificadorArtista,
        @JsonAlias("url") String url,
        @JsonAlias("stats") Estadistica estadistica,
        @JsonAlias("tags") Tag tags,
        @JsonAlias("bio") Biografia biografia
) {
}
