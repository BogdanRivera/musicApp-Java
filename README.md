# Proyecto de Búsqueda de Música

Este proyecto de Java ofrece una solución para buscar artistas y canciones utilizando la API pública de Last.fm y gestionar la información mediante una base de datos PostgreSQL. La aplicación permite a los usuarios realizar diversas operaciones, como búsqueda de artistas en la web, búsqueda de canciones por artista, consulta de artistas y canciones almacenados en la base de datos, entre otras funcionalidades.

## Características principales:

- **Búsqueda de Artistas en la Web:** Los usuarios pueden buscar artistas o bandas utilizando la API de Last.fm, proporcionando el nombre del artista como entrada. La aplicación recupera información detallada sobre el artista, como su nombre, número de oyentes, y más.

- **Búsqueda de Canciones por Artista:** Los usuarios pueden buscar canciones por artista ingresando el nombre del artista y el nombre de la canción. La aplicación utiliza la API de Last.fm para recuperar información sobre la canción y la almacena en la base de datos.

- **Consulta de Artistas y Canciones:** Los usuarios pueden consultar los artistas y canciones almacenados en la base de datos, lo que les permite ver la información previamente guardada.

- **Top 5 Artistas Más Escuchados:** La aplicación proporciona una funcionalidad para mostrar los 5 artistas más escuchados, basados en el número de oyentes registrados en la base de datos.

- **Gestión de Datos con PostgreSQL:** La información sobre artistas y canciones se almacena y gestiona utilizando una base de datos PostgreSQL, lo que permite una persistencia de datos eficiente y confiable.

## Tecnologías Utilizadas:

- Java
- API de Last.fm
- PostgreSQL
- Maven

## Capturas de pantalla: 
![inicio](https://github.com/BogdanRivera/musicApp-Java/assets/121648408/34f4ef54-2d5d-4a27-8a17-9b9c680523c0)
![base](https://github.com/BogdanRivera/musicApp-Java/assets/121648408/3bb82f2d-5d22-4c6e-97e7-a8dfb639ab34)

## Instrucciones de Uso:

1. Clona el repositorio desde GitHub.
2. Configura tu API Key de Last.fm en el archivo `Principal.java`. Ingresa aquí para más información: https://www.last.fm/api
3. Configura las variables de entorno en el archivo `application.properties`
4. Ejecuta la aplicación Java y sigue las instrucciones proporcionadas en la consola para utilizar las diferentes funcionalidades.

## Notas: 
- Para el caso de la opción 1 hace uso de la Api directamente. En dado caso de que no encuentre el artista no se guardará el dato en la base de datos.
- Para usar la opción 2 es necesario que el artista esté registrado en nuestra base de datos.
- Las demás opciones se basan en la base de datos creada. 

Cualquier sugerencia, duda o aclaración del código me pueden escribir directamente a bogdanrivera@gmail.com. 
