package manager;

import java.util.ArrayList;
import java.util.List;
import activeRecord.Genre;
import activeRecord.Movie;
import activeRecord.Character;
import activeRecord.Movie_Genre;
import activeRecord.Person;
import dtos.CharacterDTO;
import dtos.MovieDTO;
import aufgabe41.aufgabe1;

public class MovieManager {

    /**
     * Sucht alle Filme, die den Suchstring im Titel enthalten. Gibt alle Filme zurück, wenn der Suchstring leer ist.
     * Der Vergleich erfolgt ohne Berücksichtigung der Groß-/Kleinschreibung.
     * @param search Suchstring für den Filmtitel.
     * @return Eine Liste von MovieDTO-Objekten, die die passenden Filme repräsentieren.
     * @throws Exception bei einem Datenbankfehler.
     */
    public List<MovieDTO> getMovieList(String search) throws Exception {
        List<MovieDTO> movies = new ArrayList<>();
        boolean ok = false; // Transaktionsstatus

        try {
            // Sucht Filme basierend auf dem Suchstring und konvertiert sie in MovieDTO-Objekte
            for (Movie movie : Movie.findByTitle(search)) {
                movies.add(movie.toDTO());
            }

            aufgabe1.getConnection().commit(); // Bestätigt die Transaktion
            ok = true; // Erfolgreiche Transaktion

        } finally {
            if (!ok) { // Rollback bei Misserfolg
                aufgabe1.getConnection().rollback();
            }
        }

        return movies; // Gibt die Liste der gefundenen Filme zurück
    }

    /**
     * Fügt einen neuen Film in die Datenbank ein oder aktualisiert einen vorhandenen Film basierend auf den übergebenen Daten.
     * Aktualisiert auch die Zuordnungen zu Genres und Charakteren.
     * @param movieDTO Das Filmobjekt mit den neuen Daten, einschließlich Genres und Charaktere.
     * @throws Exception bei einem Datenbankfehler.
     */
    public void insertUpdateMovie(MovieDTO movieDTO) throws Exception {
        boolean ok = false;
        Movie movie;

        try {
            // Unterscheidet zwischen neuer und vorhandener Film-Einfügung/Aktualisierung
            if (movieDTO.getId() == null) {
                movie = new Movie();
                movie.setTitle(movieDTO.getTitle());
                movie.setType(movieDTO.getType().charAt(0));
                movie.setYear(movieDTO.getYear());
                movie.insert(); // Einfügen eines neuen Films

            } else {
                movie = Movie.findById(movieDTO.getId());
                if (movie == null) {
                    throw new IllegalArgumentException("Movie not found for update");
                }
                // Aktualisieren eines bestehenden Films
                movie.setTitle(movieDTO.getTitle());
                movie.setType(movieDTO.getType().charAt(0));
                movie.setYear(movieDTO.getYear());
                movie.update();
            }

            // Löscht alte Genre-Zuordnungen
            for (Movie_Genre movieGenre : Movie_Genre.findByMovieID(movie.getId())) {
                movieGenre.delete();
            }

            // Fügt neue Genre-Zuordnungen hinzu
            for (String genre : movieDTO.getGenres()) {
                List<Genre> foundGenres = Genre.findByGenre(genre);
                if (foundGenres.isEmpty()) {
                    throw new IllegalArgumentException("Genre not found: " + genre);
                }
                // Fügt ein neues MovieGenre-Objekt hinzu
                Movie_Genre movieGenre = new Movie_Genre();
                movieGenre.setMovieID(movie.getId());
                movieGenre.setGenreID(foundGenres.get(0).getID());
                movieGenre.insert();
            }

            // Löscht alte Charaktere
            for (Character Character : Character.findByMovieID(movie.getId())) {
                Character.delete();
            }

            // Fügt neue Charaktere hinzu
            for (int i = 0; i < movieDTO.getCharacters().size(); i++) {
                CharacterDTO characterDTO = movieDTO.getCharacters().get(i);
                List<Person> foundPersons = Person.findByName(characterDTO.getPlayer());

                if (foundPersons.isEmpty()) {
                    throw new IllegalArgumentException("Person not found: " + characterDTO.getPlayer());
                }

                // Erstellt und fügt ein neues MovieCharacter-Objekt hinzu
                Character movieCharacter = new Character();
                movieCharacter.setCharacter(characterDTO.getCharacter());
                movieCharacter.setAlias(characterDTO.getAlias());
                movieCharacter.setPosition(i + 1);
                movieCharacter.setMovieID(movie.getId());
                movieCharacter.setPersonID(foundPersons.get(0).getId());
                movieCharacter.insert();
            }

            aufgabe1.getConnection().commit(); // Bestätigt die Transaktion
            ok = true;

        } finally {
            if (!ok) { // Rollback bei Fehlern
                aufgabe1.getConnection().rollback();
            }
        }
    }

    /**
     * Löscht einen Film und alle zugehörigen Objekte (Genres und Charaktere) aus der Datenbank.
     * @param movieId Die ID des Films, der gelöscht werden soll.
     * @throws Exception bei einem Datenbankfehler.
     */
    public void deleteMovie(long movieId) throws Exception {
        boolean ok = false;

        try {
            // Löscht alle zugehörigen MovieCharacter-Einträge
            for (Character movieCharacter : Character.findByMovieID(movieId)) {
                movieCharacter.delete();
            }

            // Löscht alle zugehörigen MovieGenre-Einträge
            for (Movie_Genre movieGenre : Movie_Genre.findByMovieID(movieId)) {
                movieGenre.delete();
            }

            // Löscht den Film selbst
            Movie movie = Movie.findById(movieId);
            if (movie != null) {
                movie.delete();
            }

            aufgabe1.getConnection().commit(); // Bestätigt die Transaktion
            ok = true;

        } finally {
            if (!ok) { // Rollback bei Fehlern
                aufgabe1.getConnection().rollback();
            }
        }
    }

    /**
     * Holt die Daten eines Films aus der Datenbank und gibt diese als MovieDTO-Objekt zurück.
     * @param movieId Die ID des Films, dessen Daten abgerufen werden sollen.
     * @return MovieDTO mit den Daten des Films, einschließlich Genres und Charaktere.
     * @throws Exception bei einem Datenbankfehler.
     */
    public MovieDTO getMovie(long movieId) throws Exception {
        MovieDTO movieDTO;
        boolean ok = false;

        try {
            // Sucht den Film anhand der ID
            Movie movie = Movie.findById(movieId);
            if (movie == null) {
                throw new IllegalArgumentException("Movie not found for ID: " + movieId);
            }
            movieDTO = movie.toDTO(); // Konvertiert Movie in MovieDTO

            aufgabe1.getConnection().commit(); // Bestätigt die Transaktion
            ok = true;

        } finally {
            if (!ok) { // Rollback bei Fehlern
                aufgabe1.getConnection().rollback();
            }
        }

        return movieDTO; // Gibt das DTO des Films zurück
    }
}









