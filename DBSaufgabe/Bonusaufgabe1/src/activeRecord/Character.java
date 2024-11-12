package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dtos.CharacterDTO;
import aufgabe41.aufgabe1;

/**
 * Die Klasse `Character` repräsentiert eine Filmfigur in der Datenbank.
 * Jede Figur hat eine eindeutige ID, einen Namen, einen Alias, eine Position im Film, 
 * und Verweise auf einen Film und eine Person (Darsteller).
 * Die Klasse bietet Methoden zum Einfügen, Aktualisieren, Löschen und
 * Abrufen von Figuren aus der Datenbank.
 */
public class Character {

    // Eindeutige ID der Figur, die beim Einfügen in die Datenbank gesetzt wird.
    private Long id;
    
    // Name der Figur im Film, z.B. "Luke Skywalker".
    private String character;
    
    // Alias oder Spitzname der Figur, z.B. "Jedi-Ritter".
    private String alias;
    
    // Position der Figur innerhalb des Films, z.B. 1 für die Hauptfigur.
    private int position;
    
    // ID des Films, zu dem die Figur gehört.
    private Long movieID;
    
    // ID der Person (Darsteller), die die Figur spielt.
    private Long personID;

    // Getter und Setter-Methoden für die Attribute der Klasse
    
    /**
     * Gibt die ID der Figur zurück.
     * @return ID der Figur als Long
     */
    public Long getID() {
        return id;
    }

    /**
     * Gibt den Namen der Figur zurück.
     * @return Name der Figur als String
     */
    public String getCharacter(){
        return character;
    }

    /**
     * Setzt den Namen der Figur.
     * @param character Der Name der Figur als String, darf nicht null sein.
     */
    public void setCharacter(String character) {
        this.character = character;
    }

    /**
     * Gibt den Alias der Figur zurück.
     * @return Alias der Figur als String
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Setzt den Alias der Figur.
     * @param alias Der Alias der Figur als String.
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Gibt die Position der Figur im Film zurück.
     * @return Position der Figur als int
     */
    public int getPosition() {
        return position;
    }

    /**
     * Setzt die Position der Figur im Film.
     * @param position Die Position der Figur als int.
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Gibt die ID des Films zurück, zu dem die Figur gehört.
     * @return Film-ID als Long
     */
    public Long getMovieID() {
        return movieID;
    }

    /**
     * Setzt die ID des Films, zu dem die Figur gehört.
     * @param movieID Die Film-ID als Long.
     */
    public void setMovieID(Long movieID){
        this.movieID = movieID;
    }

    /**
     * Gibt die ID der Person (Darsteller) zurück, die die Figur spielt.
     * @return Personen-ID als Long
     */
    public Long getPersonID(){
        return personID;
    }

    /**
     * Setzt die ID der Person (Darsteller), die die Figur spielt.
     * @param personID Die Personen-ID als Long.
     */
    public void setPersonID(Long personID){
        this.personID = personID;
    }

    // Methoden zur Datenbankmanipulation (Einfügen, Aktualisieren, Löschen)

    /**
     * Fügt eine neue Figur in die Datenbank ein.
     * Die ID der Figur wird automatisch generiert und nach dem Einfügen gesetzt.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalStateException Falls die Figur bereits eine ID besitzt (bereits eingefügt wurde).
     */
    public void insert() throws SQLException {
        // Verhindert das Einfügen, wenn die Figur bereits eine ID hat
        if (id != null) {
            throw new IllegalStateException("Objekt wurde bereits eingefügt");
        }

        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();
        
        // SQL-Statement zum Einfügen einer neuen Figur in die `MovieCharacter`-Tabelle
        String inst1 = "INSERT INTO MovieCharacter(Character, Alias, Position, MovieID, PersonID) VALUES(?, ?, ?, ?, ?)";
        
        // SQL-Statement zum Abrufen der neu generierten ID nach dem Einfügen
        String inst2 = "SELECT last_insert_rowid() AS MovCharID";

        try (PreparedStatement stmt1 = conn.prepareStatement(inst1)) {
            // Setzt den Namen der Figur (? → character)
            stmt1.setString(1, character);
            // Setzt den Alias der Figur (? → alias)
            stmt1.setString(2, alias);
            // Setzt die Position der Figur (? → position)
            stmt1.setInt(3, position);
            // Setzt die ID des Films (? → movieID)
            stmt1.setLong(4, movieID);
            // Setzt die ID der Person (? → personID)
            stmt1.setLong(5, personID);

            // Führt das SQL-Insert-Statement aus, um die Figur in die Datenbank einzufügen
            stmt1.executeUpdate();

            // Abrufen der generierten ID nach dem Einfügen
            try (PreparedStatement stmt2 = conn.prepareStatement(inst2)) {
                try (ResultSet rs = stmt2.executeQuery()) {
                    rs.next();
                    // Speichert die generierte ID der Figur in der ID-Variable
                    id = rs.getLong("MovCharID");
                }
            }
        }
    }

