package dtos;

/**
 * Die Klasse `CharacterDTO` dient als Data Transfer Object (DTO) für eine Filmfigur.
 * Diese Klasse wird verwendet, um die Daten einer Figur zwischen der GUI und der Geschäftslogik zu übertragen.
 * Sie enthält alle relevanten Informationen zur Figur, einschließlich ihres Namens, ihres Alias und des Darstellers.
 */
public class CharacterDTO {
    
    // Der Name der Figur, z.B. "Luke Skywalker"
    private String character;
    
    // Der Alias oder Spitzname der Figur, z.B. "Jedi-Ritter"
    private String alias;
    
    // Der Name des Darstellers, der die Figur spielt, z.B. "Mark Hamill"
    private String player;

    /**
     * Standardkonstruktor.
     * Erzeugt ein neues, leeres `CharacterDTO`-Objekt.
     */
    public CharacterDTO() {}

    /**
     * Kopierkonstruktor.
     * Erzeugt eine Kopie eines anderen `CharacterDTO`-Objekts.
     * @param that Das `CharacterDTO`-Objekt, das kopiert werden soll.
     */
    public CharacterDTO(CharacterDTO that) {
        this.character = that.character;
        this.alias = that.alias;
        this.player = that.player;
    }

    // Getter- und Setter-Methoden für die Attribute der Klasse

    /**
     * Gibt den Namen der Figur zurück.
     * @return Name der Figur als String
     */
    public String getCharacter() {
        return character;
    }

    /**
     * Setzt den Namen der Figur.
     * @param character Der Name der Figur als String, darf nicht null sein.
     */
    public void setCharacter(String character) {
        this.character = character;
    }

    /**
     * Gibt den Alias der Figur zurück.
     * @return Alias der Figur als String
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Setzt den Alias der Figur.
     * @param alias Der Alias der Figur als String.
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Gibt den Namen des Darstellers zurück, der die Figur spielt.
     * @return Name des Darstellers als String
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Setzt den Namen des Darstellers, der die Figur spielt.
     * @param player Der Name des Darstellers als String.
     */
    public void setPlayer(String player) {
        this.player = player;
    }

    /**
     * Gibt eine textuelle Darstellung der Figur zurück.
     * Diese Methode wird typischerweise verwendet, um die Informationen der Figur 
     * in einer benutzerfreundlichen Form anzuzeigen.
     * @return Eine Zeichenkette, die den Namen, Alias und Darsteller der Figur enthält.
     */
    @Override
    public String toString() {
        return "CharacterDTO [character=" + character + ", alias=" + alias + ", player=" + player + "]";
    }
}
