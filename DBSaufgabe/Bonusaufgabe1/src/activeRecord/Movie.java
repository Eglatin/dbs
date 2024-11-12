package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

import dtos.MovieDTO;
import aufgabe41.aufgabe1;

public class Movie {

    private Long id;

    private String title;
    
    private int year;
    
    private char type;
    
    //Getter and Setter
    
    public Long getId() {
        
        return id;
    }

    public String getTitle() {
        
        return title;
    }

    public void setTitle(String title) {
        
        this.title = title;
    }

    public int getYear() {
        
        return year;
    }

    public void setYear(int year) {
        
        this.year = year;
    }

    public char getType() {
        
        return type;
    }

    public void setType(char type) {
        
        this.type = type;
    }
    
    // Insert, Update and Delete Methods
    
    public void insert() throws SQLException {
        if (id != null) {
            throw new IllegalStateException("Object has already been inserted");
        }

        Connection conn = aufgabe1.getConnection();
        String inst = "INSERT INTO Movie(title, year, type) VALUES(?, ?, ?) RETURNING movieid";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            stmt.setString(1, title);
            stmt.setInt(2, year);
            stmt.setObject(3, type, java.sql.Types.CHAR);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    id = rs.getLong(1); // Speichert die generierte movieid in der Variable id
                }
            }
        }
    }


    
    public void update() throws SQLException {
        
        if (id == null) {
            
            throw new IllegalStateException("Object has not been inserted");
        }
        
        Connection conn = aufgabe1.getConnection();
        
        String inst = "UPDATE Movie SET Title = ?, Year = ?, Type = ? WHERE MovieID = ?";
                
        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            
            stmt.setString(1, title);
            stmt.setInt(2, year);
            stmt.setString(3, String.valueOf(type));
            stmt.setLong(4, id);
            
            stmt.executeUpdate();
        }
    }
    
    public void delete() throws SQLException {
        
        if (id == null) {
            
            throw new IllegalStateException("Object has not been inserted");
        }
        
        Connection conn = aufgabe1.getConnection();
        
        String inst = "DELETE FROM Movie WHERE MovieID = ? ";
                
        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            
            stmt.setLong(1, id);
            
            stmt.executeUpdate();
        }
    }
    
    //To DTO Method
    
    public MovieDTO toDTO() throws SQLException {
        
        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setId(this.id);
        movieDTO.setTitle(this.title);
        movieDTO.setYear(this.year);
        movieDTO.setType(String.valueOf(this.type));

        movieDTO.setGenres(new HashSet<>());
        movieDTO.setCharacters(new ArrayList<>());

                 
        for (Movie_Genre movieGenre : Movie_Genre.findByMovieID(this.id)) {
            
             movieDTO.addGenre(Genre.findById(movieGenre.getGenreID()).getGenre()); 
        }
       
       
        for (Character Character : Character.findByMovieID(this.id)) {
            
            movieDTO.addCharacter(Character.toDTO());       
        }

        return movieDTO;
    }
    
    //Static Methods
    
    public static List<Movie> findAll() throws SQLException {
        
        Connection conn = aufgabe1.getConnection();

        List<Movie> movieList = new ArrayList<>();
        
        String inst = "SELECT MovieID, Title, Year, Type FROM Movie";
        
        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
                
            try (ResultSet rs = stmt.executeQuery()) {
                
                Movie movie = new Movie();
                
                movie.id = rs.getLong("MovieID");
                movie.setTitle(rs.getString("Title"));
                movie.setYear(rs.getInt("Year"));
                movie.setType(rs.getString("Type").charAt(0));
                
                movieList.add(movie);
            }
        }
        
        return movieList;
    }
    
    public static Movie findById(long id) throws SQLException {
        
        Connection conn = aufgabe1.getConnection();

        Movie movie = new Movie();
        
        movie.id = id;
        
        String inst = "SELECT Title, Year, Type FROM Movie WHERE MovieID = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                
                if (!rs.next()) {
                    
                    throw new IllegalArgumentException("Object with ID " + id + " does not exist");
                }
                
                movie.setTitle(rs.getString("Title"));
                movie.setYear(rs.getInt("Year"));
                movie.setType(rs.getString("Type").charAt(0));
            }
        }
        
        return movie;
    }
     
    public static List<Movie> findByTitle(String title) throws SQLException {
        
        Connection conn = aufgabe1.getConnection();

        List<Movie> movies = new ArrayList<>();
                
        String inst = "SELECT MovieID, Title, Year, Type FROM Movie WHERE LOWER(Title) LIKE ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            
            stmt.setString(1, "%" + title.toLowerCase() + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    
                    Movie movie = new Movie();
                    
                    movie.id = rs.getLong("MovieID");
                    movie.setTitle(rs.getString("Title"));
                    movie.setYear(rs.getInt("Year"));
                    movie.setType(rs.getString("Type").charAt(0));
                    
                    movies.add(movie);
                }
            }
        }
        
        return movies;
    }
}