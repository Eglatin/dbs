package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import aufgabe41.aufgabe1;

public class Genre {

    private Long id;
    private String name;

    // Getter and Setter
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // Insert method
    public void insert() throws SQLException {
        if (id != null) {
            throw new IllegalStateException("Object has already been inserted");
        }
        try (Connection conn = aufgabe1.getConnection()) {
            String sqlInsert = "INSERT INTO Genre (Genre) VALUES (?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, name);
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                }
            }
        }
    }

    // Update method
    public void update() throws SQLException {
        if (id == null) {
            throw new IllegalStateException("Object has not been inserted");
        }
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "UPDATE Genre SET Genre = ? WHERE GenreID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setLong(2, id);
                stmt.executeUpdate();
            }
        }
    }

    // Delete method
    public void delete() throws SQLException {
        if (id == null) {
            throw new IllegalStateException("Object has not been inserted");
        }
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "DELETE FROM Genre WHERE GenreID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }
        }
    }

    // FindById method
    public static Genre findById(long id) throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "SELECT Genre FROM Genre WHERE GenreID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Genre genre = new Genre();
                        genre.id = id;
                        genre.setName(rs.getString("Genre"));
                        return genre;
                    }
                }
            }
        }
        return null;
    }

    // FindAll method
    public static List<Genre> findAll() throws SQLException {
        List<Genre> genreList = new ArrayList<>();
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "SELECT GenreID, Genre FROM Genre";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Genre genre = new Genre();
                    genre.id = rs.getLong("GenreID");
                    genre.setName(rs.getString("Genre"));
                    genreList.add(genre);
                }
            }
        }
        return genreList;
    }
}
