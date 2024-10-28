package modelle;

public class Movie_Genre {
    private Long movieId;
    private Long genreId;

    // Konstruktoren
    public Movie_Genre() {}

    public Movie_Genre(Long movieId, Long genreId) {
        this.movieId = movieId;
        this.genreId = genreId;
    }

    // Getter- und Setter-Methoden
    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }
}
