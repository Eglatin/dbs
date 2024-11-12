package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dtos.CharacterDTO;
import aufgabe41.aufgabe1;

public class Character {

    private Long id;
    
    private String character;
    
    private String alias;
    
    private int position;
    
    private Long movieID;
    
    private Long personID; 

    // Getter and Setter
    
    public Long getID() {
    	
        return id;
    }

    public String getCharacter(){
    	
        return character;
    }
    
    public void setCharacter(String character) {
    	
        this.character = character;
    }
    
    public String getAlias() {
    	
        return alias;
    }

    public void setAlias(String alias) {
    	
        this.alias = alias;
    }

    public int getPosition() {
    	
        return position;
    }

    public void setPosition(int position) {
    	
        this.position = position;
    }
    
    public Long getMovieID() {
    	
        return movieID;
    }
    
    public void setMovieID(Long movieID){
    	
        this.movieID = movieID;
    }

    public Long getPersonID(){
    	
        return personID;
    }

    public void setPersonID(Long personID){
    	
        this.personID = personID;
    }

	// Insert, Update and Delete Methods
    
    public void insert() throws SQLException {
    	
        if (id != null) {
        	
            throw new IllegalStateException("Object has already been inserted");
        }

        Connection conn = aufgabe1.getConnection();
        String inst1 = "INSERT INTO MovieCharacter(Character, Alias, Position, MovieID, PersonID) VALUES(?, ?, ?, ?, ?)";
        String inst2 = "SELECT last_insert_rowid() AS MovCharID";

        try (PreparedStatement stmt1 = conn.prepareStatement(inst1)) {

            stmt1.setString(1, character);
            stmt1.setString(2, alias);
            stmt1.setInt(3, position);
            stmt1.setLong(4, movieID);
            stmt1.setLong(5, personID);
            
            stmt1.executeUpdate();

            try (PreparedStatement stmt2 = conn.prepareStatement(inst2)) {
            	
                try (ResultSet rs = stmt2.executeQuery()) {
                	
                    rs.next();
                    
                    id = rs.getLong("MovCharID");
                }
            }
        }
    }

    public void update() throws SQLException {
    	
        if (id == null) {
        	
        	
            throw new IllegalStateException("Object has not been inserted");
        }

        Connection conn = aufgabe1.getConnection();
        String inst = "UPDATE MovieCharacter SET Character = ?, Alias = ?, Position = ?, MovieID = ?, PersonID = ? WHERE MovCharID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {

        	stmt.setString(1, character);
            stmt.setString(2, alias);
            stmt.setInt(3, position);
            stmt.setLong(4, movieID);
            stmt.setLong(5, personID);
            stmt.setLong(6, id);
            
            stmt.executeUpdate();
        }
    }


    public void delete() throws SQLException {
    	
        if (id == null) {
        	
            throw new IllegalStateException("Object has not been inserted");
        }

        Connection conn = aufgabe1.getConnection();
        String inst = "DELETE FROM MovieCharacter WHERE MovCharID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
        	
            stmt.setLong(1, id);
            
            stmt.executeUpdate();
            
        }
    }
    
	//To DTO Method
    
	public CharacterDTO toDTO() throws SQLException {
		
        CharacterDTO characterDTO = new CharacterDTO();

        characterDTO.setCharacter(this.character);
        characterDTO.setAlias(this.alias);
        characterDTO.setPlayer(Person.findById(this.personID).getName());

        return characterDTO;
    }
	
    //Static Methods

    public static List<Character> findAll() throws SQLException {
    	
        Connection conn = aufgabe1.getConnection();
        
        List<Character> movieCharacterList = new ArrayList<>();
        
        String inst = "SELECT MovCharID, Character, Alias, Position, MovieID, PersonID FROM MovieCharacter";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
        	
            try (ResultSet rs = stmt.executeQuery()) {
            	
                Character Character = new Character();
                
                Character.id = rs.getLong("MovCharID");
                Character.setCharacter(rs.getString("Character"));
                Character.setAlias(rs.getString("Alias"));
                Character.setPosition(rs.getInt("Position"));
                Character.setMovieID(rs.getLong("MovieID"));
                Character.setPersonID(rs.getLong("PersonID"));
                
                movieCharacterList.add(Character);
            }
        }

        return movieCharacterList;
    }
	
    public static Character findById(long id) throws SQLException {
    	
        Connection conn = aufgabe1.getConnection();
        
        Character movieCharacter = new Character();
        
        movieCharacter.id = id;

        String inst = "SELECT Character, Alias, Position, MovieID, PersonID FROM MovieCharacter WHERE MovCharID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
        	
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
            	
                if (!rs.next()) {
                	
                    throw new IllegalArgumentException("Object with ID " + id + " does not exist");
                }

                movieCharacter.setCharacter(rs.getString("Character"));
                movieCharacter.setAlias(rs.getString("Alias"));
                movieCharacter.setPosition(rs.getInt("Position"));
                movieCharacter.setMovieID(rs.getLong("MovieID"));
                movieCharacter.setPersonID(rs.getLong("PersonID"));
            }
        }

        return movieCharacter;
    }
    
	public static List<Character> findByMovieID(long movieID) throws SQLException {
		
		Connection conn = aufgabe1.getConnection();

		List<Character> movieCharacterList = new ArrayList<>();
				
		String inst = "SELECT MovCharID, Character, Alias, Position, PersonID FROM MovieCharacter WHERE MovieID = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(inst)) {
			
			stmt.setLong(1, movieID);
			
			try (ResultSet rs = stmt.executeQuery()) {
				
				while (rs.next()) {
					
					Character Character = new Character();
					
					Character.id = rs.getLong("MovCharID");
					Character.character = rs.getString("Character");
					Character.alias = rs.getString("Alias");
					Character.position = rs.getInt("Position");
					Character.setMovieID(movieID);
					Character.setPersonID(rs.getLong("PersonID"));
					
					movieCharacterList.add(Character);
				}
			}
		}
		
		return movieCharacterList;
	}
	
	public static List<Character> findByPersonID(long personID) throws SQLException {
		
		Connection conn = aufgabe1.getConnection();

		List<Character> movieCharacterList = new ArrayList<>();
				
		String inst = "SELECT MovCharID, Character, Alias, Position, MovieID FROM MovieCharacter WHERE PersonID = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(inst)) {
			
			stmt.setLong(1, personID);
			
			try (ResultSet rs = stmt.executeQuery()) {
				
				while (rs.next()) {
					
					Character Character = new Character();
					
					Character.id = rs.getLong("MovieGenreID");
					Character.character = rs.getString("Character");
					Character.alias = rs.getString("Alias");
					Character.position = rs.getInt("Position");
					Character.setMovieID(rs.getLong("MovieID"));
					Character.setPersonID(personID);
					
					movieCharacterList.add(Character);
				}
			}
		}
		
		return movieCharacterList;
	}
}