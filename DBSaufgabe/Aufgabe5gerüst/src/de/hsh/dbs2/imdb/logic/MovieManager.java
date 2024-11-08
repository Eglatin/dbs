package manager;

import dtos.MovieDTO;
import dtos.CharacterDTO;
import activeRecord.Movie;
import activeRecord.Genre;
import activeRecord.Character;
import activeRecord.Movie_Genre;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieManager {

    // Holt einen Film und seine Genres und Charaktere als MovieDTO
    public MovieDTO getMovie(long movieId) throws SQLException {
        Movie movie = Movie.findById(movieId);
        if (movie == null) return null;

        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setYear(movie.getYear());
        movieDTO.setType(movie.getType());

        // Genres abrufen und hinzufügen
        List<String> genres = Genre.findByMovieId(movieId); // Holt die zugehörigen Genres
        movieDTO.setGenres(genres);

        // Charaktere abrufen und hinzufügen
        List<CharacterDTO> characters = Character.findByMovieId(movieId); // Holt die zugehörigen Charaktere
        movieDTO.setCharacters(characters);

        return movieDTO;
    }

    // Sucht eine Liste von Filmen anhand eines Titelsuchstrings
    public List<MovieDTO> getMovieList(String search) throws SQLException {
        List<MovieDTO> movies = new ArrayList<>();
        List<Movie> movieList = Movie.findByTitle(search);

        for (Movie movie : movieList) {
            MovieDTO movieDTO = getMovie(movie.getId());
            if (movieDTO != null) {
                movies.add(movieDTO);
            }
        }
        return movies;
    }

    // Fügt einen neuen Film hinzu oder aktualisiert einen bestehenden Film
    public void insertUpdateMovie(MovieDTO movieDTO) throws SQLException {
        Movie movie;

        if (movieDTO.getId() == null) {
            // Neuen Film einfügen
            movie = new Movie(movieDTO.getTitle(), movieDTO.getYear(), movieDTO.getType());
            movie.insert();
        } else {
            // Bestehenden Film aktualisieren
            movie = Movie.findById(movieDTO.getId());
            if (movie != null) {
                movie.setTitle(movieDTO.getTitle());
                movie.setYear(movieDTO.getYear());
                movie.setType(movieDTO.getType());
                movie.update();
            }
        }

        // Verknüpfte Genres aktualisieren
        Movie_Genre.deleteByMovieId(movie.getId()); // Löscht alte Genre-Verknüpfungen
        for (String genreName : movieDTO.getGenres()) {
            Genre genre = Genre.findByName(genreName);
            if (genre != null) {
                Movie_Genre movieGenre = new Movie_Genre(movie.getId(), genre.getId());
                movieGenre.insert();
            }
        }

        // Verknüpfte Charaktere aktualisieren
        Character.deleteByMovieId(movie.getId()); // Löscht alte Charakter-Verknüpfungen
        for (CharacterDTO characterDTO : movieDTO.getCharacters()) {
            Character character = new Character(characterDTO.getCharacter(), characterDTO.getAlias(), null);
            character.insert();
            Movie_Genre.createMovieCharacterRelation(movie.getId(), character.getId()); // Verknüpft Film und Charakter
        }
    }
}
