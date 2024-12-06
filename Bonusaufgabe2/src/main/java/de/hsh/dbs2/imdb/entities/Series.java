package de.hsh.dbs2.imdb.entities;

import jakarta.persistence.*; // Importiert JPA-Annotationen für die Entitätsdefinition.
import java.util.List; // Repräsentiert eine Sammlung von Kinofilmen, die zur Serie gehören.

@Entity // Kennzeichnet diese Klasse als JPA-Entität, die mit einer Datenbanktabelle verknüpft ist.
@Table(name = "Series") 
// Legt den Tabellennamen in der Datenbank explizit auf "Series" fest.
public class Series {

    @Id // Kennzeichnet das Feld `id` als Primärschlüssel der Tabelle.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Gibt an, dass die ID automatisch von der Datenbank generiert wird, z. B. durch AUTO_INCREMENT.
    private Long id;

    @Column(nullable = false) 
    // Definiert, dass das Feld `title` in der Datenbank nicht null sein darf.
    private String title;

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL)
    // Definiert eine eins-zu-viele-Beziehung zwischen `Series` und `CinemaMovie`.
    // `mappedBy = "series"` gibt an, dass das Feld `series` in `CinemaMovie` die Beziehung verwaltet.
    // `cascade = CascadeType.ALL` stellt sicher, dass Änderungen an einer Serie auch auf die verknüpften `CinemaMovie`-Objekte angewendet werden.
    private List<CinemaMovie> cinemaMovies;

    // =====================
    // Getter und Setter
    // =====================

    // Gibt die ID der Serie zurück.
    public Long getId() {
        return id;
    }

    // Setzt die ID der Serie. In der Regel wird dies nicht manuell genutzt, da die ID automatisch generiert wird.
    public void setId(Long id) {
        this.id = id;
    }

    // Gibt den Titel der Serie zurück.
    public String getTitle() {
        return title;
    }

    // Setzt den Titel der Serie.
    public void setTitle(String title) {
        this.title = title;
    }

    // Gibt die Liste der Kinofilme zurück, die zu dieser Serie gehören.
    public List<CinemaMovie> getCinemaMovies() {
        return cinemaMovies;
    }

    // Setzt die Liste der Kinofilme, die zu dieser Serie gehören.
    public void setCinemaMovies(List<CinemaMovie> cinemaMovies) {
        this.cinemaMovies = cinemaMovies;
    }

    // =====================
    // `toString`-Methode
    // =====================
    @Override
    public String toString() {
        // Gibt eine lesbare Darstellung der Serie zurück, die für Debugging und Ausgabe genutzt wird.
        // Beinhaltet nur `id` und `title`, um unnötige Komplexität oder rekursive Ausgaben zu vermeiden.
        return "Series{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
