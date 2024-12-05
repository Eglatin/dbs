package de.hsh.dbs2.imdb.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "MovieCharacter")
public class MovieCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person actor;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

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
    public Person getActor() {
        return actor;
    }
    public void setActor(Person actor) {
        this.actor = actor;
    }
    public Movie getMovie() {
        return movie;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
