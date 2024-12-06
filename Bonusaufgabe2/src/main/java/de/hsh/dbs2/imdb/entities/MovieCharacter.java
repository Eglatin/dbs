package de.hsh.dbs2.imdb.entities;

import jakarta.persistence.*; // Importiert JPA-Annotationen für die Entitätsdefinition.

@Entity // Kennzeichnet diese Klasse als JPA-Entität, die mit einer Datenbanktabelle verknüpft ist.
@Table(name = "MovieCharacter") 
// Legt den Tabellennamen in der Datenbank explizit fest. Hier wird "MovieCharacter" verwendet.
public class MovieCharacter {

    @Id // Kennzeichnet das Feld `id` als Primärschlüssel der Tabelle.
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // Gibt an, dass die ID automatisch von der Datenbank generiert wird, z. B. durch AUTO_INCREMENT in MySQL.
    private Long id;

    @Column(nullable = false) 
    // Definiert, dass das Feld `name` in der Datenbank nicht null sein darf.
    private String name;

    @ManyToOne // Definiert eine viele-zu-eins-Beziehung zwischen `MovieCharacter` und `Person`.
    @JoinColumn(name = "person_id") 
    // Gibt an, dass die Spalte `person_id` in der Tabelle "MovieCharacter" ein Fremdschlüssel ist, der auf `Person` verweist.
    private Person actor;

    @ManyToOne // Definiert eine viele-zu-eins-Beziehung zwischen `MovieCharacter` und `Movie`.
    @JoinColumn(name = "movie_id") 
    // Gibt an, dass die Spalte `movie_id` in der Tabelle "MovieCharacter" ein Fremdschlüssel ist, der auf `Movie` verweist.
    private Movie movie;

    // =====================
    // Getter und Setter
    // =====================
    
    // Gibt die ID des MovieCharacters zurück.
    public Long getId() {
        return id;
    }

    // Setzt die ID des MovieCharacters. In der Regel nicht manuell genutzt, da die ID automatisch generiert wird.
    public void setId(Long id) {
        this.id = id;
    }

    // Gibt den Namen des MovieCharacters zurück.
    public String getName() {
        return name;
    }

    // Setzt den Namen des MovieCharacters.
    public void setName(String name) {
        this.name = name;
    }

    // Gibt die verknüpfte `Person` (den Schauspieler) zurück.
    public Person getActor() {
        return actor;
    }

    // Verknüpft eine `Person` (den Schauspieler) mit diesem MovieCharacter.
    public void setActor(Person actor) {
        this.actor = actor;
    }

    // Gibt den verknüpften `Movie` zurück.
    public Movie getMovie() {
        return movie;
    }

    // Verknüpft einen `Movie` mit diesem MovieCharacter.
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    // =====================
    // `toString`-Methode
    // =====================
    @Override
    public String toString() {
        // Gibt eine lesbare Darstellung des MovieCharacters zurück, die für Debugging und Ausgabe genutzt wird.
        return "MovieCharacter{" +
                "id=" + id + // Primärschlüssel
                ", name='" + name + '\'' + // Name des Charakters
                ", actor=" + (actor != null ? actor.getName() : "null") + // Name des Schauspielers (falls vorhanden)
                ", movie=" + (movie != null ? movie.getTitle() : "null") + // Titel des Films (falls vorhanden)
                '}';
    }
}
