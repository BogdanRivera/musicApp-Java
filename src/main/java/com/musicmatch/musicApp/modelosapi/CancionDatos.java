package com.musicmatch.musicApp.modelosapi;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CancionDatos(
        @JsonAlias("name") String nombre,
        @JsonAlias("url") String urlCancion,
        @JsonAlias("artist") ArtistaDatosBasico artistaDatosBasico,
        @JsonAlias("toptags") Tag tags,
        @JsonAlias("playcount") String nReproducciones
) {
}
