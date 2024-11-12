package dtos;

/**
 *CharacterDTO dient als Data Transfer Object (DTO) für eine Filmfigur
 * klasse wird verwendet, um daten einer Figur zwischen gui und der Geschäftslgik zu übertragen
 *enthält alle relevanten Informationen zur Figur, einschließlich ihres Namens, ihres Alias und des Darstellers.
 */
public class CharacterDTO {
    
    //name der Figur z.B. Luke Skywalker
    private String character;
    
    //alias o. Spitzname der Figur
    private String alias;
    
    //name des Darstellers, der die Figur spielt 
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
     *setzt den Namen des Darstellers, der die Figur spielt
     * @param player Der Name des Darstellers als String
     */
    public void setPlayer(String player) {
        this.player = player;
    }

    /**
     *gibt eine textuelle Darstellung der Figur zurück
     *diese Methode wird typischerweise verwendet, um die Informationen der Figur 
     * in einer benutzerfreundlichen Form anzuzeigen
     * @return Eine Zeichenkette, die den Namen, Alias und Darsteller der Figur enthält
     */
    @Override
    public String toString() {
        return "CharacterDTO [character=" + character + ", alias=" + alias + ", player=" + player + "]";
    }
}
