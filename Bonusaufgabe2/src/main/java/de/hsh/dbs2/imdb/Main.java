package de.hsh.dbs2.imdb;

import de.hsh.dbs2.imdb.entities.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;

        try {
            emf = Persistence.createEntityManagerFactory("movie");
            System.out.println("EntityManagerFactory erfolgreich erstellt!");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            // 1. Test: Series ↔ CinemaMovie
            Series series = new Series();
            series.setTitle("Harry Potter Series");
            em.persist(series);

            CinemaMovie cinemaMovie1 = new CinemaMovie();
            cinemaMovie1.setName("Harry Potter and the Philosopher's Stone");
            cinemaMovie1.setSeries(series);
            em.persist(cinemaMovie1);

            CinemaMovie cinemaMovie2 = new CinemaMovie();
            cinemaMovie2.setName("Harry Potter and the Chamber of Secrets");
            cinemaMovie2.setSeries(series);
            em.persist(cinemaMovie2);

            System.out.println("Series und CinemaMovies gespeichert.");

            // 2. Test: Movie ↔ Genre
            Movie movie = new Movie();
            movie.setId(1);
            movie.setTitle("The Matrix");
            movie.setYear(1999);
            movie.setType("Movie");
            em.persist(movie);

            Genre genre1 = new Genre();
            genre1.setId(1);
            genre1.setName("Sci-Fi");
            genre1.getMovies().add(movie);
            em.persist(genre1);

            Genre genre2 = new Genre();
            genre2.setId(2);
            genre2.setName("Action");
            genre2.getMovies().add(movie);
            em.persist(genre2);

            movie.getGenres().add(genre1);
            movie.getGenres().add(genre2);
            System.out.println("Movie und Genres gespeichert.");

            // 3. Test: MovieCharacter ↔ Person ↔ Movie
            Person person = new Person();
            person.setName("Keanu Reeves");
            em.persist(person);

            MovieCharacter character = new MovieCharacter();
            character.setName("Neo");
            character.setActor(person);
            character.setMovie(movie);
            em.persist(character);

            System.out.println("MovieCharacter, Person und Movie gespeichert.");

            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.err.println("Fehler während der Tests:");
            e.printStackTrace();
        } finally {
            if (emf != null) {
                emf.close();
                System.out.println("EntityManagerFactory geschlossen.");
            }
        }
    }
}
