package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import activeRecord.Genre;
import aufgabe41.aufgabe1;

public class GenreManager {

    /**
     
Ermittelt eine vollstaendige Liste aller in der Datenbank abgelegten Genres
Die Genres werden alphabetisch sortiert zurueckgeliefert.
@return Alle Genre-Namen als String-Liste
@throws Exception*/
public List<String> getGenres() throws Exception {
    List<String> genres;
    boolean ok = false;
    try {
        genres = new ArrayList<>();
        genres = Genre.findAll().stream().map(e-> e.getGenre()).collect(Collectors.toList());
        genres.sort(String::compareTo);
        aufgabe1.getConnection().commit();
        ok = true;
    } finally {
        if (!ok) {
            aufgabe1.getConnection().rollback();}}
    return genres;}

}