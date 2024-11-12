package manager;

import java.util.List;
import java.util.stream.Collectors;
import activeRecord.Person;
import aufgabe41.aufgabe1;

public class PersonManager {

    /**
     * Sucht alle Personen in der Datenbank, deren Name den Suchstring enthält, und gibt diese als sortierte Liste zurück.
     * @param text Suchstring für den Namen der Personen.
     * @return Liste von Namen, die den Suchstring enthalten.
     * @throws Exception bei einem Datenbankfehler.
     */
    public List<String> getPersonList(String text) throws Exception {
        List<String> personNames;
        boolean ok = false; // Transaktionsstatus

        try {
            // Sucht nach Personen, deren Name den Suchstring enthält
            personNames = Person.findByName(text).stream()
                                 .map(Person::getName) // Holt den Namen jeder gefundenen Person
                                 .sorted(String::compareToIgnoreCase) // Sortiert die Namen alphabetisch
                                 .collect(Collectors.toList());

            aufgabe1.getConnection().commit(); // Bestätigt die Transaktion
            ok = true; // Erfolgreiche Transaktion

        } finally {
            if (!ok) { // Rollback bei Misserfolg
                aufgabe1.getConnection().rollback();
            }
        }

        return personNames; // Gibt die Liste der gefundenen Namen zurück
    }

    /**
     * Holt den Namen einer Person basierend auf ihrer ID.
     * @param personId Die ID der Person.
     * @return Der Name der Person.
     * @throws Exception bei einem Datenbankfehler oder wenn die Person nicht gefunden wird.
     */
    public static String getPersonNameById(Long personId) throws Exception {
        // Sucht die Person anhand der ID
        Person person = Person.findById(personId);
        if (person == null) {
            throw new IllegalArgumentException("Person not found for ID: " + personId);
        }
        return person.getName(); // Gibt den Namen der gefundenen Person zurück
    }

    /**
     * Holt die ID einer Person basierend auf ihrem Namen.
     * @param name Der Name der Person.
     * @return Die ID der Person, oder null, wenn sie nicht gefunden wird.
     * @throws Exception bei einem Datenbankfehler oder wenn die Person nicht gefunden wird.
     */
    public static Long getPersonIdByName(String name) throws Exception {
        // Sucht nach Personen, die den gegebenen Namen haben
        List<Person> persons = Person.findByName(name);
        if (persons.isEmpty()) { // Fehlermeldung, wenn keine Person gefunden wird
            throw new IllegalArgumentException("Person not found for name: " + name);
        }
        return persons.get(0).getId(); // Gibt die ID der ersten gefundenen Person zurück
    }
}

