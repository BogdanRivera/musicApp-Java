package com.musicmatch.musicApp.modelosapi;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Estadistica(
        @JsonAlias("listeners") String oyentes,
        @JsonAlias("playcount") String nReproducciones
) {
}
