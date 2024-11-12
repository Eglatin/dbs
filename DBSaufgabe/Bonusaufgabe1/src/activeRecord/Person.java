package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import aufgabe41.aufgabe1;

/**
 * Die Klasse `Person` repräsentiert eine Person in der Datenbank.
 * Jede Person hat eine eindeutige ID, einen Namen und ein Geschlecht.
 * Die Klasse bietet Methoden zum Einfügen, Aktualisieren, Löschen und
 * Abrufen von Personen aus der Datenbank.
 */
public class Person {

    // Eindeutige ID der Person, die beim Einfügen in die Datenbank gesetzt wird.
    private Long id;

    // Name der Person, dieser Wert darf nicht null sein.
    private String name;
    
    // Geschlecht der Person, gespeichert als ein einzelnes Zeichen ('M' für männlich, 'F' für weiblich, etc.)
    private char sex;
        
    // Getter und Setter-Methoden für die Attribute der Klasse

    /**
     * Gibt die ID der Person zurück.
     * @return ID der Person als Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Gibt den Namen der Person zurück.
     * @return Name der Person als String
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen der Person.
     * @param name Der Name der Person als String, darf nicht null sein.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt das Geschlecht der Person zurück.
     * @return Geschlecht der Person als char
     */
    public char getSex() {
        return sex;
    }

    /**
     * Setzt das Geschlecht der Person.
     * @param sex Das Geschlecht der Person als char ('M' oder 'F')
     */
    public void setSex(char sex) {
        this.sex = sex;
    }

    // Methoden zur Datenbankmanipulation (Einfügen, Aktualisieren, Löschen)
    
    /**
     * Fügt eine neue Person in die Datenbank ein.
     * Die ID der Person wird automatisch generiert und nach dem Einfügen gesetzt.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalStateException Falls die Person bereits eine ID besitzt (bereits eingefügt wurde).
     */
    public void insert() throws SQLException {
        // Verhindert das Einfügen, wenn die Person bereits eine ID hat
        if (id != null) {
            throw new IllegalStateException("Objekt wurde bereits eingefügt");
        }
        
        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();
        
        // SQL-Statement zum Einfügen einer neuen Person in die `Person`-Tabelle
        String inst1 = "INSERT INTO Person(Name, Sex) VALUES(?, ?)";
        
        // SQL-Statement zum Abrufen der neu generierten ID nach dem Einfügen
        String inst2 = "SELECT last_insert_rowid() AS PersonID";
                
        try (PreparedStatement stmt1 = conn.prepareStatement(inst1)) {
            // Setzt den Namen für die erste Platzhalter-Variable (? → name)
            stmt1.setString(1, name);
            // Setzt das Geschlecht für die zweite Platzhalter-Variable (? → sex)
            stmt1.setString(2, String.valueOf(sex));
            
            // Führt das SQL-Insert-Statement aus, um die Person in die Datenbank einzufügen
            stmt1.executeUpdate();
            
            // Abrufen der generierten ID nach dem Einfügen
            try (PreparedStatement stmt2 = conn.prepareStatement(inst2)){
                try (ResultSet rs = stmt2.executeQuery()) {
                    rs.next();
                    // Setzt die generierte ID der Person, basierend auf dem Ergebnis des Select-Statements
                    id = rs.getLong("PersonID");
                }
            }
        }
    }
    
