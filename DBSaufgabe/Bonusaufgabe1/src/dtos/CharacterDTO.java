package dtos;

/**
 * Data Transfer Object (DTO) für Objekte der Klasse Character.
 * Enthält alle benötigten Informationen für die Kommunikation GUI <-> Geschäftslogik.
 */
public class CharacterDTO {
    private String character;
    private String alias;
    private String player;

    public CharacterDTO() {}

    public CharacterDTO(CharacterDTO that) {
        this.character = that.character;
        this.alias = that.alias;
        this.player = that.player;
    }

    // Getter und Setter
    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "CharacterDTO [character=" + character + ", alias=" + alias + ", player=" + player + "]";
    }
}
