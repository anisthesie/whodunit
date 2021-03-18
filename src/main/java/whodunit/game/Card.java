package whodunit.game;

public class Card {

    private final CardType type;
    private final String value;

    public Card(CardType type, String value) {
        this.type = type;
        this.value = value;
    }

    public CardType getType() {
        return type;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

}
