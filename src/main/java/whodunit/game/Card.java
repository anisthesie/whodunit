package whodunit.game;

public class Card {

    private final Type type;
    private final String value;

    public Card(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public enum Type {
        Location, Suspect, Weapon
    }
}
