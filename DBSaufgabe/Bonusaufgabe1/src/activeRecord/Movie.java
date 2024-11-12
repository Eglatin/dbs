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

/**
 * Die Klasse `Movie` repräsentiert einen Film in der Datenbank.
 * Jeder Film hat eine eindeutige ID, einen Titel, ein Erscheinungsjahr und einen Typ.
 * Die Klasse bietet Methoden zum Einfügen, Aktualisieren, Löschen und
 * Abrufen von Filmen aus der Datenbank.
 */
public class Movie {

    // Eindeutige ID des Films, die beim Einfügen in die Datenbank gesetzt wird.
    private Long id;

    // Titel des Films, dieser Wert darf nicht null sein.
    private String title;
    
    // Erscheinungsjahr des Films, z.B. 2022.
    private int year;
    
    // Typ des Films, z.B. 'F' für Film, 'T' für TV-Serie, etc.
    private char type;
    
    // Getter- und Setter-Methoden für die Attribute der Klasse
    
    /**
     * Gibt die ID des Films zurück.
     * @return ID des Films als Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Gibt den Titel des Films zurück.
     * @return Titel des Films als String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setzt den Titel des Films.
     * @param title Der Titel des Films als String, darf nicht null sein.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gibt das Erscheinungsjahr des Films zurück.
     * @return Erscheinungsjahr des Films als int
     */
    public int getYear() {
        return year;
    }

    /**
     * Setzt das Erscheinungsjahr des Films.
     * @param year Das Erscheinungsjahr des Films als int
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Gibt den Typ des Films zurück.
     * @return Typ des Films als char
     */
    public char getType() {
        return type;
    }

    /**
     * Setzt den Typ des Films.
     * @param type Der Typ des Films als char (z.B. 'F' für Film, 'T' für TV-Serie)
     */
    public void setType(char type) {
        this.type = type;
    }
    
    // Methoden zur Datenbankmanipulation (Einfügen, Aktualisieren, Löschen)
    
