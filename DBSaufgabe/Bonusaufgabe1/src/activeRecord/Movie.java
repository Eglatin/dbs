package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import aufgabe41.aufgabe1;

public class Movie {
    private Long id;
    private String title;
    private Integer year;
    private String type;

    public Movie() {}

    public Movie(String title, Integer year, String type) {
        this.title = title;
        this.year = year;
        this.type = type;
    }

    // Getter und Setter für alle Attribute
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Insert-Methode
    public void insert() throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "INSERT INTO Movie (title, year, type) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, title);
                stmt.setInt(2, year);
                stmt.setString(3, type);
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.id = rs.getLong(1);
                    }
                }
            }
        }
    }

    // Update-Methode
    public void update() throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "UPDATE Movie SET title = ?, year = ?, type = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, title);
                stmt.setInt(2, year);
                stmt.setString(3, type);
                stmt.setLong(4, id);
                stmt.executeUpdate();
            }
        }
    }

    // Delete-Methode
    public void delete() throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "DELETE FROM Movie WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }
        }
    }

    // FindById-Methode
    public static Movie findById(long id) throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "SELECT * FROM Movie WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Movie movie = new Movie();
                        movie.id = rs.getLong("id");
                        movie.title = rs.getString("title");
                        movie.year = rs.getInt("year");
                        movie.type = rs.getString("type");
                        return movie;
                    }
                }
            }
        }
        return null;
    }

    // FindByTitle-Methode (Suche nach Filmtitel)
    public static List<Movie> findByTitle(String title) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "SELECT * FROM Movie WHERE LOWER(title) LIKE LOWER(?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, "%" + title + "%");
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Movie movie = new Movie();
                        movie.id = rs.getLong("id");
                        movie.title = rs.getString("title");
                        movie.year = rs.getInt("year");
                        movie.type = rs.getString("type");
                        movies.add(movie);
                    }
                }
            }
        }
        return movies;
    }
}
