package de.hsh.dbs2.imdb.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Genre {
    @Id
    private int id;
    private String name;

    @ManyToMany
    @JoinTable(
        name = "Movie_Genre",
        joinColumns = @JoinColumn(name = "genre_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Set<Movie> movies = new HashSet<>();

    // Getter und Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
