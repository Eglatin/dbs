package de.hsh.dbs2.imdb;

import de.hsh.dbs2.imdb.entities.*; // Importiert die Entitätsklassen, die die Datenbanktabellen repräsentieren.

import jakarta.persistence.EntityManager; // Ermöglicht den Zugriff auf die Datenbank.
import jakarta.persistence.EntityManagerFactory; // Erstellt und verwaltet EntityManager-Instanzen.
import jakarta.persistence.Persistence; // Bindet die Konfiguration aus der persistence.xml ein.

import java.util.List; // Wird für die Sammlung von Ergebnissen aus den Datenbankabfragen benötigt.

public class TablePrinter {

    public static void main(String[] args) {
        EntityManagerFactory emf = null; // Verwaltet den Lebenszyklus von EntityManager-Instanzen.

        try {
            // Initialisiert die EntityManagerFactory mit der Konfiguration aus der persistence.xml.
            emf = Persistence.createEntityManagerFactory("movie");
            
            // Erstellt einen EntityManager, um Abfragen gegen die Datenbank auszuführen.
            EntityManager em = emf.createEntityManager();

            System.out.println("Tabelleninhalte:");

            // 1. Ausgabe der Inhalte der Tabelle "Movie".
            printMovies(em);

            // 2. Ausgabe der Inhalte der Tabelle "Genre".
            printGenres(em);

            // 3. Ausgabe der Inhalte der Tabelle "Person".
            printPersons(em);

            // 4. Ausgabe der Inhalte der Tabelle "MovieCharacter".
            printMovieCharacters(em);

            // 5. Ausgabe der Inhalte der Tabelle "Series".
            printSeries(em);

            // 6. Ausgabe der Inhalte der Tabelle "CinemaMovie".
            printCinemaMovies(em);

            // Schließt den EntityManager nach dem Auslesen der Daten.
            em.close();
        } catch (Exception e) {
            // Fängt Fehler ab, die beim Zugriff auf die Datenbank auftreten, und gibt eine Fehlermeldung aus.
            System.err.println("Fehler beim Lesen der Tabellen:");
            e.printStackTrace();
        } finally {
            // Stellt sicher, dass die EntityManagerFactory geschlossen wird, um Ressourcen freizugeben.
            if (emf != null) {
                emf.close();
            }
        }
    }

    // Methode zum Abrufen und Ausgeben aller Einträge in der Tabelle "Movie".
    private static void printMovies(EntityManager em) {
        // Führt eine JPQL-Abfrage aus, um alle Filme aus der Tabelle "Movie" zu laden.
        List<Movie> movies = em.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
        System.out.println("Movies:");
        // Iteriert über die Ergebnisse und gibt jeden Film auf der Konsole aus.
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        System.out.println(); // Fügt eine Leerzeile nach der Ausgabe ein.
    }

    // Methode zum Abrufen und Ausgeben aller Einträge in der Tabelle "Genre".
    private static void printGenres(EntityManager em) {
        // Führt eine JPQL-Abfrage aus, um alle Genres aus der Tabelle "Genre" zu laden.
        List<Genre> genres = em.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
        System.out.println("Genres:");
        // Iteriert über die Ergebnisse und gibt jedes Genre auf der Konsole aus.
        for (Genre genre : genres) {
            System.out.println(genre);
        }
        System.out.println(); // Fügt eine Leerzeile nach der Ausgabe ein.
    }

    // Methode zum Abrufen und Ausgeben aller Einträge in der Tabelle "Person".
    private static void printPersons(EntityManager em) {
        // Führt eine JPQL-Abfrage aus, um alle Personen aus der Tabelle "Person" zu laden.
        List<Person> persons = em.createQuery("SELECT p FROM Person p", Person.class).getResultList();
        System.out.println("Persons:");
        // Iteriert über die Ergebnisse und gibt jede Person auf der Konsole aus.
        for (Person person : persons) {
            System.out.println(person);
        }
        System.out.println(); // Fügt eine Leerzeile nach der Ausgabe ein.
    }

    // Methode zum Abrufen und Ausgeben aller Einträge in der Tabelle "MovieCharacter".
    private static void printMovieCharacters(EntityManager em) {
        // Führt eine JPQL-Abfrage aus, um alle Filmcharaktere aus der Tabelle "MovieCharacter" zu laden.
        List<MovieCharacter> characters = em.createQuery("SELECT mc FROM MovieCharacter mc", MovieCharacter.class).getResultList();
        System.out.println("Movie Characters:");
        // Iteriert über die Ergebnisse und gibt jeden Filmcharakter auf der Konsole aus.
        for (MovieCharacter character : characters) {
            System.out.println(character);
        }
        System.out.println(); // Fügt eine Leerzeile nach der Ausgabe ein.
    }

    // Methode zum Abrufen und Ausgeben aller Einträge in der Tabelle "Series".
    private static void printSeries(EntityManager em) {
        // Führt eine JPQL-Abfrage aus, um alle Serien aus der Tabelle "Series" zu laden.
        List<Series> seriesList = em.createQuery("SELECT s FROM Series s", Series.class).getResultList();
        System.out.println("Series:");
        // Iteriert über die Ergebnisse und gibt jede Serie auf der Konsole aus.
        for (Series series : seriesList) {
            System.out.println(series);
        }
        System.out.println(); // Fügt eine Leerzeile nach der Ausgabe ein.
    }

    // Methode zum Abrufen und Ausgeben aller Einträge in der Tabelle "CinemaMovie".
    private static void printCinemaMovies(EntityManager em) {
        // Führt eine JPQL-Abfrage aus, um alle Kinofilme aus der Tabelle "CinemaMovie" zu laden.
        List<CinemaMovie> cinemaMovies = em.createQuery("SELECT cm FROM CinemaMovie cm", CinemaMovie.class).getResultList();
        System.out.println("Cinema Movies:");
        // Iteriert über die Ergebnisse und gibt jeden Kinofilm auf der Konsole aus.
        for (CinemaMovie cinemaMovie : cinemaMovies) {
            System.out.println(cinemaMovie);
        }
        System.out.println(); // Fügt eine Leerzeile nach der Ausgabe ein.
    }
}