    /**
     * Fügt einen neuen Film in die Datenbank ein.
     * Die ID des Films wird automatisch generiert und nach dem Einfügen gesetzt.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalStateException Falls der Film bereits eine ID besitzt (bereits eingefügt wurde).
     */
    public void insert() throws SQLException {
        // Verhindert das Einfügen, wenn der Film bereits eine ID hat
        if (id != null) {
            throw new IllegalStateException("Objekt wurde bereits eingefügt");
        }

        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();
        
        // SQL-Statement zum Einfügen eines neuen Films in die `Movie`-Tabelle
        String inst = "INSERT INTO Movie(title, year, type) VALUES(?, ?, ?) RETURNING movieid";

        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt den Titel für die erste Platzhalter-Variable (? → title)
            stmt.setString(1, title);
            // Setzt das Jahr für die zweite Platzhalter-Variable (? → year)
            stmt.setInt(2, year);
            // Setzt den Typ für die dritte Platzhalter-Variable (? → type)
            stmt.setObject(3, type, java.sql.Types.CHAR);

            // Führt das SQL-Insert-Statement aus und erhält das ResultSet mit der generierten ID
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Speichert die generierte movieid in der ID-Variable
                    id = rs.getLong(1);
                }
            }
        }
    }

    /**
     * Aktualisiert die vorhandenen Daten eines Films in der Datenbank.
     * Der Film muss bereits in die Datenbank eingefügt worden sein.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalStateException Falls der Film noch keine ID besitzt.
     */
    public void update() throws SQLException {
        // Verhindert das Aktualisieren, wenn der Film noch keine ID hat
        if (id == null) {
            throw new IllegalStateException("Objekt wurde noch nicht eingefügt");
        }
        
        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();
        
        // SQL-Statement zum Aktualisieren der Filmdaten basierend auf der ID
        String inst = "UPDATE Movie SET Title = ?, Year = ?, Type = ? WHERE MovieID = ?";
                
        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt den Titel für die erste Platzhalter-Variable (? → title)
            stmt.setString(1, title);
            // Setzt das Jahr für die zweite Platzhalter-Variable (? → year)
            stmt.setInt(2, year);
            // Setzt den Typ für die dritte Platzhalter-Variable (? → type)
            stmt.setString(3, String.valueOf(type));
            // Setzt die ID für die vierte Platzhalter-Variable (? → id)
            stmt.setLong(4, id);
            
            // Führt das SQL-Update-Statement aus, um die Filmdaten zu aktualisieren
            stmt.executeUpdate();
        }
    }
    
    /**
     * Löscht einen Film aus der Datenbank.
     * Der Film muss bereits in die Datenbank eingefügt worden sein.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalStateException Falls der Film noch keine ID besitzt.
     */
    public void delete() throws SQLException {
        // Verhindert das Löschen, wenn der Film noch keine ID hat
        if (id == null) {
            throw new IllegalStateException("Objekt wurde noch nicht eingefügt");
        }
        
        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();
        
        // SQL-Statement zum Löschen des Films basierend auf der ID
        String inst = "DELETE FROM Movie WHERE MovieID = ?";
                
        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt die ID des Films als Parameter für das Delete-Statement (? → id)
            stmt.setLong(1, id);
            
            // Führt das SQL-Delete-Statement aus, um den Film zu löschen
            stmt.executeUpdate();
        }
    }
    
    /**
     * Konvertiert den aktuellen Film in ein `MovieDTO`-Objekt.
     * Das DTO enthält alle relevanten Daten des Films sowie die zugeordneten Genres und Charaktere.
     * @return Ein `MovieDTO`-Objekt, das die Daten des Films repräsentiert.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     */
    public MovieDTO toDTO() throws SQLException {
        MovieDTO movieDTO = new MovieDTO();

        // Setzt ID, Titel, Jahr und Typ im DTO
        movieDTO.setId(this.id);
        movieDTO.setTitle(this.title);
        movieDTO.setYear(this.year);
        movieDTO.setType(String.valueOf(this.type));

        // Initialisiert Genres und Charaktere im DTO
        movieDTO.setGenres(new HashSet<>());
        movieDTO.setCharacters(new ArrayList<>());

        // Ruft die zugehörigen Genres und fügt sie dem DTO hinzu
        for (Movie_Genre movieGenre : Movie_Genre.findByMovieID(this.id)) {
            movieDTO.addGenre(Genre.findById(movieGenre.getGenreID()).getGenre()); 
        }

        // Ruft die zugehörigen Charaktere und fügt sie dem DTO hinzu
        for (Character character : Character.findByMovieID(this.id)) {
            movieDTO.addCharacter(character.toDTO());       
        }

        return movieDTO;
    }

    // Statische Methoden zur Abfrage von Filmdaten aus der Datenbank
    
    /**
     * Gibt eine Liste aller Filme aus der Datenbank zurück.
     * @return Eine Liste von `Movie`-Objekten, die alle Filme in der Datenbank repräsentieren.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     */
    public static List<Movie> findAll() throws SQLException {
        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();

        List<Movie> movieList = new ArrayList<>();
        
        // SQL-Statement, um alle Filme abzurufen
        String inst = "SELECT MovieID, Title, Year, Type FROM Movie";
        
        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Führt das SQL-Select-Statement aus und holt alle Filme aus der Tabelle
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Movie movie = new Movie();
                    // Setzt die ID, Titel, Jahr und Typ für jeden gefundenen Film
                    movie.id = rs.getLong("MovieID");
                    movie.setTitle(rs.getString("Title"));
                    movie.setYear(rs.getInt("Year"));
                    movie.setType(rs.getString("Type").charAt(0));
                    
                    // Fügt den gefundenen Film zur movieList hinzu
                    movieList.add(movie);
                }
            }
        }
        
        return movieList;
    }
    
    /**
     * Sucht einen Film in der Datenbank basierend auf der ID.
     * @param id Die ID des Films.
     * @return Das `Movie`-Objekt mit den Daten des Films.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     * @throws IllegalArgumentException Falls der Film mit der gegebenen ID nicht existiert.
     */
    public static Movie findById(long id) throws SQLException {
        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();

        Movie movie = new Movie();
        movie.id = id;
        
        // SQL-Statement zur Suche des Films nach ID
        String inst = "SELECT Title, Year, Type FROM Movie WHERE MovieID = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt die ID des Films als Parameter für das Select-Statement (? → id)
            stmt.setLong(1, id);
            
            // Führt das SQL-Select-Statement aus, um den Film anhand der ID zu suchen
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalArgumentException("Objekt mit ID " + id + " existiert nicht");
                }
                
                // Setzt Titel, Jahr und Typ des gefundenen Films
                movie.setTitle(rs.getString("Title"));
                movie.setYear(rs.getInt("Year"));
                movie.setType(rs.getString("Type").charAt(0));
            }
        }
        
        return movie;
    }
     
    /**
     * Sucht alle Filme, deren Titel den Suchstring enthält.
     * @param title Der Titel oder Teil des Titels der gesuchten Filme.
     * @return Eine Liste von `Movie`-Objekten, deren Titel den Suchstring enthält.
     * @throws SQLException Falls ein Fehler bei der Datenbankoperation auftritt.
     */
    public static List<Movie> findByTitle(String title) throws SQLException {
        // Verbindung zur Datenbank wird hergestellt
        Connection conn = aufgabe1.getConnection();

        List<Movie> movies = new ArrayList<>();
                
        // SQL-Statement zur Suche von Filmen nach Titel (case-insensitive)
        String inst = "SELECT MovieID, Title, Year, Type FROM Movie WHERE LOWER(Title) LIKE ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(inst)) {
            // Setzt den Titel als Parameter für das LIKE-Statement, mit % für die Wildcard-Suche (? → title)
            stmt.setString(1, "%" + title.toLowerCase() + "%");
            
            // Führt das SQL-Select-Statement aus und holt alle Filme, deren Titel den Suchstring enthält
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Movie movie = new Movie();
                    // Setzt die ID, Titel, Jahr und Typ für jeden gefundenen Film
                    movie.id = rs.getLong("MovieID");
                    movie.setTitle(rs.getString("Title"));
                    movie.setYear(rs.getInt("Year"));
                    movie.setType(rs.getString("Type").charAt(0));
                    
                    // Fügt den gefundenen Film zur movies-Liste hinzu
                    movies.add(movie);
                }
            }
        }
        
        return movies;
    }
}
