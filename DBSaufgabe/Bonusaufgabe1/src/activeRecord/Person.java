package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import aufgabe41.aufgabe1;

public class Person {
    private Long id;
    private String name;

    public Person() {}

    public Person(String name) {
        this.name = name;
    }

    // Getter und Setter für alle Attribute
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Insert-Methode
    public void insert() throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "INSERT INTO Person (name) VALUES (?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, name);
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.id = rs.getLong(1);
                    }
                }
            }
        }
    }

    // Update-Methode
    public void update() throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "UPDATE Person SET name = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setLong(2, id);
                stmt.executeUpdate();
            }
        }
    }

    // Delete-Methode
    public void delete() throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "DELETE FROM Person WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }
        }
    }

    // FindById-Methode
    public static Person findById(long id) throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "SELECT * FROM Person WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Person person = new Person();
                        person.id = rs.getLong("id");
                        person.name = rs.getString("name");
                        return person;
                    }
                }
            }
        }
        return null;
    }

    // FindByName-Methode
    public static List<Person> findByName(String name) throws SQLException {
        List<Person> persons = new ArrayList<>();
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "SELECT * FROM Person WHERE LOWER(name) LIKE LOWER(?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, "%" + name + "%");
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Person person = new Person();
                        person.id = rs.getLong("id");
                        person.name = rs.getString("name");
                        persons.add(person);
                    }
                }
            }
        }
        return persons;
    }
}
