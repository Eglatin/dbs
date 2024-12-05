package de.hsh.dbs2.imdb.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToMany
    @JoinTable(
        name = "Movie_Genre",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieCharacter> characters;

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
    public List<Genre> getGenres() {
        return genres;
    }
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
    public List<MovieCharacter> getCharacters() {
        return characters;
    }
    public void setCharacters(List<MovieCharacter> characters) {
        this.characters = characters;
    }
}
