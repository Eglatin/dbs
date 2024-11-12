package dtos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Die Klasse `MovieDTO` dient als Data Transfer Object (DTO) für Filme.
 * Dieses Objekt wird verwendet, um alle notwendigen Filminformationen zwischen GUI und Geschäftslogik zu übertragen,
 * einschließlich aller zugeordneten Genres und Charaktere.
 * Die ID ist `null`, falls es sich um einen neuen Film handelt.
 */
public class MovieDTO {

    // Eindeutige ID des Films. Ist `null` bei neuen Filmen, die noch nicht in der Datenbank gespeichert sind.
    private Long id = null;
    
    // Titel des Films, z.B. "Star Wars"
    private String title = "";
    
    // Typ des Films, z.B. 'F' für Film, 'T' für TV-Serie
    private String type = "C";
    
    // Erscheinungsjahr des Films, z.B. 1977 für "Star Wars"
    private int year = 0;
    
    // Set zur Speicherung der Genres des Films, um doppelte Werte zu vermeiden
    private Set<String> genres = new HashSet<>();
    
    // Liste zur geordneten Speicherung der Charaktere des Films
    private List<CharacterDTO> characters = new ArrayList<>();

    // Getter- und Setter-Methoden für die Attribute der Klasse

    /**
     * Gibt die ID des Films zurück.
     * @return ID des Films als Long oder `null`, wenn es ein neuer Film ist
     */
    public Long getId() {
        return id;
    }

    /**
     * Setzt die ID des Films.
     * @param id Die eindeutige ID des Films als Long.
     */
    public void setId(Long id) {
        this.id = id;
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
     * Gibt den Typ des Films zurück.
     * @return Typ des Films als String, z.B. "F" für Film, "T" für TV-Serie
     */
    public String getType() {
        return type;
    }

    /**
     * Setzt den Typ des Films.
     * @param type Der Typ des Films als String, z.B. "F" für Film oder "T" für TV-Serie.
     */
    public void setType(String type) {
        this.type = type;
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
     * @param year Das Erscheinungsjahr des Films als int.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Gibt die Genres des Films als `Set` zurück.
     * @return Ein `Set` mit den Namen der Genres des Films
     */
    public Set<String> getGenres() {
        return genres;
    }

    /**
     * Setzt die Genres des Films.
     * @param genres Ein `Set` mit den Namen der Genres des Films
     */
    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    /**
     * Gibt die Charaktere des Films als `List` zurück.
     * @return Eine `List` mit `CharacterDTO`-Objekten, die die Charaktere des Films repräsentieren
     */
    public List<CharacterDTO> getCharacters() {
        return characters;
    }

    /**
     * Setzt die Charaktere des Films.
     * @param characters Eine `List` mit `CharacterDTO`-Objekten, die die Charaktere des Films repräsentieren
     */
    public void setCharacters(List<CharacterDTO> characters) {
        this.characters = characters;
    }

    /**
     * Fügt ein Genre zum Set der Genres hinzu, falls es noch nicht vorhanden ist.
     * @param genre Der Name des Genres als String.
     */
    public void addGenre(String genre) {
        genres.add(genre); // Fügt ein Genre hinzu, vermeidet Duplikate durch Verwendung des Sets
    }

    /**
     * Fügt einen Charakter zur Liste der Charaktere hinzu.
     * @param characterDTO Das `CharacterDTO`-Objekt, das den Charakter repräsentiert
     */
    public void addCharacter(CharacterDTO characterDTO) {
        characters.add(characterDTO); // Fügt einen Charakter zur geordneten Liste hinzu
    }

    /**
     * Gibt eine textuelle Darstellung des Films zurück.
     * Diese Methode wird typischerweise verwendet, um die Informationen des Films 
     * in einer benutzerfreundlichen Form anzuzeigen.
     * @return Eine Zeichenkette, die die ID, den Titel, den Typ, das Jahr, Genres und Charaktere des Films enthält.
     */
    @Override
    public String toString() {
        return "MovieDTO [id=" + id + ", title=" + title + ", type=" + type 
               + ", year=" + year + ", genres=" + genres + ", characters=" + characters + "]";
    }
}
