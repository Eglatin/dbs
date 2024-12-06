package de.hsh.dbs2.imdb.entities;

import jakarta.persistence.*; // Importiert JPA-Annotationen für die Entitätsdefinition.
import java.util.HashSet; // Wird für die Initialisierung der Set-Datenstruktur verwendet.
import java.util.Set; // Repräsentiert eine Sammlung von eindeutigen Elementen.

@Entity // Kennzeichnet diese Klasse als JPA-Entität, die mit einer Datenbanktabelle verbunden ist.
public class Genre {
    
    @Id // Kennzeichnet das Feld `id` als Primärschlüssel der Tabelle.
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // Gibt an, dass der Primärschlüssel automatisch von der Datenbank generiert wird, z. B. bei MySQL als AUTO_INCREMENT.
    private int id;

    private String name; 
    // Ein Attribut, das den Namen des Genres repräsentiert. Es wird automatisch als Spalte in der Tabelle "Genre" gespeichert.

    @ManyToMany // Definiert eine viele-zu-viele-Beziehung zwischen `Genre` und `Movie`.
    @JoinTable(
        name = "Movie_Genre", // Gibt den Namen der Verknüpfungstabelle an, die die Beziehung in der Datenbank darstellt.
        joinColumns = @JoinColumn(name = "genre_id"), // Definiert die Spalte in der Verknüpfungstabelle, die auf `Genre` verweist.
        inverseJoinColumns = @JoinColumn(name = "movie_id") // Definiert die Spalte, die auf `Movie` verweist.
    )
    private Set<Movie> movies = new HashSet<>();
    // Verwendet eine `Set`-Datenstruktur, um sicherzustellen, dass Filme in einem Genre eindeutig sind.
    // Die Beziehung wird von Hibernate automatisch in der Verknüpfungstabelle gepflegt.

    // =====================
    // Getter und Setter
    // =====================
    
    // Gibt die ID des Genres zurück.
    public int getId() {
        return id;
    }

    // Setzt die ID des Genres. In der Regel nicht manuell genutzt, da die ID automatisch generiert wird.
    public void setId(int id) {
        this.id = id;
    }

    // Gibt den Namen des Genres zurück.
    public String getName() {
        return name;
    }

    // Setzt den Namen des Genres.
    public void setName(String name) {
        this.name = name;
    }

    // Gibt die Menge der Filme zurück, die mit diesem Genre verknüpft sind.
    public Set<Movie> getMovies() {
        return movies;
    }

    // Setzt die Menge der Filme, die mit diesem Genre verknüpft sind.
    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    // =====================
    // `toString`-Methode
    // =====================
    
    @Override
    public String toString() {
        // Gibt eine lesbare Darstellung des Genres zurück, die für Debugging und Ausgabe genutzt wird.
        return "Genre{" +
                "id=" + id + // Primärschlüssel
                ", name='" + name + '\'' + // Name des Genres
                '}';
    }
}
