package com.musicmatch.musicApp.conexiones;

public interface IConvierteDatos {
    <T> T adquiereDatos(String json, Class<T> clase);
}
