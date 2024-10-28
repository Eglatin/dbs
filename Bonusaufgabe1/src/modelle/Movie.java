package modelle;

public class Movie {
    private Long id;
    private String title;
    private int year;
    private char type;

    // Konstruktoren
    public Movie() {}

    public Movie(String title, int year, char type) {
        this.title = title;
        this.year = year;
        this.type = type;
    }

    // Getter- und Setter-Methoden
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }
}
