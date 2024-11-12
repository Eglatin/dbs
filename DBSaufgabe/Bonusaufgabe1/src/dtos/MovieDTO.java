package dtos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Data Transfer Object (DTO) für die Klasse Movie.
 * Dieses Objekt wird verwendet, um alle notwendigen Film-Informationen zwischen GUI und Geschäftslogik zu übertragen,
 * einschließlich aller zugeordneten Genres und Charaktere.
 * Die ID ist null, falls es sich um einen neuen Film handelt.
 */
public class MovieDTO {

    private Long id = null; // ID des Films (null für neue Filme)
    private String title = ""; // Titel des Films
    private String type = "C"; // Standardtyp des Films (z.B. "C" für Standardinitialisierung)
    private int year = 0; // Erscheinungsjahr des Films
    private Set<String> genres = new HashSet<>(); // Set zur Speicherung der Genres, um doppelte Werte zu vermeiden
    private List<CharacterDTO> characters = new ArrayList<>(); // Liste zur geordneten Speicherung der Charaktere

    // Getter und Setter für alle Felder
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    public List<CharacterDTO> getCharacters() {
        return characters;
    }

    public void setCharacters(List<CharacterDTO> characters) {
        this.characters = characters;
    }

    // Methode zum Hinzufügen eines Genres, falls es noch nicht vorhanden ist
    public void addGenre(String genre) {
        genres.add(genre); // Fügt ein Genre hinzu, vermeidet Duplikate durch Verwendung des Sets
    }

    // Methode zum Hinzufügen eines Charakters zur Liste
    public void addCharacter(CharacterDTO characterDTO) {
        characters.add(characterDTO); // Fügt einen Charakter zur geordneten Liste hinzu
    }

    // Überschreibt die toString-Methode, um die Film-Informationen als Zeichenkette darzustellen
    @Override
    public String toString() {
        return "MovieDTO [id=" + id + ", title=" + title + ", type=" + type 
               + ", year=" + year + ", genres=" + genres + ", characters=" + characters + "]";
    }
}




