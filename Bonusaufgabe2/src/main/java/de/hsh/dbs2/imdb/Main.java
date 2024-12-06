package de.hsh.dbs2.imdb;

import de.hsh.dbs2.imdb.entities.*; // Importiert die Entitätsklassen, die in der Datenbank persistiert werden sollen.

import jakarta.persistence.EntityManager; // EntityManager wird benötigt, um Datenbankoperationen durchzuführen.
import jakarta.persistence.EntityManagerFactory; // Erzeugt EntityManager-Instanzen.
import jakarta.persistence.Persistence; // Ermöglicht Zugriff auf die Konfigurationsinformationen in persistence.xml.

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = null; // EntityManagerFactory wird für die Verwaltung der EntityManager-Instanzen verwendet.

        try {
            // Initialisiert die EntityManagerFactory basierend auf der Konfiguration in der persistence.xml.
            // Der Parameter "movie" muss mit dem Namen der <persistence-unit> in der persistence.xml übereinstimmen.
            emf = Persistence.createEntityManagerFactory("movie");
            System.out.println("EntityManagerFactory erfolgreich erstellt!");

            // Erstellt einen EntityManager, der für die Durchführung von Datenbankoperationen zuständig ist.
            EntityManager em = emf.createEntityManager();

            // Startet eine neue Datenbanktransaktion. Alle Änderungen an der Datenbank müssen innerhalb einer Transaktion erfolgen.
            em.getTransaction().begin();

            /* ========================
             * Test 1: Series ↔ CinemaMovie
             * ======================== */
            
            // Erstellt ein neues Series-Objekt und setzt dessen Titel.
            Series series = new Series();
            series.setTitle("Harry Potter Series"); // Setzt den Titel der Serie.
            
            // Speichert das Series-Objekt in der Datenbank. Hibernate verwaltet die Persistenz.
            em.persist(series);

            // Erstellt ein neues CinemaMovie-Objekt und verknüpft es mit der Serie.
            CinemaMovie cinemaMovie1 = new CinemaMovie();
            cinemaMovie1.setName("Harry Potter and the Philosopher's Stone"); // Setzt den Namen des Films.
            cinemaMovie1.setSeries(series); // Verknüpft den Film mit der Serie.
            
            // Speichert das CinemaMovie-Objekt in der Datenbank.
            em.persist(cinemaMovie1);

            // Wiederholt den Vorgang für einen weiteren Film der Serie.
            CinemaMovie cinemaMovie2 = new CinemaMovie();
            cinemaMovie2.setName("Harry Potter and the Chamber of Secrets");
            cinemaMovie2.setSeries(series); // Verknüpft auch diesen Film mit der Serie.
            em.persist(cinemaMovie2);

            // Ausgabe, dass die Serie und die zugehörigen Filme erfolgreich gespeichert wurden.
            System.out.println("Series und CinemaMovies gespeichert.");

            /* ========================
             * Test 2: Movie ↔ Genre
             * ======================== */
            
            // Erstellt ein neues Movie-Objekt und setzt dessen Attribute.
            Movie movie = new Movie();
            movie.setId(1); // Achtung: Hier wird die ID manuell gesetzt. Besser wäre @GeneratedValue.
            movie.setTitle("The Matrix"); // Setzt den Titel des Films.
            movie.setYear(1999); // Setzt das Erscheinungsjahr.
            movie.setType("Movie"); // Setzt den Typ des Films.
            
            // Speichert das Movie-Objekt in der Datenbank.
            em.persist(movie);

            // Erstellt ein Genre-Objekt und verknüpft es mit dem Film.
            Genre genre1 = new Genre();
            genre1.setId(1); // Manuelle ID-Zuweisung.
            genre1.setName("Sci-Fi"); // Setzt den Namen des Genres.
            genre1.getMovies().add(movie); // Fügt den Film zur Liste der Filme des Genres hinzu.
            
            // Speichert das Genre-Objekt in der Datenbank.
            em.persist(genre1);

            // Wiederholt den Vorgang für ein weiteres Genre.
            Genre genre2 = new Genre();
            genre2.setId(2);
            genre2.setName("Action");
            genre2.getMovies().add(movie); // Verknüpft das Genre ebenfalls mit dem Film.
            em.persist(genre2);

            // Verknüpft den Film mit den Genres, um die Beziehung bidirektional zu halten.
            movie.getGenres().add(genre1);
            movie.getGenres().add(genre2);

            // Ausgabe, dass der Film und die Genres erfolgreich gespeichert wurden.
            System.out.println("Movie und Genres gespeichert.");

            /* ========================
             * Test 3: MovieCharacter ↔ Person ↔ Movie
             * ======================== */
            
            // Erstellt eine neue Person und setzt deren Name.
            Person person = new Person();
            person.setName("Keanu Reeves"); // Setzt den Namen der Person.
            
            // Speichert die Person in der Datenbank.
            em.persist(person);

            // Erstellt einen neuen MovieCharacter und verknüpft ihn mit der Person und dem Film.
            MovieCharacter character = new MovieCharacter();
            character.setName("Neo"); // Setzt den Namen der Rolle.
            character.setActor(person); // Verknüpft die Rolle mit der Person.
            character.setMovie(movie); // Verknüpft die Rolle mit dem Film.
            
            // Speichert den MovieCharacter in der Datenbank.
            em.persist(character);

            // Ausgabe, dass der Charakter, die Person und der Film erfolgreich gespeichert wurden.
            System.out.println("MovieCharacter, Person und Movie gespeichert.");

            // Bestätigt die Transaktion und schreibt alle Änderungen in die Datenbank.
            em.getTransaction().commit();

            // Schließt den EntityManager nach Abschluss der Operationen.
            em.close();
        } catch (Exception e) {
            // Behandelt auftretende Fehler während der Datenbankoperationen.
            System.err.println("Fehler während der Tests:");
            e.printStackTrace();
        } finally {
            // Schließt die EntityManagerFactory, um Ressourcen freizugeben.
            if (emf != null) {
                emf.close();
                System.out.println("EntityManagerFactory geschlossen.");
            }
        }
    }
}
