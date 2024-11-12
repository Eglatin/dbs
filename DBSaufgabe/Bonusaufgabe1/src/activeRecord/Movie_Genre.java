package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import aufgabe41.aufgabe1;

/**
 * Die Klasse `Movie_Genre` repräsentiert die N:M-Beziehung zwischen Movie und Genre.
 * Sie speichert die Zuordnung von Filmen zu ihren jeweiligen Genres und umgekehrt.
 * Die Klasse bietet Methoden zum Einfügen, Aktualisieren, Löschen und Abrufen
 * dieser Beziehungen aus der Datenbank.
 */
public class Movie_Genre {
    
    // ID der Movie_Genre-Beziehung; könnte optional für die Datenbankverwaltung sein.
    private Long id;

    // ID des Films, auf den sich die Beziehung bezieht.
    private Long movieID;

    // ID des Genres, das mit dem Film verknüpft ist.
    private Long genreID;
    
    // Getter- und Setter-Methoden für die Attribute der Klasse
    
    /**
     * Gibt die ID der Movie_Genre-Beziehung zurück.
     * @return ID der Beziehung als Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Gibt die ID des zugeordneten Films zurück.
     * @return Film-ID als Long
     */
    public long getMovieID() {
        return movieID;
    }

    /**
     * Setzt die ID des zugeordneten Films.
     * @param movieID Die Film-ID als Long
     */
    public void setMovieID(long movieID) {
        this.movieID = movieID;
    }

    /**
     * Gibt die ID des zugeordneten Genres zurück.
     * @return Genre-ID als Long
     */
    public long getGenreID() {
        return genreID;
    }

    /**
     * Setzt die ID des zugeordneten Genres.
     * @param genreID Die Genre-ID als Long
     */
    public void setGenreID(long genreID) {
        this.genreID = genreID;
    }

    // Methoden zur Datenbankmanipulation (Einfügen, Aktualisieren, Löschen)
    
