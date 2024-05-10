package com.musicmatch.musicApp.repository;

import com.musicmatch.musicApp.modelosdb.Artista;
import com.musicmatch.musicApp.modelosdb.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artista,Long> {
    Optional<Artista> findBynombreArtistaContainsIgnoreCase(String nombre);

    @Query("SELECT a FROM Artista a ORDER BY a.nOyentes DESC LIMIT 5")
    List<Artista> topArtistasEscuchados();

}
