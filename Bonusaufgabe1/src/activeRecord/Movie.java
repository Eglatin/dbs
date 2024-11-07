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
    private int year;
    private String type;

    // Getter and Setter
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    // Insert method
    public void insert() throws SQLException {
        if (id != null) {
            throw new IllegalStateException("Object has already been inserted");
        }
        try (Connection conn = aufgabe1.getConnection()) {
            String sqlInsert = "INSERT INTO Movie (Title, Year, Type) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, title);
                stmt.setInt(2, year);
                stmt.setString(3, type);
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
            String sql = "UPDATE Movie SET Title = ?, Year = ?, Type = ? WHERE MovieID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, title);
                stmt.setInt(2, year);
                stmt.setString(3, type);
                stmt.setLong(4, id);
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
            String sql = "DELETE FROM Movie WHERE MovieID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }
        }
    }

    // FindById method
    public static Movie findById(long id) throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "SELECT Title, Year, Type FROM Movie WHERE MovieID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Movie movie = new Movie();
                        movie.id = id;
                        movie.setTitle(rs.getString("Title"));
                        movie.setYear(rs.getInt("Year"));
                        movie.setType(rs.getString("Type"));
                        return movie;
                    }
                }
            }
        }
        return null;
    }

    // FindAll method
    public static List<Movie> findAll() throws SQLException {
        List<Movie> movieList = new ArrayList<>();
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "SELECT MovieID, Title, Year, Type FROM Movie";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Movie movie = new Movie();
                    movie.id = rs.getLong("MovieID");
                    movie.setTitle(rs.getString("Title"));
                    movie.setYear(rs.getInt("Year"));
                    movie.setType(rs.getString("Type"));
                    movieList.add(movie);
                }
            }
        }
        return movieList;
    }
}
