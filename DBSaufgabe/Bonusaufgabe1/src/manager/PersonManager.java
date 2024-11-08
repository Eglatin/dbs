package manager;

import activeRecord.Person;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager-Klasse für die Verwaltung von Personen.
 */
public class PersonManager {

    public List<String> getPersonList(String text) throws Exception {
        List<String> persons = new ArrayList<>();
        for (Person person : Person.findByName(text)) {
            persons.add(person.getName());
        }
        return persons;
    }
}
