package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import aufgabe41.aufgabe1;

public class Character {
    private Long id;
    private String name;
    private String alias;
    private Integer position;

    public Character() {}

    public Character(String name, String alias, Integer position) {
        this.name = name;
        this.alias = alias;
        this.position = position;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    // Insert-Methode
    public void insert() throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "INSERT INTO Character (name, alias, position) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, name);
                stmt.setString(2, alias);
                stmt.setInt(3, position);
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
            String sql = "UPDATE Character SET name = ?, alias = ?, position = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, alias);
                stmt.setInt(3, position);
                stmt.setLong(4, id);
                stmt.executeUpdate();
            }
        }
    }

    // Delete-Methode
    public void delete() throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "DELETE FROM Character WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }
        }
    }

    // FindById-Methode
    public static Character findById(long id) throws SQLException {
        try (Connection conn = aufgabe1.getConnection()) {
            String sql = "SELECT * FROM Character WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Character character = new Character();
                        character.id = rs.getLong("id");
                        character.name = rs.getString("name");
                        character.alias = rs.getString("alias");
                        character.position = rs.getInt("position");
                        return character;
                    }
                }
            }
        }
        return null;
    }
}
