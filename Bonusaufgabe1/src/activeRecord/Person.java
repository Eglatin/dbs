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
    private String sex;

    // Getter and Setter
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    // Insert method
    public void insert() throws SQLException {
        if (id != null) {
            throw new IllegalStateException("Object has already been inserted");
        }
        try (Connection conn = aufgabe1.getConnection()) {
            String sqlInsert = "INSERT INTO Person (Name, Sex) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, name);
                stmt.setString(2, sex);
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                }
            }
        }
    }

    // Update method
    public void update() throws SQLException {
        if (id == null) {
            throw new IllegalStateException("Object has not been inserted");
        }
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "UPDATE Person SET Name = ?, Sex = ? WHERE PersonID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, sex);
                stmt.setLong(3, id);
                stmt.executeUpdate();
            }
        }
    }

    // Delete method
    public void delete() throws SQLException {
        if (id == null) {
            throw new IllegalStateException("Object has not been inserted");
        }
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "DELETE FROM Person WHERE PersonID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }
        }
    }

    // FindById method
    public static Person findById(long id) throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "SELECT Name, Sex FROM Person WHERE PersonID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Person person = new Person();
                        person.id = id;
                        person.setName(rs.getString("Name"));
                        person.setSex(rs.getString("Sex"));
                        return person;
                    }
                }
            }
        }
        return null;
    }

    // FindAll method
    public static List<Person> findAll() throws SQLException {
        List<Person> personList = new ArrayList<>();
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "SELECT PersonID, Name, Sex FROM Person";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Person person = new Person();
                    person.id = rs.getLong("PersonID");
                    person.setName(rs.getString("Name"));
                    person.setSex(rs.getString("Sex"));
                    personList.add(person);
                }
            }
        }
        return personList;
    }
}
