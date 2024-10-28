package modelle;

public class Genre {
    private Long id;
    private String name;

    // Konstruktoren
    public Genre() {}

    public Genre(String name) {
        this.name = name;
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
}
