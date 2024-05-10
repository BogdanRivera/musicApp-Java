package com.musicmatch.musicApp.principal;

import com.musicmatch.musicApp.conexiones.ConsumeApi;
import com.musicmatch.musicApp.conexiones.ConvierteDatos;
import com.musicmatch.musicApp.modelosapi.ArtistaRespuesta;
import com.musicmatch.musicApp.modelosapi.TrackRespuesta;
import com.musicmatch.musicApp.modelosdb.Artista;
import com.musicmatch.musicApp.modelosdb.Cancion;
import com.musicmatch.musicApp.repository.ArtistRepository;


import java.text.Normalizer;
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
        System.out.println("\n************Bienvenido a mi aplicación de búsqueda de música****************");
        while (opcion != 0) {
            var menu = """
                    
                    1. Buscar artista en la web
                    2. Buscar canción por artista
                    3. Mostrar artistas buscados y sus canciones
                    4. Buscar artista en la base de datos
                    5. Top 5 artistas más escuchados
                    6. Buscar canciones de un artista en específico
                    7. Canciones por artista más escuchadas
                                        
                    0. Salir
                                        
                    Seleccione una opción""";
            System.out.println(menu);
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        getArtistaWeb();
                        break;
                    case 2:
                        //getCancion();
                        getCancion();
                        break;
                    case 3:
                        muestraArtistasBuscados();
                        break;
                    case 4:
                        var response= buscaArtistaBD();
                        if (response != null) {
                            System.out.println("Artista encontrado!\n" + response);
                        } else {
                            System.out.println("Artista no encontrado");
                        }
                        break;
                    case 5:
                        topArtistasEscuchados();
                        break;
                    case 6:
                        buscaCancionesArtista();
                        break;

                    case 7:
                        buscaTopCanciones();
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
        var artistaConAcentos = scanner.nextLine();
        var artist = quitarAcentos(artistaConAcentos);
        try{
            var json = consumeApi.adquiereDatos(URL_BASE+GET_ARTIST_METHOD+artist.toLowerCase().replace(" ","+")+API_KEY+"&format=json");
            //            Artista artista = new Artista(datos.artista());
//            System.out.println(artista);
//            repositorio.save(artista);
            if (json.contains("{\"error\":6,\"message\":\"The artist you supplied could not be found\",\"links\":[]}")){
                return null;
            }else{
                return convierteDatos.adquiereDatos(json,ArtistaRespuesta.class);
            }

        }catch (Exception e){
            System.out.println("Ocurrió un error");
        }
        return null;
    }

    private void getArtistaWeb() {
        ArtistaRespuesta datos = getDatosArtista();
        if (datos != null) {
            Artista artista = new Artista(datos.artista());
            try {
                repositorio.save(artista);
                System.out.println(artista);
            } catch (Exception e) {
                System.out.println("Ocurrió un error. Probablemente el artista ya está en la base de datos");
            }
        }else{
            System.out.println("Artista no encontrado");
        }
    }

    private void getCancion(){
        muestraArtistasBuscados();
        System.out.println("\nA que banda/artista pertenece la canción: ");
        var respArtistAcentos = scanner.nextLine();
        var respArtist = quitarAcentos(respArtistAcentos);
        System.out.println("\nIngresa el nombre de una canción: ");
        var respCancionAcentos = scanner.nextLine();
        var respCancion = quitarAcentos(respCancionAcentos);
        Optional <Artista> artistaEncontrado = artistas.stream()
                .filter(s -> s.getNombre().toLowerCase().contains(respArtist.toLowerCase()))
                .findFirst();
        if(artistaEncontrado.isPresent()){
            try {
                var a = artistaEncontrado.get();
                var json = consumeApi.adquiereDatos(URL_BASE + GET_TRACK_METHOD + respArtist.toLowerCase().replace(" ", "+") + "&track=" + respCancion.toLowerCase().replace(" ","+") + API_KEY + "&format=json");
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

    private Artista buscaArtistaBD(){
        System.out.println("\nIngresa el nombre del artista/banda a buscar en la base de datos: ");
        var nameAcento = scanner.nextLine();
        var name = quitarAcentos(nameAcento);
        var artistaBuscado = repositorio.findBynombreArtistaContainsIgnoreCase(name);
        return artistaBuscado.orElse(null);
    }

    private void topArtistasEscuchados() {
        var respuesta = repositorio.topArtistasEscuchados();
        if(respuesta.isEmpty()){
            System.out.println("No hay artistas registrados aún");
        }else{
            System.out.println("******* Top 5 artistas más escuchados *******\n");
            respuesta.forEach(a -> System.out.println("Artista: "+a.getNombre()+", número de oyentes: "+a.getnOyentes() + "\n"));
        }
    }

    private Artista buscaCancionesArtista() {
        try {
            var responseCanciones = buscaArtistaBD();
            if (!responseCanciones.getCanciones().isEmpty()) {
                System.out.println("\nCanciones de " + responseCanciones.getNombre());
                responseCanciones.getCanciones().forEach(System.out::println);
                return responseCanciones;
            } else {
                System.out.println("El artista no tiene canciones registradas");
                return responseCanciones;
            }
        }catch (Exception e){
            System.out.println("Ocurrió un error");
        }
        return null;
    }

    private void buscaTopCanciones() {
        var artista = buscaArtistaBD();
        if(artista!=null){
            System.out.println("\n***** Canciones más escuchadas de "+artista.getNombre()+" *****");
            artista.getCanciones()
                    .stream()
                    .sorted(Comparator.comparing(Cancion::getnReproducciones).reversed())
                    .limit(5)
                    .forEach(System.out::println);
        }else{
            System.out.println("Artista no encontrado");
        }
    }

    private static String quitarAcentos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }




}
