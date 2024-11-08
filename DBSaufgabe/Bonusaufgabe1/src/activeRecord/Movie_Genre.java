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

    // Insert-Methode
    public void insert() throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "INSERT INTO Movie_Genre (movie_id, genre_id) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, movieId);
                stmt.setLong(2, genreId);
                stmt.executeUpdate();
            }
        }
    }

    // Delete-Methode
    public static void deleteByMovieId(long movieId) throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "DELETE FROM Movie_Genre WHERE movie_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, movieId);
                stmt.executeUpdate();
            }
        }
    }

    public static void deleteByGenreId(long genreId) throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "DELETE FROM Movie_Genre WHERE genre_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, genreId);
                stmt.executeUpdate();
            }
        }
    }
}
