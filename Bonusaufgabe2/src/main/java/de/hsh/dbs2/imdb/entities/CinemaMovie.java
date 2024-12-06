package de.hsh.dbs2.imdb;

import de.hsh.dbs2.imdb.entities.*; // Importiert alle Entitätsklassen, die die verschiedenen Tabellen in der Datenbank repräsentieren.
import jakarta.persistence.EntityManager; // Ermöglicht den Zugriff auf die Datenbank, um CRUD-Operationen auszuführen.
import jakarta.persistence.EntityManagerFactory; // Verwaltet den Lebenszyklus von EntityManager-Instanzen.
import jakarta.persistence.Persistence; // Stellt die Verbindung zwischen dem Code und der persistence.xml her.
import java.util.List; // Wird benötigt, um die Abfrageergebnisse in Listen zu speichern.

public class TablePrinter {

    public static void main(String[] args) {
        EntityManagerFactory emf = null; // EntityManagerFactory verwaltet EntityManager und die Verbindung zur Datenbank.

        try {
            // Initialisiert die EntityManagerFactory mit der in der persistence.xml definierten "persistence-unit".
            // Der Name "movie" muss mit der Definition in der persistence.xml übereinstimmen.
            emf = Persistence.createEntityManagerFactory("movie");
            
            // Erstellt einen EntityManager, der für die Abfragen in der Datenbank verwendet wird.
            EntityManager em = emf.createEntityManager();

            System.out.println("Tabelleninhalte:");

            // Ruft alle Daten aus der Tabelle "Movie" ab und gibt sie aus.
            printMovies(em);

            // Ruft alle Daten aus der Tabelle "Genre" ab und gibt sie aus.
            printGenres(em);

            // Ruft alle Daten aus der Tabelle "Person" ab und gibt sie aus.
            printPersons(em);

            // Ruft alle Daten aus der Tabelle "MovieCharacter" ab und gibt sie aus.
            printMovieCharacters(em);

            // Ruft alle Daten aus der Tabelle "Series" ab und gibt sie aus.
            printSeries(em);

            // Ruft alle Daten aus der Tabelle "CinemaMovie" ab und gibt sie aus.
            printCinemaMovies(em);

            // Schließt den EntityManager, nachdem alle Abfragen durchgeführt wurden.
            em.close();
        } catch (Exception e) {
            // Fängt mögliche Fehler ab und gibt eine Fehlermeldung aus.
            System.err.println("Fehler beim Lesen der Tabellen:");
            e.printStackTrace(); // Gibt den Fehler-Stacktrace zur besseren Diagnose aus.
        } finally {
            // Stellt sicher, dass die EntityManagerFactory geschlossen wird, um Ressourcenlecks zu vermeiden.
            if (emf != null) {
                emf.close();
            }
        }
    }

    // Diese Methode ruft alle Filme aus der Tabelle "Movie" ab und gibt sie auf der Konsole aus.
    private static void printMovies(EntityManager em) {
        // Führt eine JPQL-Abfrage aus, um alle Einträge in der Tabelle "Movie" zu laden.
        List<Movie> movies = em.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
        System.out.println("Movies:");
        // Iteriert durch die Liste der Filme und gibt jeden Film aus.
        for (Movie movie : movies) {
            System.out.println(movie); // Nutzt die toString-Methode der Entität Movie.
        }
        System.out.println(); // Fügt eine Leerzeile zur besseren Lesbarkeit ein.
    }

    // Diese Methode ruft alle Genres aus der Tabelle "Genre" ab und gibt sie auf der Konsole aus.
    private static void printGenres(EntityManager em) {
        // Führt eine JPQL-Abfrage aus, um alle Einträge in der Tabelle "Genre" zu laden.
        List<Genre> genres = em.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
        System.out.println("Genres:");
        // Iteriert durch die Liste der Genres und gibt jedes Genre aus.
        for (Genre genre : genres) {
            System.out.println(genre); // Nutzt die toString-Methode der Entität Genre.
        }
        System.out.println(); // Fügt eine Leerzeile zur besseren Lesbarkeit ein.
    }

    // Diese Methode ruft alle Personen aus der Tabelle "Person" ab und gibt sie auf der Konsole aus.
    private static void printPersons(EntityManager em) {
        // Führt eine JPQL-Abfrage aus, um alle Einträge in der Tabelle "Person" zu laden.
        List<Person> persons = em.createQuery("SELECT p FROM Person p", Person.class).getResultList();
        System.out.println("Persons:");
        // Iteriert durch die Liste der Personen und gibt jede Person aus.
        for (Person person : persons) {
            System.out.println(person); // Nutzt die toString-Methode der Entität Person.
        }
        System.out.println(); // Fügt eine Leerzeile zur besseren Lesbarkeit ein.
    }

    // Diese Methode ruft alle Filmcharaktere aus der Tabelle "MovieCharacter" ab und gibt sie aus.
    private static void printMovieCharacters(EntityManager em) {
        // Führt eine JPQL-Abfrage aus, um alle Einträge in der Tabelle "MovieCharacter" zu laden.
        List<MovieCharacter> characters = em.createQuery("SELECT mc FROM MovieCharacter mc", MovieCharacter.class).getResultList();
        System.out.println("Movie Characters:");
        // Iteriert durch die Liste der Filmcharaktere und gibt jeden Charakter aus.
        for (MovieCharacter character : characters) {
            System.out.println(character); // Nutzt die toString-Methode der Entität MovieCharacter.
        }
        System.out.println(); // Fügt eine Leerzeile zur besseren Lesbarkeit ein.
    }

    // Diese Methode ruft alle Serien aus der Tabelle "Series" ab und gibt sie auf der Konsole aus.
    private static void printSeries(EntityManager em) {
        // Führt eine JPQL-Abfrage aus, um alle Einträge in der Tabelle "Series" zu laden.
        List<Series> seriesList = em.createQuery("SELECT s FROM Series s", Series.class).getResultList();
        System.out.println("Series:");
        // Iteriert durch die Liste der Serien und gibt jede Serie aus.
        for (Series series : seriesList) {
            System.out.println(series); // Nutzt die toString-Methode der Entität Series.
        }
        System.out.println(); // Fügt eine Leerzeile zur besseren Lesbarkeit ein.
    }

    // Diese Methode ruft alle Kinofilme aus der Tabelle "CinemaMovie" ab und gibt sie aus.
    private static void printCinemaMovies(EntityManager em) {
        // Führt eine JPQL-Abfrage aus, um alle Einträge in der Tabelle "CinemaMovie" zu laden.
        List<CinemaMovie> cinemaMovies = em.createQuery("SELECT cm FROM CinemaMovie cm", CinemaMovie.class).getResultList();
        System.out.println("Cinema Movies:");
        // Iteriert durch die Liste der Kinofilme und gibt jeden Film aus.
        for (CinemaMovie cinemaMovie : cinemaMovies) {
            System.out.println(cinemaMovie); // Nutzt die toString-Methode der Entität CinemaMovie.
        }
        System.out.println(); // Fügt eine Leerzeile zur besseren Lesbarkeit ein.
    }
}
