package manager;

import activeRecord.Genre;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager-Klasse für die Verwaltung von Genres.
 */
public class GenreManager {

    /**
     * Gibt eine alphabetisch sortierte Liste aller Genre-Namen aus der Datenbank zurück.
     * @return Liste der Genre-Namen
     * @throws Exception bei Datenbankfehlern
     */
    public List<String> getGenres() throws Exception {
        List<String> genres = new ArrayList<>();
        for (Genre genre : Genre.findAll()) {
            genres.add(genre.getName());
        }
        genres.sort(String::compareTo);
        return genres;
    }
}
