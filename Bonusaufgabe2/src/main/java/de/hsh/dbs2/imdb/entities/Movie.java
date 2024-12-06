package de.hsh.dbs2.imdb.entities;

import jakarta.persistence.*; // Importiert JPA-Annotationen für die Entitätsdefinition.
import java.util.HashSet; // Wird für die Initialisierung der Set-Datenstruktur verwendet.
import java.util.Set; // Repräsentiert eine Sammlung von eindeutigen Elementen.

@Entity // Kennzeichnet diese Klasse als JPA-Entität, die mit einer Datenbanktabelle verbunden ist.
public class Movie {

    @Id // Kennzeichnet das Feld `id` als Primärschlüssel der Tabelle.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Gibt an, dass die ID automatisch von der Datenbank generiert wird, z. B. bei MySQL als AUTO_INCREMENT.
    private int id;

    private String title; // Speichert den Titel des Films.
    private int year; // Speichert das Erscheinungsjahr des Films.
    private String type; 
    // Speichert den Typ des Films, z. B. "Movie", "Series", etc. Kann für Kategorien verwendet werden.

    @ManyToMany(mappedBy = "movies")
    // Definiert die viele-zu-viele-Beziehung zwischen `Movie` und `Genre`.
    // `mappedBy` gibt an, dass `Genre` die Beziehung verwaltet und die Verknüpfungstabelle definiert.
    private Set<Genre> genres = new HashSet<>();
    // Verwendet ein `Set`, um sicherzustellen, dass jedes Genre nur einmal mit einem Film verknüpft ist.

    // =====================
    // Getter und Setter
    // =====================

    // Gibt die ID des Films zurück.
    public int getId() {
        return id;
    }

    // Setzt die ID des Films. In der Regel wird dies nicht manuell genutzt, da die ID automatisch generiert wird.
    public void setId(int id) {
        this.id = id;
    }

    // Gibt den Titel des Films zurück.
    public String getTitle() {
        return title;
    }

    // Setzt den Titel des Films.
    public void setTitle(String title) {
        this.title = title;
    }

    // Gibt das Erscheinungsjahr des Films zurück.
    public int getYear() {
        return year;
    }

    // Setzt das Erscheinungsjahr des Films.
    public void setYear(int year) {
        this.year = year;
    }

    // Gibt den Typ des Films zurück.
    public String getType() {
        return type;
    }

    // Setzt den Typ des Films.
    public void setType(String type) {
        this.type = type;
    }

    // Gibt die Menge der Genres zurück, die mit diesem Film verknüpft sind.
    public Set<Genre> getGenres() {
        return genres;
    }

    // Setzt die Menge der Genres, die mit diesem Film verknüpft sind.
    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    // =====================
    // `toString`-Methode
    // =====================
    @Override
    public String toString() {
        // Gibt eine lesbare Darstellung des Films zurück, die für Debugging und Ausgabe genutzt wird.
        return "Movie{" +
                "id=" + id + // Primärschlüssel
                ", title='" + title + '\'' + // Titel des Films
                ", year=" + year + // Erscheinungsjahr
                ", type='" + type + '\'' + // Typ des Films
                '}';
    }
}
