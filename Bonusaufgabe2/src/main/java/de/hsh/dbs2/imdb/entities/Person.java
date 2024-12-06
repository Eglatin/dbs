package de.hsh.dbs2.imdb.entities;

import jakarta.persistence.*; // Importiert JPA-Annotationen für die Entitätsdefinition.
import java.util.List; // Repräsentiert eine Sammlung von Filmcharakteren, die von dieser Person gespielt werden.

@Entity // Kennzeichnet diese Klasse als JPA-Entität, die mit einer Datenbanktabelle verknüpft ist.
@Table(name = "Person") 
// Legt den Tabellennamen in der Datenbank explizit auf "Person" fest.
public class Person {

    @Id // Kennzeichnet das Feld `id` als Primärschlüssel der Tabelle.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Gibt an, dass die ID automatisch von der Datenbank generiert wird, z. B. durch AUTO_INCREMENT.
    private Long id;

    @Column(nullable = false)
    // Definiert, dass das Feld `name` in der Datenbank nicht null sein darf.
    private String name;

    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL)
    // Definiert eine eins-zu-viele-Beziehung zwischen `Person` und `MovieCharacter`.
    // `mappedBy = "actor"` gibt an, dass das Feld `actor` in `MovieCharacter` die Beziehung verwaltet.
    // `cascade = CascadeType.ALL` stellt sicher, dass Operationen (z. B. Speichern, Löschen) auf `Person` auch auf die verknüpften `MovieCharacter`-Objekte angewendet werden.
    private List<MovieCharacter> characters;

    // =====================
    // Getter und Setter
    // =====================

    // Gibt die ID der Person zurück.
    public Long getId() {
        return id;
    }

    // Setzt die ID der Person. In der Regel wird dies nicht manuell genutzt, da die ID automatisch generiert wird.
    public void setId(Long id) {
        this.id = id;
    }

    // Gibt den Namen der Person zurück.
    public String getName() {
        return name;
    }

    // Setzt den Namen der Person.
    public void setName(String name) {
        this.name = name;
    }

    // Gibt die Liste der Filmcharaktere zurück, die von dieser Person gespielt werden.
    public List<MovieCharacter> getCharacters() {
        return characters;
    }

    // Setzt die Liste der Filmcharaktere.
    public void setCharacters(List<MovieCharacter> characters) {
        this.characters = characters;
    }

    // =====================
    // `toString`-Methode
    // =====================
    @Override
    public String toString() {
        // Gibt eine lesbare Darstellung der Person zurück, die für Debugging und Ausgabe genutzt wird.
        // Beinhaltet nur `id` und `name`, um unnötige Komplexität oder rekursive Ausgaben zu vermeiden.
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
