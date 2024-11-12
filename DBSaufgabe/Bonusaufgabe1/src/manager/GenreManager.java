package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import activeRecord.Genre;
import aufgabe41.aufgabe1;

/**
 * klasse Genremanager verwaltet die Datenbankzugriffe und -operationen 
 * für die Genre-Entitäten. sie stellt Methodn zur Verfügung, um 
 * eine Liste aller Genres abzurufen, die alphabetisch sortiert ist
 */
public class GenreManager {

    /**
     * Ruft vollständige Liste aller in der Datenbank gespeicherten Genres ab
     *Genres werden alphabetisch sortiert zurückgegebe
     * @return alphabetisch sortierte List mit allen Genre-Namen als String
     * @throws Exception Falls Fehler bei der Datenbankoperation auftritt
     */
    public List<String> getGenres() throws Exception {
        List<String> genres;
        boolean ok = false; // Transaktionsstatus, um Commit oder Rollback zu steuern

        try {
            genres = new ArrayList<>(); // Initialisiert eine leere Liste für die Genres

            // Ruft alle Genre-Objekte aus der Datenbank ab und extrahiert die Genre-Namen
            genres = Genre.findAll().stream()
                          .map(Genre::getGenre)  // Wandelt jedes Genre-Objekt in seinen Namen um
                          .collect(Collectors.toList()); // Sammeln aller Namen in einer Liste

            // Sortiert Liste der Genre-Namen alphabetisch
            genres.sort(String::compareTo);

            // Bestätigt Transaktion durch Commit
            aufgabe1.getConnection().commit();
            ok = true; // Setzt Transaktionsstatus auf erfolgreich

        } finally {
            // Falls Fehler aufgetrten ist, wird dTransaktion durch Rollback abgebrochen
            if (!ok) {
                aufgabe1.getConnection().rollback();
            }
        }

        return genres; // Gibt sortierte Liste der Genre-Namen zurück
    }
}
