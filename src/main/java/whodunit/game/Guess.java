package whodunit.game;

import java.util.Objects;

public class Guess {

    private final Card who, where, how;
    private final boolean accusation;

    public Guess(Card who, Card where, Card how, boolean accusation) {
        this.who = who;
        this.where = where;
        this.how = how;
        this.accusation = accusation;
    }

    public boolean isAccusation() {
        return accusation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guess guess = (Guess) o;
        return guess.getWho().getValue().equalsIgnoreCase(getWho().getValue()) &&
                guess.getWhere().getValue().equalsIgnoreCase(getWhere().getValue()) &&
                guess.getHow().getValue().equalsIgnoreCase(getHow().getValue());
    }

    public Card getWho() {
        return who;
    }

    public Card getWhere() {
        return where;
    }

    public Card getHow() {
        return how;
    }
}