    /**
     * Fügt eine neue Movie_Genre-Beziehung in die Datenbank ein.
     * Die IDs für Film und Genre müssen gesetzt sein, bevor die Methode aufgerufen wird.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalStateException Falls `movieID` oder `genreID` nicht gesetzt sind.
     */
    public void insert() throws SQLException {
        // Verhindert das Einfügen, wenn entweder die Film- oder Genre-ID fehlt
        if (movieID == null || genreID == null) {
            throw new IllegalStateException("MovieID und GenreID dürfen nicht null sein");
        }

        // Verbindungsabruf für die Datenbankoperation
        Connection conn = aufgabe1.getConnection();
        
        // SQL-Statement zum Einfügen der Beziehung in die `MovieGenre`-Tabelle
        String inst = "INSERT INTO MovieGenre(MovieID, GenreID) VALUES(?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt den Wert für die erste Platzhalter-Variable (? → movieID)
            stmt.setLong(1, movieID);
            // Setzt den Wert für die zweite Platzhalter-Variable (? → genreID)
            stmt.setLong(2, genreID);
            
            // Führt das SQL-Insert-Statement aus, um die Beziehung einzufügen
            stmt.executeUpdate();
        }
    }

    /**
     * Löscht eine Movie_Genre-Beziehung aus der Datenbank.
     * Die IDs für Film und Genre müssen gesetzt sein, bevor die Methode aufgerufen wird.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalStateException Falls `movieID` oder `genreID` nicht gesetzt sind.
     */
    public void delete() throws SQLException {
        // Verhindert das Löschen, wenn entweder die Film- oder Genre-ID fehlt
        if (movieID == null || genreID == null) {
            throw new IllegalStateException("MovieID und GenreID dürfen nicht null sein");
        }

        // Verbindungsabruf für die Datenbankoperation
        Connection conn = aufgabe1.getConnection();
        
        // SQL-Statement zum Löschen der Beziehung basierend auf den IDs
        String inst = "DELETE FROM MovieGenre WHERE MovieID = ? AND GenreID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt den Wert für den ersten Platzhalter (? → movieID)
            stmt.setLong(1, movieID);
            // Setzt den Wert für den zweiten Platzhalter (? → genreID)
            stmt.setLong(2, genreID);

            // Führt das SQL-Delete-Statement aus, um die Beziehung zu löschen
            stmt.executeUpdate();
        }
    }

    /**
     * Aktualisiert die Genre-Zuordnung für einen bestehenden Film in der Datenbank.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalStateException Falls `movieID` oder `genreID` nicht gesetzt sind.
     */
    public void update() throws SQLException {
        // Verhindert das Aktualisieren, wenn entweder die Film- oder Genre-ID fehlt
        if (movieID == null || genreID == null) {
            throw new IllegalStateException("MovieID und GenreID dürfen nicht null sein");
        }

        // Verbindungsabruf für die Datenbankoperation
        Connection conn = aufgabe1.getConnection();
        
        // SQL-Statement zum Aktualisieren des Genre-Eintrags für den angegebenen Film
        String inst = "UPDATE MovieGenre SET GenreID = ? WHERE MovieID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt den Wert für das Genre (? → genreID)
            stmt.setLong(1, genreID);
            // Setzt den Wert für die Film-ID zur Identifikation des zu aktualisierenden Datensatzes (? → movieID)
            stmt.setLong(2, movieID);

            // Führt das SQL-Update-Statement aus und prüft, ob Zeilen betroffen sind
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Aktualisieren von Movie_Genre fehlgeschlagen, keine Zeilen betroffen.");
            }
        }
    }

    // Statische Methoden zur Abfrage von Movie_Genre-Daten aus der Datenbank

    /**
     * Gibt eine Liste aller Movie_Genre-Beziehungen aus der Datenbank zurück.
     * @return Eine Liste von `Movie_Genre`-Objekten, die alle Beziehungen repräsentieren.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     */
    public static List<Movie_Genre> findAll() throws SQLException {
        // Verbindungsabruf für die Datenbankoperation
        Connection conn = aufgabe1.getConnection();
        List<Movie_Genre> movieGenreList = new ArrayList<>();

        // SQL-Statement, um alle Movie_Genre-Beziehungen abzurufen
        String inst = "SELECT MovieID, GenreID FROM MovieGenre";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Führt das SQL-Select-Statement aus und holt alle Movie_Genre-Beziehungen
            try (ResultSet rs = stmt.executeQuery()) {
                // Durchläuft das ResultSet und fügt jede Beziehung zur Liste hinzu
                while (rs.next()) {
                    Movie_Genre movieGenre = new Movie_Genre();
                    movieGenre.setMovieID(rs.getLong("MovieID"));
                    movieGenre.setGenreID(rs.getLong("GenreID"));
                    
                    // Fügt die gefundene Beziehung zur movieGenreList hinzu
                    movieGenreList.add(movieGenre);
                }
            }
        }

        return movieGenreList;
    }

    /**
     * Sucht eine Movie_Genre-Beziehung in der Datenbank basierend auf einer ID.
     * @param id Die ID der Movie_Genre-Beziehung.
     * @return Das `Movie_Genre`-Objekt mit den zugehörigen Daten.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalArgumentException Falls die Beziehung mit der gegebenen ID nicht existiert.
     */
    public static Movie_Genre findById(long id) throws SQLException {
        // Verbindungsabruf für die Datenbankoperation
        Connection conn = aufgabe1.getConnection();

        Movie_Genre movieGenre = new Movie_Genre();
        movieGenre.id = id;
        
        // SQL-Statement zur Suche der Beziehung nach ID
        String inst = "SELECT MovieID, GenreID FROM MovieGenre WHERE MovieGenreID = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt den Wert für die ID, um die spezifische Beziehung zu finden (? → id)
            stmt.setLong(1, id);
            
            // Führt das SQL-Select-Statement aus
            try (ResultSet rs = stmt.executeQuery()) {
                // Prüft, ob das ResultSet ein Ergebnis enthält
                if (!rs.next()) {
                    throw new IllegalArgumentException("Objekt mit ID " + id + " existiert nicht");
                }
                
                // Setzt die MovieID und GenreID des gefundenen Datensatzes
                movieGenre.setMovieID(rs.getLong("MovieID"));
                movieGenre.setGenreID(rs.getLong("GenreID"));
            }
        }
        
        return movieGenre;
    }
     
    /**
     * Sucht alle Genres für einen bestimmten Film anhand seiner ID.
     * @param movieID Die ID des Films.
     * @return Eine Liste von `Movie_Genre`-Objekten, die die Genres des Films repräsentieren.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     */
    public static List<Movie_Genre> findByMovieID(long movieID) throws SQLException {
        // Verbindungsabruf für die Datenbankoperation
        Connection conn = aufgabe1.getConnection();
        List<Movie_Genre> movieGenreList = new ArrayList<>();

        // SQL-Statement, um alle Genres für einen bestimmten Film abzurufen
        String inst = "SELECT MovieID, GenreID FROM MovieGenre WHERE MovieID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt den Wert für die MovieID zur Identifikation des Films (? → movieID)
            stmt.setLong(1, movieID);
            
            // Führt das SQL-Select-Statement aus und holt alle Genres für den Film
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Movie_Genre movieGenre = new Movie_Genre();
                    movieGenre.setMovieID(movieID);
                    movieGenre.setGenreID(rs.getLong("GenreID"));
                    
                    // Fügt die Beziehung zur movieGenreList hinzu
                    movieGenreList.add(movieGenre);
                }
            }
        }

        return movieGenreList;
    }

    /**
     * Sucht alle Filme für ein bestimmtes Genre anhand seiner ID.
     * @param genreID Die ID des Genres.
     * @return Eine Liste von `Movie_Genre`-Objekten, die die Filme des Genres repräsentieren.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     */
    public static List<Movie_Genre> findByGenreID(long genreID) throws SQLException {
        // Verbindungsabruf für die Datenbankoperation
        Connection conn = aufgabe1.getConnection();
        List<Movie_Genre> movieGenreList = new ArrayList<>();
                
        // SQL-Statement, um alle Filme für ein bestimmtes Genre abzurufen
        String inst = "SELECT MovieGenreID, MovieID FROM MovieGenre WHERE GenreID = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt den Wert für die GenreID zur Identifikation des Genres (? → genreID)
            stmt.setLong(1, genreID);
            
            // Führt das SQL-Select-Statement aus und holt alle Filme für das Genre
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Movie_Genre movieGenre = new Movie_Genre();
                    movieGenre.id = rs.getLong("MovieGenreID");
                    movieGenre.setMovieID(rs.getLong("MovieID"));
                    movieGenre.setGenreID(genreID);
                    
                    // Fügt die Beziehung zur movieGenreList hinzu
                    movieGenreList.add(movieGenre);
                }
            }
        }
        
        return movieGenreList;
    }
}
