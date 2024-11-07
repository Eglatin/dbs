package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import aufgabe41.aufgabe1;

public class Movie_Genre {

    private Long movieId;
    private Long genreId;

    public Movie_Genre(Long movieId, Long genreId) {
        this.movieId = movieId;
        this.genreId = genreId;
    }

    // Getter and Setter
    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }
    public Long getGenreId() { return genreId; }
    public void setGenreId(Long genreId) { this.genreId = genreId; }

    // Insert method for the relationship
    public void insert() throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sqlInsert = "INSERT INTO Movie_Genre (MovieID, GenreID) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {
                stmt.setLong(1, movieId);
                stmt.setLong(2, genreId);
                stmt.executeUpdate();
            }
        }
    }

    // Delete method for the relationship
    public void delete() throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sqlDelete = "DELETE FROM Movie_Genre WHERE MovieID = ? AND GenreID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlDelete)) {
                stmt.setLong(1, movieId);
                stmt.setLong(2, genreId);
                stmt.executeUpdate();
            }
        }
    }
}
