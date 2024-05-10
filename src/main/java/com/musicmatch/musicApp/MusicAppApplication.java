package com.musicmatch.musicApp;

import com.musicmatch.musicApp.principal.Principal;
import com.musicmatch.musicApp.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusicAppApplication implements CommandLineRunner {

	@Autowired
	ArtistRepository artistRepository;
	public static void main(String[] args) {
		SpringApplication.run(MusicAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(artistRepository);
		principal.muestra();
	}
}
