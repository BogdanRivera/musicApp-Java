package com.musicmatch.musicApp.modelosdb;

import com.musicmatch.musicApp.modelosapi.ArtistaDatos;
import com.musicmatch.musicApp.modelosapi.CancionDatos;
import jakarta.persistence.*;

import java.util.OptionalInt;

@Entity
@Table(name = "canciones")
public class Cancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombreCancion;
    @Column(unique = true)
    private String urlCancion;
    private String artista;
    private String urlArtista;
    private String genero;
    private String urlGenero;
    private Integer nReproducciones;

    @ManyToOne
    private Artista artist;

    public Cancion(){}

    public Cancion(CancionDatos cancionDatos) {
        this.nombreCancion = cancionDatos.nombre();
        this.urlCancion = cancionDatos.urlCancion();
        this.genero = cancionDatos.tags().etiquetas().get(0).nombreGenero();
        this.urlGenero = cancionDatos.tags().etiquetas().get(0).urlGenero();
        this.nReproducciones = OptionalInt.of(Integer.valueOf(cancionDatos.nReproducciones())).orElse(0);
        this.urlArtista = cancionDatos.artistaDatosBasico().enlaceArtista();
        this.artista = cancionDatos.artistaDatosBasico().nombreArtista();
    }

    public Artista getArtist() {
        return artist;
    }

    public void setArtist(Artista artist) {
        this.artist = artist;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }

    public String getUrlCancion() {
        return urlCancion;
    }

    public void setUrlCancion(String urlCancion) {
        this.urlCancion = urlCancion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getUrlGenero() {
        return urlGenero;
    }

    public void setUrlGenero(String urlGenero) {
        this.urlGenero = urlGenero;
    }

    public Integer getnReproducciones() {
        return nReproducciones;
    }

    public void setnReproducciones(Integer nReproducciones) {
        this.nReproducciones = nReproducciones;
    }

    @Override
    public String toString() {
        return "\nCancion: \n"+
                "nombreCancion='" + nombreCancion + '\'' +
                ", urlCancion='" + urlCancion + '\'' +
                ", artista='" + artista + '\'' +
                ", urlArtista='" + urlArtista + '\'' +
                ", genero='" + genero + '\'' +
                ", urlGenero='" + urlGenero + '\'' +
                ", nReproducciones=" + nReproducciones;
    }
}

