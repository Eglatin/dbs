package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import aufgabe41.aufgabe1; // Importiere die aufgabe1-Klasse

public class Character {

    private Long id;
    private String character;
    private String alias;
    private int position;
    private Long movieID;
    private Long personID;

    // Getter and Setter
    public Long getId() { return id; }
    public String getCharacter() { return character; }
    public void setCharacter(String character) { this.character = character; }
    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }
    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
    public Long getMovieID() { return movieID; }
    public void setMovieID(Long movieID) { this.movieID = movieID; }
    public Long getPersonID() { return personID; }
    public void setPersonID(Long personID) { this.personID = personID; }

    // Insert method
    public void insert() throws SQLException {
        if (id != null) {
            throw new IllegalStateException("Object has already been inserted");
        }
        try (Connection conn = aufgabe1.getConnection()) {  // Verbindung aus aufgabe1-Klasse holen
            String sqlInsert = "INSERT INTO Character (Character, Alias, Position, MovieID, PersonID) VALUES (?, ?, ?, ?, ?)";
            String sqlRetrieveId = "SELECT last_insert_rowid() AS CharacterID";

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, character);
                stmtInsert.setString(2, alias);
                stmtInsert.setInt(3, position);
                stmtInsert.setLong(4, movieID);
                stmtInsert.setLong(5, personID);
                stmtInsert.executeUpdate();

                try (PreparedStatement stmtRetrieveId = conn.prepareStatement(sqlRetrieveId);
                     ResultSet rs = stmtRetrieveId.executeQuery()) {
                    if (rs.next()) {
                        id = rs.getLong("CharacterID");
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
            String sql = "UPDATE Character SET Character = ?, Alias = ?, Position = ?, MovieID = ?, PersonID = ? WHERE CharacterID = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, character);
                stmt.setString(2, alias);
                stmt.setInt(3, position);
                stmt.setLong(4, movieID);
                stmt.setLong(5, personID);
                stmt.setLong(6, id);
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
            String sql = "DELETE FROM Character WHERE CharacterID = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }
        }
    }

    // Find methods
    public static Character findById(long id) throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "SELECT Character, Alias, Position, MovieID, PersonID FROM Character WHERE CharacterID = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Character character = new Character();
                        character.id = id;
                        character.setCharacter(rs.getString("Character"));
                        character.setAlias(rs.getString("Alias"));
                        character.setPosition(rs.getInt("Position"));
                        character.setMovieID(rs.getLong("MovieID"));
                        character.setPersonID(rs.getLong("PersonID"));
                        return character;
                    }
                }
            }
        }
        return null;
    }

    public static List<Character> findAll() throws SQLException {
        List<Character> characterList = new ArrayList<>();
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "SELECT CharacterID, Character, Alias, Position, MovieID, PersonID FROM Character";

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Character character = new Character();
                    character.id = rs.getLong("CharacterID");
                    character.setCharacter(rs.getString("Character"));
                    character.setAlias(rs.getString("Alias"));
                    character.setPosition(rs.getInt("Position"));
                    character.setMovieID(rs.getLong("MovieID"));
                    character.setPersonID(rs.getLong("PersonID"));
                    characterList.add(character);
                }
            }
        }
        return characterList;
    }
}