    /**
     * Aktualisiert die vorhandenen Daten einer Figur in der Datenbank.
     * Die Figur muss bereits in die Datenbank eingefügt worden sein.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalStateException Falls die Figur noch keine ID besitzt.
     */
    public void update() throws SQLException {
        // Verhindert das Aktualisieren, wenn die Figur noch keine ID hat
        if (id == null) {
            throw new IllegalStateException("Objekt wurde noch nicht eingefügt");
        }

        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();

        // SQL-Statement zum Aktualisieren der Figurendaten basierend auf der ID
        String inst = "UPDATE MovieCharacter SET Character = ?, Alias = ?, Position = ?, MovieID = ?, PersonID = ? WHERE MovCharID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt den Namen der Figur (? → character)
            stmt.setString(1, character);
            // Setzt den Alias der Figur (? → alias)
            stmt.setString(2, alias);
            // Setzt die Position der Figur (? → position)
            stmt.setInt(3, position);
            // Setzt die ID des Films (? → movieID)
            stmt.setLong(4, movieID);
            // Setzt die ID der Person (? → personID)
            stmt.setLong(5, personID);
            // Setzt die ID der Figur zur Identifikation des Datensatzes (? → id)
            stmt.setLong(6, id);

            // Führt das SQL-Update-Statement aus, um die Figur zu aktualisieren
            stmt.executeUpdate();
        }
    }

    /**
     * Löscht eine Figur aus der Datenbank.
     * Die Figur muss bereits in die Datenbank eingefügt worden sein.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalStateException Falls die Figur noch keine ID besitzt.
     */
    public void delete() throws SQLException {
        // Verhindert das Löschen, wenn die Figur noch keine ID hat
        if (id == null) {
            throw new IllegalStateException("Objekt wurde noch nicht eingefügt");
        }

        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();

        // SQL-Statement zum Löschen der Figur basierend auf der ID
        String inst = "DELETE FROM MovieCharacter WHERE MovCharID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt die ID der Figur als Parameter für das Delete-Statement (? → id)
            stmt.setLong(1, id);

            // Führt das SQL-Delete-Statement aus, um die Figur zu löschen
            stmt.executeUpdate();
        }
    }

    /**
     * Konvertiert die aktuelle Figur in ein `CharacterDTO`-Objekt.
     * Das DTO enthält die relevanten Informationen zur Figur sowie den Namen des Darstellers.
     * @return Ein `CharacterDTO`-Objekt, das die Daten der Figur repräsentiert.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     */
    public CharacterDTO toDTO() throws SQLException {
        CharacterDTO characterDTO = new CharacterDTO();

        // Setzt den Namen und Alias der Figur im DTO
        characterDTO.setCharacter(this.character);
        characterDTO.setAlias(this.alias);

        // Setzt den Namen des Darstellers basierend auf der PersonID
        characterDTO.setPlayer(Person.findById(this.personID).getName());

        return characterDTO;
    }

    // Statische Methoden zur Abfrage von Charakterdaten aus der Datenbank

    /**
     * Gibt eine Liste aller Figuren aus der Datenbank zurück.
     * @return Eine Liste von `Character`-Objekten, die alle Figuren in der Datenbank repräsentieren.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     */
    public static List<Character> findAll() throws SQLException {
        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();

        List<Character> movieCharacterList = new ArrayList<>();

        // SQL-Statement, um alle Figuren abzurufen
        String inst = "SELECT MovCharID, Character, Alias, Position, MovieID, PersonID FROM MovieCharacter";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Führt das SQL-Select-Statement aus und holt alle Figuren aus der Tabelle
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Character character = new Character();
                    // Setzt die ID, Name, Alias, Position, MovieID und PersonID für jede gefundene Figur
                    character.id = rs.getLong("MovCharID");
                    character.setCharacter(rs.getString("Character"));
                    character.setAlias(rs.getString("Alias"));
                    character.setPosition(rs.getInt("Position"));
                    character.setMovieID(rs.getLong("MovieID"));
                    character.setPersonID(rs.getLong("PersonID"));

                    // Fügt die gefundene Figur zur movieCharacterList hinzu
                    movieCharacterList.add(character);
                }
            }
        }

        return movieCharacterList;
    }

    /**
     * Sucht eine Figur in der Datenbank basierend auf der ID.
     * @param id Die ID der Figur.
     * @return Das `Character`-Objekt mit den Daten der Figur.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalArgumentException Falls die Figur mit der gegebenen ID nicht existiert.
     */
    public static Character findById(long id) throws SQLException {
        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();

        Character movieCharacter = new Character();
        movieCharacter.id = id;

        // SQL-Statement zur Suche der Figur nach ID
        String inst = "SELECT Character, Alias, Position, MovieID, PersonID FROM MovieCharacter WHERE MovCharID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt die ID der Figur als Parameter für das Select-Statement (? → id)
            stmt.setLong(1, id);

            // Führt das SQL-Select-Statement aus, um die Figur anhand der ID zu suchen
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalArgumentException("Objekt mit ID " + id + " existiert nicht");
                }

                // Setzt Name, Alias, Position, MovieID und PersonID der gefundenen Figur
                movieCharacter.setCharacter(rs.getString("Character"));
                movieCharacter.setAlias(rs.getString("Alias"));
                movieCharacter.setPosition(rs.getInt("Position"));
                movieCharacter.setMovieID(rs.getLong("MovieID"));
                movieCharacter.setPersonID(rs.getLong("PersonID"));
            }
        }

        return movieCharacter;
    }

    /**
     * Sucht alle Figuren, die zu einem bestimmten Film gehören.
     * @param movieID Die ID des Films.
     * @return Eine Liste von `Character`-Objekten, die zu dem Film gehören.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     */
    public static List<Character> findByMovieID(long movieID) throws SQLException {
        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();

        List<Character> movieCharacterList = new ArrayList<>();

        // SQL-Statement zur Suche von Figuren nach der Film-ID
        String inst = "SELECT MovCharID, Character, Alias, Position, PersonID FROM MovieCharacter WHERE MovieID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt die Film-ID als Parameter für das Select-Statement (? → movieID)
            stmt.setLong(1, movieID);

            // Führt das SQL-Select-Statement aus und holt alle Figuren des angegebenen Films
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Character character = new Character();
                    // Setzt die ID, Name, Alias, Position und PersonID für jede gefundene Figur
                    character.id = rs.getLong("MovCharID");
                    character.setCharacter(rs.getString("Character"));
                    character.setAlias(rs.getString("Alias"));
                    character.setPosition(rs.getInt("Position"));
                    character.setMovieID(movieID);
                    character.setPersonID(rs.getLong("PersonID"));

                    // Fügt die gefundene Figur zur movieCharacterList hinzu
                    movieCharacterList.add(character);
                }
            }
        }

        return movieCharacterList;
    }

    /**
     * Sucht alle Figuren, die von einer bestimmten Person gespielt werden.
     * @param personID Die ID der Person (Darsteller).
     * @return Eine Liste von `Character`-Objekten, die von der Person gespielt werden.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     */
    public static List<Character> findByPersonID(long personID) throws SQLException {
        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();

        List<Character> movieCharacterList = new ArrayList<>();

        // SQL-Statement zur Suche von Figuren nach der Personen-ID
        String inst = "SELECT MovCharID, Character, Alias, Position, MovieID FROM MovieCharacter WHERE PersonID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt die Personen-ID als Parameter für das Select-Statement (? → personID)
            stmt.setLong(1, personID);

            // Führt das SQL-Select-Statement aus und holt alle Figuren, die von der angegebenen Person gespielt werden
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Character character = new Character();
                    // Setzt die ID, Name, Alias, Position und MovieID für jede gefundene Figur
                    character.id = rs.getLong("MovCharID");
                    character.setCharacter(rs.getString("Character"));
                    character.setAlias(rs.getString("Alias"));
                    character.setPosition(rs.getInt("Position"));
                    character.setMovieID(rs.getLong("MovieID"));
                    character.setPersonID(personID);

                    // Fügt die gefundene Figur zur movieCharacterList hinzu
                    movieCharacterList.add(character);
                }
            }
        }

        return movieCharacterList;
    }
}
