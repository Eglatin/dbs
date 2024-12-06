package de.hsh.dbs2.imdb;

import jakarta.persistence.EntityManager; // EntityManager wird verwendet, um Datenbankoperationen durchzuführen.
import jakarta.persistence.EntityManagerFactory; // Erstellt EntityManager-Instanzen.
import jakarta.persistence.Persistence; // Ermöglicht den Zugriff auf die Konfigurationsinformationen in der persistence.xml.

public class TableCleaner {

    public static void main(String[] args) {
        EntityManagerFactory emf = null; // EntityManagerFactory verwaltet EntityManager und den Zugriff auf die Datenbank.

        try {
            // Initialisiert die EntityManagerFactory mit der Konfiguration aus der persistence.xml.
            // Der Parameter "movie" muss mit der <persistence-unit> in der persistence.xml übereinstimmen.
            emf = Persistence.createEntityManagerFactory("movie");

            // Erstellt einen EntityManager, der für alle Datenbankoperationen genutzt wird.
            EntityManager em = emf.createEntityManager();

            // Startet eine neue Transaktion, um mehrere Löschoperationen durchzuführen.
            em.getTransaction().begin();

            System.out.println("Löschen der Tabelleninhalte...");

            // Löscht alle Einträge in der Tabelle "MovieCharacter".
            // Wichtig: Die Reihenfolge der Löschoperationen ist entscheidend, um Fremdschlüsselverletzungen zu vermeiden.
            em.createQuery("DELETE FROM MovieCharacter").executeUpdate();

            // Löscht alle Einträge in der Tabelle "CinemaMovie".
            em.createQuery("DELETE FROM CinemaMovie").executeUpdate();

            // Löscht alle Einträge in der Tabelle "Series".
            em.createQuery("DELETE FROM Series").executeUpdate();

            // Löscht alle Einträge in der Tabelle "Genre".
            em.createQuery("DELETE FROM Genre").executeUpdate();

            // Löscht alle Einträge in der Tabelle "Movie".
            em.createQuery("DELETE FROM Movie").executeUpdate();

            // Löscht alle Einträge in der Tabelle "Person".
            em.createQuery("DELETE FROM Person").executeUpdate();

            // Bestätigt die Transaktion und schreibt die Änderungen in die Datenbank.
            em.getTransaction().commit();

            System.out.println("Alle Tabelleninhalte wurden gelöscht.");

            // Schließt den EntityManager, um Ressourcen freizugeben.
            em.close();
        } catch (Exception e) {
            // Fängt Fehler ab, die während der Löschoperationen auftreten, und gibt eine Fehlermeldung aus.
            System.err.println("Fehler beim Löschen der Tabelleninhalte:");
            e.printStackTrace();
        } finally {
            // Stellt sicher, dass die EntityManagerFactory geschlossen wird, selbst wenn ein Fehler auftritt.
            if (emf != null) {
                emf.close();
            }
        }
    }
}
