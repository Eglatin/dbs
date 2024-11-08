package dtos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Data Transfer Object (DTO) für Objekte der Klasse Movie.
 * Enthält alle erforderlichen Informationen für die Kommunikation GUI <-> Geschäftslogik,
 * einschließlich aller zugeordneten Genres und Charaktere.
 * Falls es sich um einen neuen Film handelt, ist die ID null.
 */
public class MovieDTO {

    private Long id = null;
    private String title = "";
    private String type = "C";
    private int year = 0;
    private Set<String> genres = new HashSet<>();
    private List<CharacterDTO> characters = new ArrayList<>();

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

    public void addGenre(String genre) {
        genres.add(genre);
    }

    public void addCharacter(CharacterDTO characterDTO) {
        characters.add(characterDTO);
    }

    @Override
    public String toString() {
        return "MovieDTO [id=" + id + ", title=" + title + ", type=" + type + ", year=" + year 
                + ", genres=" + genres + ", characters=" + characters + "]";
    }
}
