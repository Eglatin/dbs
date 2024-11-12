package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import aufgabe41.aufgabe1;

/**
 * Die Klasse `Genre` repräsentiert ein Genre in der Datenbank.
 * Jedes Genre hat eine eindeutige ID und einen Namen.
 * Die Klasse bietet Methoden zum Einfügen, Aktualisieren, Löschen und
 * Abrufen von Genres aus der Datenbank.
 */
public class Genre {

    // Eindeutige ID des Genres, die beim Einfügen in die Datenbank gesetzt wird.
    private Long id;

    // Name des Genres, dieser Wert darf nicht null sein und sollte eindeutig sein.
    private String genre;

    // Getter- und Setter-Methoden für die Attribute der Klasse
    
    /**
     * Gibt die ID des Genres zurück.
     * @return ID des Genres als Long
     */
    public Long getID() { 
        return id;
    }

    /**
     * Gibt den Namen des Genres zurück.
     * @return Name des Genres als String
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Setzt den Namen des Genres.
     * @param genre Der Name des Genres als String, darf nicht null sein.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    // Methoden zur Datenbankmanipulation (Einfügen, Aktualisieren, Löschen)
    
    /**
     * Fügt ein neues Genre in die Datenbank ein.
     * Die ID des Genres wird automatisch generiert und nach dem Einfügen gesetzt.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalStateException Falls das Genre bereits eine ID besitzt (bereits eingefügt wurde).
     */
    public void insert() throws SQLException {
        // Verhindert das Einfügen, wenn das Genre bereits eine ID hat
        if (id != null) {
            throw new IllegalStateException("Objekt wurde bereits eingefügt");
        }

        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();
        
        // SQL-Statement zum Einfügen eines neuen Genres in die `Genre`-Tabelle
        String inst1 = "INSERT INTO Genre(Genre) VALUES(?)";

        // SQL-Statement zum Abrufen der neu generierten ID nach dem Einfügen
        String inst2 = "SELECT last_insert_rowid() AS GenreID";

        try (PreparedStatement stmt1 = conn.prepareStatement(inst1)) {
            // Setzt den Namen des Genres für den ersten Platzhalter (? → genre)
            stmt1.setString(1, genre);
            
            // Führt das SQL-Insert-Statement aus, um das Genre in die Datenbank einzufügen
            stmt1.executeUpdate();

            // Abrufen der generierten ID nach dem Einfügen
            try (PreparedStatement stmt2 = conn.prepareStatement(inst2)){
                try (ResultSet rs = stmt2.executeQuery()) {
                    rs.next();
                    // Speichert die generierte GenreID in der ID-Variable
                    id = rs.getLong("GenreID");
                }
            }
        }
    }

