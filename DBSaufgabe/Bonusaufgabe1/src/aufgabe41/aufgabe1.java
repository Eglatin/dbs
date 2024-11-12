package aufgabe41;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.SwingUtilities;
import gui.SearchMovieDialog;
import gui.SearchMovieDialogCallback;

public class aufgabe1 {

    // Statische Verbindung zur Datenbank
    private static Connection conn;

    static {
        try {
            // Verbindungsdetails festlegen
            String host = "localhost";
            String port = "5433";
            String database = "db01";
            String username = "KAKAROT29";
            String password = "EndlichDBS2";
            
            // Verbindungs-URL erstellen
            String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);
            
            // Verbindung zur Datenbank herstellen
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);
            System.out.println("Verbindung erfolgreich hergestellt...");
        } catch (Exception e) {
            System.err.println("Fehler beim Verbinden zur Datenbank");
            e.printStackTrace();
            System.exit(1);
        }
    }

    // Zugriffsmethode für die Verbindung
    public static Connection getConnection() {
        return conn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new aufgabe1().run();
            }
        });

        // Beispiel: Abfrage und Ausgabe der Genre-Tabelle
        try {
            queryGenreTable();
        } catch (Exception e) {
            System.err.println("Fehler bei der Tabellenabfrage: " + e.getMessage());
        }
    }

    // Methode zur Ausf端hrung der Anwendung und Anzeige der GUI
    public void run() {
        SearchMovieDialogCallback callback = new SearchMovieDialogCallback();
        SearchMovieDialog sd = new SearchMovieDialog(callback);
        sd.setVisible(true);
    }

    // Beispielmethode zum Abfragen und Ausgeben der Movie-Tabelle
    public static void queryGenreTable() throws Exception {
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM Movie";  // Beispiel: Abfrage der Movie-Tabelle
        ResultSet rs = stmt.executeQuery(sql);
        
        // Abfrageergebnisse ausgeben
        while (rs.next()) {
            System.out.println("Movie ID: " + rs.getInt("movieid"));  // Richtiger Spaltenname ist "movieid"
            System.out.println("Title: " + rs.getString("title"));
            System.out.println("Year: " + rs.getInt("year"));
            System.out.println("Type: " + rs.getString("type"));
            System.out.println("-------------------------");
        }
        
        rs.close();
        stmt.close(); 
    }

}
