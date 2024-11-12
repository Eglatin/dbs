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

/**
 * MovieManager verwaltet die Datenbankzugriffe und -operationen 
 * für Film-Entitäten. stellt Methoden zur Verfügung, um Filme zu suchen,
 * hinzuzufügen, aktualisieren und löschen
 */
public class MovieManager {

    /**
     * Sucht alle Filme, die den Suchstring im Titel enthalten. Gibt alle Filme zurück, wenn der Suchstring leer ist
     * Vergleich ohne Berücksichtigung groß und kleinschreibung
     * @param search Suchstring für den Filmtitel.
     * @return Eine Liste von MovieDTO-Objekten, die die passenden Filme repräsentieren
     * @throws Exception Bei einem Datenbankfehler
     */
    public List<MovieDTO> getMovieList(String search) throws Exception {
        List<MovieDTO> movies = new ArrayList<>();
        boolean ok = false; // Transaktionsstatus, um Commit oder Rollback zu steuern

        try {
            // sucht Filme basierend auf Suchstring und konvertiert sie in MovieDTO-Objekte
            for (Movie movie : Movie.findByTitle(search)) {
                movies.add(movie.toDTO());
            }

            //Bestätigt die Transaktion durch Commit
            aufgabe1.getConnection().commit();
            ok = true; // Setzt den Transaktionsstatus auf erfolgreich

        } finally {
            // Falls Fehler, wird Trasnaktion durch Rollback abgebrochen
            if (!ok) {
                aufgabe1.getConnection().rollback();
            }
        }

        return movies; //Gibt Liste der gefundenen Filme zurück
    }

    /**
     *Fügt neuen Film in Datenbank ein oder aktualisiert einen vorhandenen Film basierend auf den übergebenen Daten
     * Aktualisiert auch die Zuordnungen zu Genres und Charakteren
    *@param movieDTO Filmobjekt mit neuen Daten, einschließlich Genres und Charaktere
     * @throws Exception Bei datenbankfehler
     */
    public void insertUpdateMovie(MovieDTO movieDTO) throws Exception {
        boolean ok = false;
        Movie movie;

        try {
            // unterscheidet zwischen neuer und vorhandener Filmeinfügung/ aktualisierung
            if (movieDTO.getId() == null) {
                // Neues Movie-Objekt, falls  ID null ist (neuer Film)
                movie = new Movie();
                movie.setTitle(movieDTO.getTitle());
                movie.setType(movieDTO.getType().charAt(0));
                movie.setYear(movieDTO.getYear());
                movie.insert(); // Einfügen eines neuen Films

            } else {
                //Sucht nach vorhandenen Film anhand id
                movie = Movie.findById(movieDTO.getId());
                if (movie == null) {
                    throw new IllegalArgumentException("Film für Update nicht gefunden");
                }
                // aktualisieren  bestehenden Films
                movie.setTitle(movieDTO.getTitle());
                movie.setType(movieDTO.getType().charAt(0));
                movie.setYear(movieDTO.getYear());
                movie.update();
            }

            // Löscht alte Genre-Zuordnungen für den Film
            for (Movie_Genre movieGenre : Movie_Genre.findByMovieID(movie.getId())) {
                movieGenre.delete();
            }

            // Fügt neue Genre-Zuordnungen hinzu
            for (String genre : movieDTO.getGenres()) {
                //sucht das Genre in der Datenbank anhand des Namens
                List<Genre> foundGenres = Genre.findByGenre(genre);
                if (foundGenres.isEmpty()) {
                    throw new IllegalArgumentException("Genre nicht gefunden: " + genre);
                }
                // Erstellt eine neue Zuordnung zwischen Film und Genre
                Movie_Genre movieGenre = new Movie_Genre();
                movieGenre.setMovieID(movie.getId());
                movieGenre.setGenreID(foundGenres.get(0).getID());
                movieGenre.insert();
            }

            // Löscht alte Charaktere für den Film
            for (Character character : Character.findByMovieID(movie.getId())) {
                character.delete();
            }

            // Fügt neue Charaktere hinzu
            for (int i = 0; i < movieDTO.getCharacters().size(); i++) {
                CharacterDTO characterDTO = movieDTO.getCharacters().get(i);
                //sucht Person (Darsteller) in der Datenbank anhand des Namens
                List<Person> foundPersons = Person.findByName(characterDTO.getPlayer());

                if (foundPersons.isEmpty()) {
                    throw new IllegalArgumentException("Person nicht gefunden: " + characterDTO.getPlayer());
                }

                //erstellt und fügt ein neues Character-Objekt für den Film hinzu
                Character movieCharacter = new Character();
                movieCharacter.setCharacter(characterDTO.getCharacter());
                movieCharacter.setAlias(characterDTO.getAlias());
                movieCharacter.setPosition(i + 1); // Position entsprechend reihenfolge setzen
                movieCharacter.setMovieID(movie.getId());
                movieCharacter.setPersonID(foundPersons.get(0).getId());
                movieCharacter.insert();
            }

            //bestätigt transaktion durch Commit
            aufgabe1.getConnection().commit();
            ok = true;

        } finally {
            // FallsFehler aufgetreten, wird die Transaktion durch Rollback abgebrochen
            if (!ok) {
                aufgabe1.getConnection().rollback();
            }
        }
    }

    /**
     * Löscht einen Film und alle zugehörigen Objekte (Genres und Charaktere) aus der Datenbank
     * @param movieId Die ID des Films, der gelöscht werden soll
     * @throws Exception Bei Datenbankfehler
     */
    public void deleteMovie(long movieId) throws Exception {
        boolean ok = false;

        try {
            // Löscht alle zugehörigen Character-Einträge für den Film
            for (Character movieCharacter : Character.findByMovieID(movieId)) {
                movieCharacter.delete();
            }

            // Löscht alle zugehörigen Movie_GenreEinträge für den Film
            for (Movie_Genre movieGenre : Movie_Genre.findByMovieID(movieId)) {
                movieGenre.delete();
            }

            // Löscht Film selbst
            Movie movie = Movie.findById(movieId);
            if (movie != null) {
                movie.delete();
            }

            // Bestätigt die Transaktion durch Commit
            aufgabe1.getConnection().commit();
            ok = true;

        } finally {
            // Falls fehler aufgetreten ist, wird die Transaktion durch Rollback abgebrochen
            if (!ok) {
                aufgabe1.getConnection().rollback();
            }
        }
    }

    /**
     * Holt die Daten eines Films aus der Datenbank und gibt diese als `MovieDTO`-Objekt zurück.
     * @param movieId Die ID des Films, dessen Daten abgerufen werden sollen.
     * @return MovieDTO mit den Daten des Films, einschließlich Genres und Charaktere.
     * @throws Exception Bei einem Datenbankfehler.
     */
    public MovieDTO getMovie(long movieId) throws Exception {
        MovieDTO movieDTO;
        boolean ok = false;

        try {
            // Sucht den Film anhand der ID
            Movie movie = Movie.findById(movieId);
            if (movie == null) {
                throw new IllegalArgumentException("Film für ID " + movieId + " nicht gefunden");
            }
            // Konvertiert den Film in ein `MovieDTO`-Objekt
            movieDTO = movie.toDTO();

            // Bestätigt die Transaktion durch Commit
            aufgabe1.getConnection().commit();
            ok = true;

        } finally {
            // Falls ein Fehler aufgetreten ist, wird die Transaktion durch Rollback abgebrochen
            if (!ok) {
                aufgabe1.getConnection().rollback();
            }
        }

        return movieDTO; // Gibt das DTO des Films zurück
    }
}
