package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import aufgabe41.aufgabe1;


public class Movie_Genre {
	
	private Long id;
	
	private Long movieID;

	private Long genreID;
			
	//Getter and Setter
	
	public Long getId() {
		
		return id;
	}

	public long getMovieID() {
		
		return movieID;
	}

	public void setMovieID(long movieID) {
		
		this.movieID = movieID;
	}

	public long getGenreID() {
		
		return genreID;
	}

	public void setGenreID(long genreID) {
		
		this.genreID = genreID;
	}

	// Insert and Delete Methods
	
	public void insert() throws SQLException {
	    if (movieID == null || genreID == null) {
	        throw new IllegalStateException("MovieID and GenreID must not be null");
	    }

	    Connection conn = aufgabe1.getConnection();
	    String inst = "INSERT INTO MovieGenre(MovieID, GenreID) VALUES(?, ?)";

	    try (PreparedStatement stmt = conn.prepareStatement(inst)) {
	        stmt.setLong(1, movieID);
	        stmt.setLong(2, genreID);
	        stmt.executeUpdate();
	    }
	}

	
	public void delete() throws SQLException {
	    if (movieID == null || genreID == null) {
	        throw new IllegalStateException("MovieID and GenreID must not be null");
	    }

	    Connection conn = aufgabe1.getConnection();
	    String inst = "DELETE FROM MovieGenre WHERE MovieID = ? AND GenreID = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(inst)) {
	        stmt.setLong(1, movieID);
	        stmt.setLong(2, genreID);

	        stmt.executeUpdate();
	    }
	}

	
	public void update() throws SQLException {
	    if (movieID == null || genreID == null) {
	        throw new IllegalStateException("MovieID and GenreID must not be null");
	    }

	    Connection conn = aufgabe1.getConnection();
	    String inst = "UPDATE MovieGenre SET GenreID = ? WHERE MovieID = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(inst)) {
	        stmt.setLong(1, genreID); // Aktualisiert GenreID
	        stmt.setLong(2, movieID); // Nutzt movieID als Identifikator

	        int affectedRows = stmt.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Updating Movie_Genre failed, no rows affected.");
	        }
	    }
	}

	
    //Static Methods
	
	public static List<Movie_Genre> findAll() throws SQLException {
	    Connection conn = aufgabe1.getConnection();
	    List<Movie_Genre> movieGenreList = new ArrayList<>();

	    String inst = "SELECT MovieID, GenreID FROM MovieGenre";

	    try (PreparedStatement stmt = conn.prepareStatement(inst)) {
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Movie_Genre movieGenre = new Movie_Genre();
	                movieGenre.setMovieID(rs.getLong("MovieID"));
	                movieGenre.setGenreID(rs.getLong("GenreID"));
	                movieGenreList.add(movieGenre);
	            }
	        }
	    }

	    return movieGenreList;
	}

	
	public static Movie_Genre findById(long id) throws SQLException {
		
		Connection conn = aufgabe1.getConnection();

		Movie_Genre movieGenre = new Movie_Genre();
		
		movieGenre.id = id;
		
		String inst = "SELECT MovieID, GenreID FROM MovieGenre WHERE MovieGenreID = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(inst)) {
			
			stmt.setLong(1, id);
			
			try (ResultSet rs = stmt.executeQuery()) {
				
				if (!rs.next()) {
					
					throw new IllegalArgumentException("Object with ID " + id + " does not exist");
				}
				
				movieGenre.setMovieID(rs.getLong("MovieID"));
				movieGenre.setGenreID(rs.getLong("GenreID"));
			}
		}
		
		return movieGenre;
	}
	 
	public static List<Movie_Genre> findByMovieID(long movieID) throws SQLException {
	    Connection conn = aufgabe1.getConnection();
	    List<Movie_Genre> movieGenreList = new ArrayList<>();

	    String inst = "SELECT MovieID, GenreID FROM MovieGenre WHERE MovieID = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(inst)) {
	        stmt.setLong(1, movieID);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Movie_Genre movieGenre = new Movie_Genre();
	                movieGenre.setMovieID(movieID);
	                movieGenre.setGenreID(rs.getLong("GenreID"));
	                movieGenreList.add(movieGenre);
	            }
	        }
	    }

	    return movieGenreList;
	}

	
	public static List<Movie_Genre> findByGenreID(long genreID) throws SQLException {
		
		Connection conn = aufgabe1.getConnection();

		List<Movie_Genre> movieGenreList = new ArrayList<>();
				
		String inst = "SELECT MovieGenreID, MovieID FROM MovieGenre WHERE GenreID = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(inst)) {
			
			stmt.setLong(1, genreID);
			
			try (ResultSet rs = stmt.executeQuery()) {
				
				while (rs.next()) {
					
					Movie_Genre movieGenre = new Movie_Genre();
					
					movieGenre.id = rs.getLong("MovieGenreID");
					movieGenre.setMovieID(rs.getLong("MovieID"));
					movieGenre.setGenreID(genreID);
					
					movieGenreList.add(movieGenre);
				}
			}
		}
		
		return movieGenreList;
	}
}