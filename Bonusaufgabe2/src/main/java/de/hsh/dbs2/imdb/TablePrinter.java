package de.hsh.dbs2.imdb;

import de.hsh.dbs2.imdb.entities.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class TablePrinter {

    public static void main(String[] args) {
        EntityManagerFactory emf = null;

        try {
            // EntityManagerFactory erstellen
            emf = Persistence.createEntityManagerFactory("movie");
            EntityManager em = emf.createEntityManager();

            System.out.println("Tabelleninhalte:");

            // 1. Ausgabe der Movie-Tabelle
            printMovies(em);

            // 2. Ausgabe der Genre-Tabelle
            printGenres(em);

            // 3. Ausgabe der Person-Tabelle
            printPersons(em);

            // 4. Ausgabe der MovieCharacter-Tabelle
            printMovieCharacters(em);

            // 5. Ausgabe der Series-Tabelle
            printSeries(em);

            // 6. Ausgabe der CinemaMovie-Tabelle
            printCinemaMovies(em);

            em.close();
        } catch (Exception e) {
            System.err.println("Fehler beim Lesen der Tabellen:");
            e.printStackTrace();
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }

    private static void printMovies(EntityManager em) {
        List<Movie> movies = em.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
        System.out.println("Movies:");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        System.out.println();
    }

    private static void printGenres(EntityManager em) {
        List<Genre> genres = em.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
        System.out.println("Genres:");
        for (Genre genre : genres) {
            System.out.println(genre);
        }
        System.out.println();
    }

    private static void printPersons(EntityManager em) {
        List<Person> persons = em.createQuery("SELECT p FROM Person p", Person.class).getResultList();
        System.out.println("Persons:");
        for (Person person : persons) {
            System.out.println(person);
        }
        System.out.println();
    }

    private static void printMovieCharacters(EntityManager em) {
        List<MovieCharacter> characters = em.createQuery("SELECT mc FROM MovieCharacter mc", MovieCharacter.class).getResultList();
        System.out.println("Movie Characters:");
        for (MovieCharacter character : characters) {
            System.out.println(character);
        }
        System.out.println();
    }

    private static void printSeries(EntityManager em) {
        List<Series> seriesList = em.createQuery("SELECT s FROM Series s", Series.class).getResultList();
        System.out.println("Series:");
        for (Series series : seriesList) {
            System.out.println(series);
        }
        System.out.println();
    }

    private static void printCinemaMovies(EntityManager em) {
        List<CinemaMovie> cinemaMovies = em.createQuery("SELECT cm FROM CinemaMovie cm", CinemaMovie.class).getResultList();
        System.out.println("Cinema Movies:");
        for (CinemaMovie cinemaMovie : cinemaMovies) {
            System.out.println(cinemaMovie);
        }
        System.out.println();
    }
}
