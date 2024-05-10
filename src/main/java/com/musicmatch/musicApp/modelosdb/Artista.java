package com.musicmatch.musicApp.modelosdb;

import com.musicmatch.musicApp.modelosapi.ArtistaDatos;
import com.musicmatch.musicApp.modelosapi.CancionDatos;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

@Entity
@Table(name = "artistas")
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String nombreArtista;
    private String idArtistaWeb;
    @Column(unique = true)
    private String urlArtista;
    private Integer nOyentes;
    private Integer nReproducciones;
    private String genero;
    private String urlGenero;
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Cancion> canciones = new ArrayList<>();

    public Artista(){

    }

    public Artista(ArtistaDatos artistaDatos) {
        this.nombreArtista = artistaDatos.nombre();
        this.idArtistaWeb = artistaDatos.identificadorArtista();
        this.urlArtista = artistaDatos.url();
        this.nOyentes = OptionalInt.of(Integer.valueOf(artistaDatos.estadistica().oyentes())).orElse(0);
        this.nReproducciones = OptionalInt.of(Integer.valueOf(artistaDatos.estadistica().nReproducciones())).orElse(0);
        this.genero = artistaDatos.tags().etiquetas().get(1).nombreGenero();
        this.urlGenero = artistaDatos.tags().etiquetas().get(1).urlGenero();
    }

    @Override
    public String toString() {
        return "Artista:\n" +
                "nombre='" + nombreArtista + '\'' +
                ", idArtistaWeb='" + idArtistaWeb + '\'' +
                ", urlArtista='" + urlArtista + '\'' +
                ", nOyentes=" + nOyentes +
                ", nReproducciones=" + nReproducciones +
                ", genero='" + genero + '\'' +
                ", urlGenero='" + urlGenero + '\''+
                ", canciones='"+canciones;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        canciones.forEach(c -> c.setArtist(this));
        this.canciones = canciones;
    }

    public String getNombre() {
        return nombreArtista;
    }

    public void setNombre(String nombre) {
        this.nombreArtista = nombre;
    }

    public String getIdArtistaWeb() {
        return idArtistaWeb;
    }

    public void setIdArtistaWeb(String idArtistaWeb) {
        this.idArtistaWeb = idArtistaWeb;
    }

    public String getUrlArtista() {
        return urlArtista;
    }

    public void setUrlArtista(String urlArtista) {
        this.urlArtista = urlArtista;
    }

    public int getnOyentes() {
        return nOyentes;
    }

    public void setnOyentes(int nOyentes) {
        this.nOyentes = nOyentes;
    }

    public Integer getnReproducciones() {
        return nReproducciones;
    }

    public void setnReproducciones(Integer nReproducciones) {
        this.nReproducciones = nReproducciones;
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
}
