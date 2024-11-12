package manager;

import java.util.List;
import java.util.stream.Collectors;

import activeRecord.Person;
import aufgabe41.aufgabe1;

/**
 *PrsonManager verwaltet datenbankzugriffe und -operationen 
 * für Person-Entitäten. Klasse stellt Methoden zur Verfügung, um Personen zu suchen 
 *und den Namen oder die ID einer Person abzurufen
 */
public class PersonManager {

    /**
     * Sucht alle Personen in der Datenbank, deren Name den Suchstring enthält, 
     * und gibt diese als alphabetisch sortierte Liste zurück
     * @param text Suchstring für den Namen der Personen
     * @return Eine alphabetisch sortierte Liste von Namen, die den Suchstring enthalten 
     * @throws Exception bei einem Datenbankfehler
     */
    public List<String> getPersonList(String text) throws Exception {
        List<String> personNames;
        boolean ok = false; // Transaktionsstatus, um Commit oder Rollback zu steuern

        try {
            //sucht nach Personen, deren Name den Suchstring enthält und sammelt die Namen in einer Liste
            personNames = Person.findByName(text).stream()
                                 .map(Person::getName) // Extrahiert den Namen jeder gefundenen Person
                                 .sorted(String::compareToIgnoreCase) // Sortiert die Namen alphabetisch
                                 .collect(Collectors.toList());

            //bestätigt die Transaktion durch Commit
            aufgabe1.getConnection().commit();
            ok = true; // Setzt den Transaktionsstatus auf erfolgreich

        } finally {
            //falls ein Fehler aufgetreten ist, wird die Transaktion durch Rollback abgebrochen
            if (!ok) {
                aufgabe1.getConnection().rollback();
            }
        }

        return personNames; // Gibt liste der gefundenen Namen zurück
    }

    /**
     *holt Namen einer Person basierend auf ihrer ID
     * @param personId Die id der Person
     * @return Der Name der Person als String
     * @throws Exception Bei einem Datenbankfehler oder wenn die Person nicht gefunden wird
     */
    public static String getPersonNameById(Long personId) throws Exception {
        // Sucht die Person anhand der ID in der Datenbank
        Person person = Person.findById(personId);
        if (person == null) {
            // Wenn keine Person mit der angegebenen ID gefunden wurde, wird eine Ausnahme ausgelöst
            throw new IllegalArgumentException("Person nicht gefunden für ID: " + personId);
        }
        return person.getName(); // Gibt den Namen der gefundenen Person zurück
    }

    /**
     * holt Id einer Person basierend auf ihrem Namen
     * @param name der Name der Person
     * @return Die ID der Person als Long, oder null, wenn sie nicht gefunden wird
     * @throws Exception Bei einem Datenbankfehler oder wenn die Person nicht gefunden wird
     */
    public static Long getPersonIdByName(String name) throws Exception {
        // Sucht nach Personen, die den gegebenen Namen haben
        List<Person> persons = Person.findByName(name);
        if (persons.isEmpty()) {
            // Wenn keine Person mit dem angegebenen Namen gefunden wurde, wird eine Ausnahme ausgelöst
            throw new IllegalArgumentException("Person nicht gefunden für Namen: " + name);
        }
        return persons.get(0).getId(); // Gibt ID ersten gefundenen Person zurück
    }
}
