package de.hsh.dbs2.imdb;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        // EntityManagerFactory initialisieren
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("moviedb");
            System.out.println("EntityManagerFactory erfolgreich gestartet!");
        } catch (Exception e) {
            System.err.println("Fehler beim Starten der EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // EntityManagerFactory schließen
            if (emf != null) {
                emf.close();
                System.out.println("EntityManagerFactory geschlossen.");
            }
        }
    }
}