    /**
     * Aktualisiert die vorhandenen Daten einer Person in der Datenbank.
     * Die Person muss bereits in die Datenbank eingefügt worden sein.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalStateException Falls die Person noch keine ID besitzt.
     */
    public void update() throws SQLException {
        // Verhindert das Aktualisieren, wenn die Person noch keine ID hat
        if (id == null) {
            throw new IllegalStateException("Objekt wurde noch nicht eingefügt");
        }
        
        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();
        
        // SQL-Statement zum Aktualisieren der Personendaten basierend auf der ID
        String inst = "UPDATE Person SET Name = ?, Sex = ? WHERE PersonID = ?";
                
        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt den Namen für die erste Platzhalter-Variable (? → name)
            stmt.setString(1, name);
            // Setzt das Geschlecht für die zweite Platzhalter-Variable (? → sex)
            stmt.setString(2, String.valueOf(sex));
            // Setzt die ID für die dritte Platzhalter-Variable, um die entsprechende Zeile zu identifizieren (? → id)
            stmt.setLong(3, id);
            
            // Führt das SQL-Update-Statement aus, um die Personendaten zu aktualisieren
            stmt.executeUpdate();
        }
    }
    
    /**
     * Löscht eine Person aus der Datenbank.
     * Die Person muss bereits in die Datenbank eingefügt worden sein.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalStateException Falls die Person noch keine ID besitzt.
     */
    public void delete() throws SQLException {
        // Verhindert das Löschen, wenn die Person noch keine ID hat
        if (id == null) {
            throw new IllegalStateException("Objekt wurde noch nicht eingefügt");
        }
        
        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();
        
        // SQL-Statement zum Löschen der Person basierend auf der ID
        String inst = "DELETE FROM Person WHERE PersonID = ?";
                
        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt die ID der Person als Parameter für das Delete-Statement (? → id)
            stmt.setLong(1, id);
            
            // Führt das SQL-Delete-Statement aus, um die Person zu löschen
            stmt.executeUpdate();
        }
    }
    
    // Statische Methoden zur Abfrage von Personendaten aus der Datenbank

    /**
     * Gibt eine Liste aller Personen aus der Datenbank zurück.
     * @return Eine Liste von `Person`-Objekten, die alle Personen in der Datenbank repräsentieren.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     */
    public static List<Person> findAll() throws SQLException {
        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();

        List<Person> personList = new ArrayList<>();
        
        // SQL-Statement, um alle Personen abzurufen
        String inst = "SELECT PersonID, Name, Sex FROM Person";
        
        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Führt das SQL-Select-Statement aus und holt alle Personen aus der Tabelle
            try (ResultSet rs = stmt.executeQuery()) {
                // Durchläuft das ResultSet und fügt jede Person zur Liste hinzu
                while (rs.next()) {
                    Person person = new Person();
                    // Setzt die ID, Name und Geschlecht für jede gefundene Person
                    person.id = rs.getLong("PersonID");
                    person.setName(rs.getString("Name"));
                    person.setSex(rs.getString("Sex").charAt(0));
                    
                    // Fügt die gefundene Person zur personList hinzu
                    personList.add(person);
                }
            }
        }
        
        return personList;
    }
    
    /**
     * Sucht eine Person in der Datenbank basierend auf der ID.
     * @param id Die ID der Person.
     * @return Das `Person`-Objekt mit den Daten der Person.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalArgumentException Falls die Person mit der gegebenen ID nicht existiert.
     */
    public static Person findById(long id) throws SQLException {
        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();

        Person person = new Person();
        person.id = id;
        
        // SQL-Statement zur Suche der Person nach ID
        String inst = "SELECT Name, Sex FROM Person WHERE PersonID = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt die ID der Person als Parameter für das Select-Statement (? → id)
            stmt.setLong(1, id);
            
            // Führt das SQL-Select-Statement aus, um die Person anhand der ID zu suchen
            try (ResultSet rs = stmt.executeQuery()) {
                // Prüft, ob das ResultSet ein Ergebnis enthält (Person mit der ID existiert)
                if (!rs.next()) {
                    throw new IllegalArgumentException("Objekt mit ID " + id + " existiert nicht");
                }
                
                // Setzt Name und Geschlecht der gefundenen Person
                person.setName(rs.getString("Name"));
                person.setSex(rs.getString("Sex").charAt(0));
            }
        }
        
        return person;
    }
     
    /**
     * Sucht alle Personen, deren Name den Suchstring enthält.
     * @param name Der Name oder Teil des Namens der gesuchten Personen.
     * @return Eine Liste von `Person`-Objekten, deren Name den Suchstring enthält.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     */
    public static List<Person> findByName(String name) throws SQLException {
        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();

        List<Person> people = new ArrayList<>();
                
        // SQL-Statement zur Suche von Personen nach Namen (case-insensitive)
        String inst = "SELECT PersonID, Name, Sex FROM Person WHERE LOWER(Name) LIKE ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt den Namen als Parameter für das LIKE-Statement, mit % für die Wildcard-Suche (? → name)
            stmt.setString(1, "%" + name.toLowerCase() + "%");
            
            // Führt das SQL-Select-Statement aus und holt alle Personen, deren Name den Suchstring enthält
            try (ResultSet rs = stmt.executeQuery()) {
                // Durchläuft das ResultSet und fügt jede gefundene Person zur Liste hinzu
                while (rs.next()) {
                    Person person = new Person();
                    // Setzt die ID, Name und Geschlecht für jede gefundene Person
                    person.id = rs.getLong("PersonID");
                    person.setName(rs.getString("Name"));
                    person.setSex(rs.getString("Sex").charAt(0));
                    
                    // Fügt die gefundene Person zur people-Liste hinzu
                    people.add(person);
                }
            }
        }
        
        return people;
    }
}
