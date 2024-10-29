package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

//Aufgabe 4.5
public class TestMovie {
    public static void main(String[] args) {
        // Verbindungsdetails zur Datenbank
        String host = "localhost";
        String port = "5433";
        String database = "db01";
        String username = "KAKAROT29";
        String password = "EndlichDBS2";
        String jdbcUrl = "jdbc:postgresql://" + host + ":" + port + "/" + database;

        // Verbindung zur Datenbank herstellen
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Verbindung zur Datenbank erfolgreich hergestellt!");

            // Test 1: Einfügen eines neuen Movie-Datensatzes
            Movie movie = new Movie("Inception", 2010, 'F');
            movie.insert(connection);
            System.out.println("Inserted Movie with ID: " + movie.getId());

            // Test 2: Aktualisieren des Movie-Datensatzes
            movie.setTitle("Inception Updated");
            movie.update(connection);
            System.out.println("Updated Movie Title: " + movie.getTitle());

            // Test 3: Suchen nach Movie über ID
            Movie foundMovie = Movie.findById(connection, movie.getId());
            if (foundMovie != null) {
                System.out.println("Found Movie by ID: " + foundMovie.getTitle());
            } else {
                System.out.println("No movie found with ID: " + movie.getId());
            }

            // Test 4: Suchen nach Movie über Titel (enthält)
            List<Movie> moviesByTitle = Movie.findByTitle(connection, "Inception");
            System.out.println("Movies with 'Inception' in the title:");
            for (Movie m : moviesByTitle) {
                System.out.println("- " + m.getTitle() + " (" + m.getYear() + ")");
            }

            // Test 5: Löschen des Movie-Datensatzes
            Long movieIdToDelete = movie.getId(); // Speichere die ID in einer temporären Variable
            movie.delete(connection);
            System.out.println("Deleted Movie with ID: " + movieIdToDelete);

            // Überprüfung, ob der Datensatz wirklich gelöscht wurde
            Movie deletedMovie = Movie.findById(connection, movieIdToDelete);
            if (deletedMovie == null) {
                System.out.println("Movie with ID " + movieIdToDelete + " was successfully deleted.");
            } else {
                System.out.println("Movie was not deleted.");
            }



        } catch (SQLException e) {
            System.out.println("Fehler bei der Verbindung zur Datenbank oder bei SQL-Operationen:");
            e.printStackTrace();
        }
    }
}
