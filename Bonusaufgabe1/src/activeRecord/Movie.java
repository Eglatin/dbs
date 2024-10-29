package activeRecord;

//Aufgabe 4.3
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private Long id;           // ID vom Typ Long, um NULL-Werte zuzulassen
    private String title;      // Titel des Films
    private Integer year;      // Erscheinungsjahr
    private Character type;    // Typ als char, hier als Character für NULL-Werte

    // Konstruktoren
    public Movie() {}
    
    public Movie(String title, Integer year, Character type) {
        this.id = null;
        this.title = title;
        this.year = year;
        this.type = type;
    }

    // Getter und Setter für Attribute
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Character getType() {
        return type;
    }

    public void setType(Character type) {
        this.type = type;
    }
    
    // Aufgabe 4.4
    // Methode zum Abrufen eines Movies nach ID
    public static Movie findById(Connection conn, long id) throws SQLException {
        String sql = "SELECT movieid, title, year, type FROM Movie WHERE movieid = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Movie-Objekt mit den abgerufenen Daten erstellen
                    Movie movie = new Movie();
                    movie.id = rs.getLong("movieid");
                    movie.title = rs.getString("title");
                    movie.year = rs.getInt("year");
                    movie.type = rs.getString("type").charAt(0);
                    return movie;
                } else {
                    return null; // Kein Film mit dieser ID gefunden
                }
            }
        }
    }

    // Methode zum Abrufen von Movies nach Titel (enthält)
    public static List<Movie> findByTitle(Connection conn, String title) throws SQLException {
        String sql = "SELECT movieid, title, year, type FROM Movie WHERE LOWER(title) LIKE LOWER(?)";
        List<Movie> movies = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + title + "%"); // Teilstring-Suche
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Movie-Objekt mit den abgerufenen Daten erstellen und zur Liste hinzufügen
                    Movie movie = new Movie();
                    movie.id = rs.getLong("movieid");
                    movie.title = rs.getString("title");
                    movie.year = rs.getInt("year");
                    movie.type = rs.getString("type").charAt(0);
                    movies.add(movie);
                }
            }
        }
        return movies;
    }

    // Wieder Aufgabe 4.3
    // Insert-Methode: Fügt einen neuen Datensatz hinzu und setzt die ID
    public void insert(Connection conn) throws SQLException {
        if (this.id != null) {
            throw new IllegalStateException("Insert can only be called if ID is not set.");
        }

        // Nur die Spalten angeben, die keine automatisch generierte ID benötigen
        String insertSQL = "INSERT INTO Movie (title, year, type) VALUES (?, ?, ?)";

        try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStmt.setString(1, this.title);
            insertStmt.setInt(2, this.year);
            insertStmt.setString(3, String.valueOf(this.type));
            insertStmt.executeUpdate();

            // ID abfragen und im Objekt setzen
            try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.id = generatedKeys.getLong(1);
                }
            }
        }
    }


    // Update-Methode: Aktualisiert den Datensatz in der Datenbank
    public void update(Connection conn) throws SQLException {
        if (this.id == null) {
            throw new IllegalStateException("Update can only be called if ID is set.");
        }

        // Verwende den korrekten Spaltennamen 'movieid' statt 'id'
        String updateSQL = "UPDATE Movie SET title = ?, year = ?, type = ? WHERE movieid = ?";

        try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
            updateStmt.setString(1, this.title);
            updateStmt.setInt(2, this.year);
            updateStmt.setString(3, String.valueOf(this.type));
            updateStmt.setLong(4, this.id);
            int rowsUpdated = updateStmt.executeUpdate();
            
            if (rowsUpdated == 0) {
                throw new SQLException("No record found to update with ID: " + this.id);
            }
        }
    }


    // Delete-Methode: Löscht den Datensatz aus der Datenbank
    public void delete(Connection conn) throws SQLException {
        if (this.id == null) {
            throw new IllegalStateException("Delete can only be called if ID is set.");
        }

        // Verwende den korrekten Spaltennamen 'movieid' statt 'id'
        String deleteSQL = "DELETE FROM Movie WHERE movieid = ?";

        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL)) {
            deleteStmt.setLong(1, this.id);
            int rowsDeleted = deleteStmt.executeUpdate();

            if (rowsDeleted == 0) {
                throw new SQLException("No record found to delete with ID: " + this.id);
            }
            this.id = null; // Setzt die ID zurück, da der Datensatz gelöscht wurde
        }
    }
}