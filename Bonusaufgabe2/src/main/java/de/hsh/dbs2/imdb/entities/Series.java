package de.hsh.dbs2.imdb.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Series")
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL)
    private List<CinemaMovie> cinemaMovies;

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CinemaMovie> getCinemaMovies() {
        return cinemaMovies;
    }

    public void setCinemaMovies(List<CinemaMovie> cinemaMovies) {
        this.cinemaMovies = cinemaMovies;
    }

    @Override
    public String toString() {
        return "Series{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
