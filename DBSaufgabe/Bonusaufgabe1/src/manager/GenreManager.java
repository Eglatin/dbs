package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import activeRecord.Genre;
import aufgabe41.aufgabe1;

/**
 * Die Klasse `GenreManager` verwaltet die Datenbankzugriffe und -operationen 
 * für die Genre-Entitäten. Diese Klasse stellt Methoden zur Verfügung, um 
 * eine Liste aller Genres abzurufen, die alphabetisch sortiert ist.
 */
public class GenreManager {

    /**
     * Ruft eine vollständige Liste aller in der Datenbank gespeicherten Genres ab.
     * Die Genres werden alphabetisch sortiert zurückgegeben.
     * @return Eine alphabetisch sortierte `List` mit allen Genre-Namen als String.
     * @throws Exception Falls ein Fehler bei der Datenbankoperation auftritt.
     */
    public List<String> getGenres() throws Exception {
        List<String> genres;
        boolean ok = false; // Transaktionsstatus, um Commit oder Rollback zu steuern

        try {
            genres = new ArrayList<>(); // Initialisiert eine leere Liste für die Genres

            // Ruft alle Genre-Objekte aus der Datenbank ab und extrahiert die Genre-Namen
            genres = Genre.findAll().stream()
                          .map(Genre::getGenre)  // Wandelt jedes `Genre`-Objekt in seinen Namen um
                          .collect(Collectors.toList()); // Sammeln aller Namen in einer Liste

            // Sortiert die Liste der Genre-Namen alphabetisch
            genres.sort(String::compareTo);

            // Bestätigt die Transaktion durch Commit
            aufgabe1.getConnection().commit();
            ok = true; // Setzt den Transaktionsstatus auf erfolgreich

        } finally {
            // Falls ein Fehler aufgetreten ist, wird die Transaktion durch Rollback abgebrochen
            if (!ok) {
                aufgabe1.getConnection().rollback();
            }
        }

        return genres; // Gibt die sortierte Liste der Genre-Namen zurück
    }
}
