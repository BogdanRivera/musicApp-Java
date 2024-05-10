package com.musicmatch.musicApp.modelosapi;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ArtistaDatosBasico(
        @JsonAlias("name") String nombreArtista,
        @JsonAlias("url") String enlaceArtista

) {
}