    /**
     * Aktualisiert die vorhandenen Daten eines Genres in der Datenbank.
     * Das Genre muss bereits in die Datenbank eingefügt worden sein.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalStateException Falls das Genre noch keine ID besitzt.
     */
    public void update() throws SQLException {
        // Verhindert das Aktualisieren, wenn das Genre noch keine ID hat
        if (id == null) {
            throw new IllegalStateException("Objekt wurde noch nicht eingefügt");
        }

        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();
        
        // SQL-Statement zum Aktualisieren der Genre-Daten basierend auf der ID
        String inst = "UPDATE Genre SET Genre = ? WHERE GenreID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt den Namen des Genres für den ersten Platzhalter (? → genre)
            stmt.setString(1, genre);
            // Setzt die ID des Genres als Identifikator für das zu aktualisierende Datensatz (? → id)
            stmt.setLong(2, id);

            // Führt das SQL-Update-Statement aus, um das Genre zu aktualisieren
            stmt.executeUpdate();
        }
    }

    /**
     * Löscht ein Genre aus der Datenbank.
     * Das Genre muss bereits in die Datenbank eingefügt worden sein.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalStateException Falls das Genre noch keine ID besitzt.
     */
    public void delete() throws SQLException {
        // Verhindert das Löschen, wenn das Genre noch keine ID hat
        if (id == null) {
            throw new IllegalStateException("Objekt wurde noch nicht eingefügt");
        }

        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();

        // SQL-Statement zum Löschen des Genres basierend auf der ID
        String inst = "DELETE FROM Genre WHERE GenreID = ? ";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt die ID des Genres als Parameter für das Delete-Statement (? → id)
            stmt.setLong(1, id);

            // Führt das SQL-Delete-Statement aus, um das Genre zu löschen
            stmt.executeUpdate();
        }
    }

    // Statische Methoden zur Abfrage von Genre-Daten aus der Datenbank

    /**
     * Gibt eine Liste aller Genres aus der Datenbank zurück.
     * @return Eine Liste von `Genre`-Objekten, die alle Genres in der Datenbank repräsentieren.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     */
    public static List<Genre> findAll() throws SQLException {
        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();

        List<Genre> genreList = new ArrayList<>();

        // SQL-Statement, um alle Genres abzurufen
        String inst = "SELECT GenreID, Genre FROM Genre";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Führt das SQL-Select-Statement aus und holt alle Genres aus der Tabelle
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Genre genre = new Genre();
                    // Setzt die ID und den Namen für jedes gefundene Genre
                    genre.id = rs.getLong("GenreID");
                    genre.setGenre(rs.getString("Genre"));

                    // Fügt das gefundene Genre zur genreList hinzu
                    genreList.add(genre);
                }
            }
        }

        return genreList;
    }

    /**
     * Sucht ein Genre in der Datenbank basierend auf der ID.
     * @param id Die ID des Genres.
     * @return Das `Genre`-Objekt mit den Daten des Genres.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalArgumentException Falls das Genre mit der gegebenen ID nicht existiert.
     */
    public static Genre findById(long id) throws SQLException {
        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();

        Genre genre = new Genre();

        // SQL-Statement zur Suche des Genres nach ID
        String inst = "SELECT Genre FROM Genre WHERE GenreID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt die ID des Genres als Parameter für das Select-Statement (? → id)
            stmt.setLong(1, id);

            // Führt das SQL-Select-Statement aus, um das Genre anhand der ID zu suchen
            try (ResultSet rs = stmt.executeQuery()) {
                // Prüft, ob das ResultSet ein Ergebnis enthält (Genre mit der ID existiert)
                if (!rs.next()) {
                    throw new IllegalArgumentException("Objekt mit ID " + id + " existiert nicht");
                }

                // Setzt den Namen des gefundenen Genres und speichert die ID
                genre.genre = rs.getString("Genre");
                genre.id = id;
            }
        }

        return genre;
    }

    /**
     * Sucht alle Genres, deren Name den Suchstring enthält.
     * @param genre Der Name oder Teil des Namens der gesuchten Genres.
     * @return Eine Liste von `Genre`-Objekten, deren Name den Suchstring enthält.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     */
    public static List<Genre> findByGenre(String genre) throws SQLException {
        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();

        List<Genre> genreList = new ArrayList<>();

        // SQL-Statement zur Suche von Genres nach Namen (case-insensitive)
        String inst = "SELECT GenreID, Genre FROM Genre WHERE LOWER(Genre) LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt den Namen als Parameter für das LIKE-Statement, mit % für die Wildcard-Suche (? → genre)
            stmt.setString(1, "%" + genre.toLowerCase() + "%");

            // Führt das SQL-Select-Statement aus und holt alle Genres, deren Name den Suchstring enthält
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Genre newGenre = new Genre();
                    // Setzt die ID und den Namen für jedes gefundene Genre
                    newGenre.id = rs.getLong("GenreID");
                    newGenre.setGenre(rs.getString("Genre"));

                    // Fügt das gefundene Genre zur genreList hinzu
                    genreList.add(newGenre);
                }
            }
        }

        return genreList;
    }
}
