package com.musicmatch.musicApp.repository;

import com.musicmatch.musicApp.modelosdb.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artista,Long> {
}
