package aufgabe41;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class aufgabe1 {
    public static void main(String[] args) {
        // Verbindungsparameter
        String host = "localhost";
        String port = "5433";
        String database = "db01";
        String username = "KAKAROT29";
        String password = "EndlichDBS2";
        
        // Verbindungs-URL erstellen
        String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);

        // ID-Parameter verarbeiten
        Integer movieId = null;
        if (args.length > 0) {
            try {
                movieId = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Film-ID. Bitte geben Sie eine gültige Zahl ein.");
                return;
            }
        }

        // Verbindung zur PostgreSQL-Datenbank herstellen
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            if (conn != null) {
                System.out.println("Verbindung erfolgreich!");

                // SQL-Abfrage vorbereiten
                String sql;
                if (movieId != null) {
                    sql = "SELECT title FROM moviedb2.movie WHERE id = ?";
                } else {
                    sql = "SELECT title FROM moviedb2.movie WHERE id BETWEEN 600000 AND 600100";
                }

                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    if (movieId != null) {
                        pstmt.setInt(1, movieId);
                    }

                    // Abfrage ausführen und Ergebnisse verarbeiten
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        String title = rs.getString("title"); // Hier wird die Spalte "title" abgerufen
                        System.out.printf("Titel: %s%n", title);
                    }
                } catch (SQLException e) {
                    System.out.println("Fehler beim Ausführen der Abfrage: " + e.getMessage());
                }
            } else {
                System.out.println("Verbindung fehlgeschlagen!");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Verbinden: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        String host = "localhost";
        String port = "5433";
        String database = "db01";
        String username = "KAKAROT29";
        String password = "EndlichDBS2";

        // Verbindungs-URL erstellen
        String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);
        
        return DriverManager.getConnection(url, username, password);
    }

}
