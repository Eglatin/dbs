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

    public Genre() {}

    public Genre(String name) {
        this.name = name;
    }

    // Getter und Setter für alle Attribute
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Insert-Methode
    public void insert() throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "INSERT INTO Genre (name) VALUES (?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, name);
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.id = rs.getLong(1);
                    }
                }
            }
        }
    }

    // FindAll-Methode
    public static List<Genre> findAll() throws SQLException {
        List<Genre> genres = new ArrayList<>();
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "SELECT * FROM Genre";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Genre genre = new Genre();
                    genre.id = rs.getLong("id");
                    genre.name = rs.getString("name");
                    genres.add(genre);
                }
            }
        }
        return genres;
    }

    // FindByName-Methode
    public static Genre findByName(String name) throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "SELECT * FROM Genre WHERE name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Genre genre = new Genre();
                        genre.id = rs.getLong("id");
                        genre.name = rs.getString("name");
                        return genre;
                    }
                }
            }
        }
        return null;
    }
}
