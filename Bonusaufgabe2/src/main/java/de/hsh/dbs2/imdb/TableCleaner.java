package de.hsh.dbs2.imdb;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TableCleaner {

    public static void main(String[] args) {
        EntityManagerFactory emf = null;

        try {
            // EntityManagerFactory erstellen
            emf = Persistence.createEntityManagerFactory("movie");
            EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();

            System.out.println("Löschen der Tabelleninhalte...");

            // Löschen der Inhalte aus den Tabellen (Reihenfolge beachten wegen Fremdschlüsselabhängigkeiten)
            em.createQuery("DELETE FROM MovieCharacter").executeUpdate();
            em.createQuery("DELETE FROM CinemaMovie").executeUpdate();
            em.createQuery("DELETE FROM Series").executeUpdate();
            em.createQuery("DELETE FROM Genre").executeUpdate();
            em.createQuery("DELETE FROM Movie").executeUpdate();
            em.createQuery("DELETE FROM Person").executeUpdate();

            em.getTransaction().commit();
            System.out.println("Alle Tabelleninhalte wurden gelöscht.");

            em.close();
        } catch (Exception e) {
            System.err.println("Fehler beim Löschen der Tabelleninhalte:");
            e.printStackTrace();
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }
}
