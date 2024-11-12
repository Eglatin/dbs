package dtos;

/**
 * Data Transfer Object (DTO) für Objekte der Klasse Character.
 * Diese Klasse dient zur Übertragung von Charakter-Daten zwischen der GUI und der Geschäftslogik.
 * Enthält alle relevanten Informationen eines Charakters: den Namen des Charakters, Alias und den Darsteller.
 */
public class CharacterDTO {
    private String character; // Name des Charakters
    private String alias;     // Alias des Charakters
    private String player;    // Name des Darstellers

    // Standardkonstruktor
    public CharacterDTO() {}

    // Kopierkonstruktor: Erzeugt eine Kopie eines anderen CharacterDTO-Objekts
    public CharacterDTO(CharacterDTO that) {
        this.character = that.character;
        this.alias = that.alias;
        this.player = that.player;
    }

    // Getter für den Charakternamen
    public String getCharacter() {
        return character;
    }

    // Setter für den Charakternamen
    public void setCharacter(String character) {
        this.character = character;
    }

    // Getter für den Alias des Charakters
    public String getAlias() {
        return alias;
    }

    // Setter für den Alias des Charakters
    public void setAlias(String alias) {
        this.alias = alias;
    }

    // Getter für den Namen des Darstellers
    public String getPlayer() {
        return player;
    }

    // Setter für den Namen des Darstellers
    public void setPlayer(String player) {
        this.player = player;
    }

    // Überschreibt die toString-Methode, um die Charakterinformationen als Zeichenkette darzustellen
    @Override
    public String toString() {
        return "CharacterDTO [character=" + character + ", alias=" + alias + ", player=" + player + "]";
    }
}




