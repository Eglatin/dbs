package de.hsh.dbs2.imdb.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL)
    private List<MovieCharacter> characters;

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
    public List<MovieCharacter> getCharacters() {
        return characters;
    }
    public void setCharacters(List<MovieCharacter> characters) {
        this.characters = characters;
    }
}
