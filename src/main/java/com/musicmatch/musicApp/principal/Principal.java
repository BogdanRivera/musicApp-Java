package com.musicmatch.musicApp.principal;

import com.musicmatch.musicApp.conexiones.ConsumeApi;
import com.musicmatch.musicApp.conexiones.ConvierteDatos;
import com.musicmatch.musicApp.modelosapi.ArtistaRespuesta;
import com.musicmatch.musicApp.modelosapi.CancionDatos;
import com.musicmatch.musicApp.modelosapi.TrackRespuesta;
import com.musicmatch.musicApp.modelosdb.Artista;
import com.musicmatch.musicApp.modelosdb.Cancion;
import com.musicmatch.musicApp.repository.ArtistRepository;


import java.util.*;

public class Principal {
    private  ConsumeApi consumeApi = new ConsumeApi();
    private  String URL_BASE = "http://ws.audioscrobbler.com/2.0/";
    private String API_KEY = "&api_key=fd522a59d01f85676a0d7242180b24f1";
    private String GET_ARTIST_METHOD = "?method=artist.getinfo&artist=";
    private String GET_TRACK_METHOD = "?method=track.getInfo&artist=";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private Scanner scanner = new Scanner(System.in);
    private ArtistRepository repositorio;
    private List<Artista> artistas;

    public Principal(ArtistRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void muestra() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ****************Bienvenido a mi aplicación de búsqueda de música****************
                    1. Buscar artista
                    2. Buscar canción por artista
                    3. Mostrar artistas buscados y sus canciones
                                        
                    0. Salir
                                        
                    Seleccione una opción""";
            System.out.println(menu);
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        getArtista();
                        break;
                    case 2:
                        //getCancion();
                        getCancion();
                        break;
                    case 3:
                        muestraArtistasBuscados();
                        break;

                    case 0:
                        System.out.println("Cerrando la aplicación...");
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Opción inválida, inténtelo de nuevo\n");
                opcion = -1;
            }
        }
    }

    private ArtistaRespuesta getDatosArtista(){
        System.out.println("Ingresa el nombre de un artista o banda: ");
        var artist = scanner.nextLine();
        try{
            var json = consumeApi.adquiereDatos(URL_BASE+GET_ARTIST_METHOD+artist.toLowerCase().replace(" ","+")+API_KEY+"&format=json");
            System.out.println("Respuesta: \n"+json);

            //            Artista artista = new Artista(datos.artista());
//            System.out.println(artista);
//            repositorio.save(artista);
            return convierteDatos.adquiereDatos(json,ArtistaRespuesta.class);
        }catch (Exception e){
            System.out.println("Ocurrió un error");
        }
        return null;
    }

    private void getArtista(){
        ArtistaRespuesta datos = getDatosArtista();
        Artista artista = new Artista(datos.artista());
        try{
            repositorio.save(artista);
            System.out.println(artista);
        }catch (Exception e){
            System.out.println("Ocurrió un error. Probablemente el artista ya está en la base de datos");
        }
    }

    private void getCancion(){
        muestraArtistasBuscados();
        System.out.println("Ingresa el nombre de una canción: ");
        var respCancion = scanner.nextLine();
        System.out.println("A que banda/artista pertenece la canción: ");
        var respArtist = scanner.nextLine();
        Optional <Artista> artistaEncontrado = artistas.stream()
                .filter(s -> s.getNombre().toLowerCase().contains(respArtist.toLowerCase()))
                .findFirst();
        if(artistaEncontrado.isPresent()){
            try {

                var a = artistaEncontrado.get();
                var json = consumeApi.adquiereDatos(URL_BASE + GET_TRACK_METHOD + respArtist.toLowerCase().replace(" ", "+") + "&track=" + respCancion + API_KEY + "&format=json");
                TrackRespuesta cancionDatos = convierteDatos.adquiereDatos(json, TrackRespuesta.class);
                Cancion cancion = new Cancion(cancionDatos.cancionDatos());
                cancion.setArtist(a);
                a.getCanciones().add(cancion);
                repositorio.save(a);
            }catch (Exception e){
                System.out.println("Ha ocurrido un error registrando la canción");
            }
        }
    }

    private void muestraArtistasBuscados(){
        artistas = repositorio.findAll();

        artistas.stream()
                .sorted(Comparator.comparing(Artista::getNombre))
                .forEach(System.out::println);
    }


}
