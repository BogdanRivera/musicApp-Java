package com.musicmatch.musicApp.modelosapi;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Etiqueta(
        @JsonAlias("name") String nombreGenero,
        @JsonAlias("url") String urlGenero
) {
}
