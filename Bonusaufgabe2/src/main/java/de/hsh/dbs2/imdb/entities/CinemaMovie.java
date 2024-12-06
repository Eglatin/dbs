package de.hsh.dbs2.imdb.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "CinemaMovie")
public class CinemaMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    @Override
    public String toString() {
        return "CinemaMovie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", series=" + (series != null ? series.getTitle() : "null") +
                '}';
    }
}
