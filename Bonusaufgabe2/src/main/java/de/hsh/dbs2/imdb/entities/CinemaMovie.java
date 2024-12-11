package de.hsh.dbs2.imdb.entities;

import jakarta.persistence.*; // Importiert JPA-Annotationen für die Entitätsdefinition.

@Entity // Kennzeichnet diese Klasse als JPA-Entität, die mit einer Datenbanktabelle verknüpft ist.
@Table(name = "CinemaMovie") 
// Legt den Tabellennamen in der Datenbank explizit auf "CinemaMovie" fest.
public class CinemaMovie {

    @Id // Kennzeichnet das Feld `id` als Primärschlüssel der Tabelle.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Gibt an, dass die ID automatisch von der Datenbank generiert wird, z. B. durch AUTO_INCREMENT.
    private Long id;

    @Column(nullable = false) 
    // Definiert, dass das Feld `name` in der Datenbank nicht null sein darf.
    private String name;

    @Column(nullable = false)
    // Definiert, dass das Feld `ticketsSold` in der Datenbank nicht null sein darf.
    private int ticketsSold;

    @ManyToOne // Definiert eine viele-zu-eins-Beziehung zwischen `CinemaMovie` und `Series`.
    @JoinColumn(name = "series_id") 
    // Gibt an, dass die Spalte `series_id` in der Tabelle "CinemaMovie" ein Fremdschlüssel ist, der auf `Series` verweist.
    private Series series;

    // =====================
    // Getter und Setter
    // =====================

    // Gibt die ID des CinemaMovies zurück.
    public Long getId() {
        return id;
    }

    // Setzt die ID des CinemaMovies. In der Regel wird dies nicht manuell genutzt, da die ID automatisch generiert wird.
    public void setId(Long id) {
        this.id = id;
    }

    // Gibt den Namen des CinemaMovies zurück.
    public String getName() {
        return name;
    }

    // Setzt den Namen des CinemaMovies.
    public void setName(String name) {
        this.name = name;
    }

    // Gibt die Anzahl der verkauften Tickets zurück.
    public int getTicketsSold() {
        return ticketsSold;
    }

    // Setzt die Anzahl der verkauften Tickets.
    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    // Gibt die verknüpfte Serie zurück.
    public Series getSeries() {
        return series;
    }

    // Verknüpft eine Serie mit diesem CinemaMovie.
    public void setSeries(Series series) {
        this.series = series;
    }

    // =====================
    // `toString`-Methode
    // =====================
    @Override
    public String toString() {
        // Gibt eine lesbare Darstellung des CinemaMovies zurück, die für Debugging und Ausgabe genutzt wird.
        return "CinemaMovie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ticketsSold=" + ticketsSold +
                ", series=" + (series != null ? series.getTitle() : "null") +
                '}';
    }
}
