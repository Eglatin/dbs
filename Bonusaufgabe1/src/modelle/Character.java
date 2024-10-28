package modelle;

public class Character {
    private Long id;
    private String name;
    private String alias;
    private Integer position;
    private Long movieId;
    private Long personId;

    // Konstruktoren
    public Character() {}

    public Character(String name, String alias, Integer position, Long movieId, Long personId) {
        this.name = name;
        this.alias = alias;
        this.position = position;
        this.movieId = movieId;
        this.personId = personId;
    }

    // Getter- und Setter-Methoden
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}
