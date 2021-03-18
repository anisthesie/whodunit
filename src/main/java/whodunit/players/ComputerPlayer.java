package whodunit.players;

import whodunit.game.Card;
import whodunit.game.Guess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComputerPlayer implements IPlayer {

    private final List<Card> cards = new ArrayList<>();
    private ArrayList<Card> suspects, places, weapons;
    int index, numPlayers;
    private ArrayList<Card> currentSuspects = new ArrayList<>(),
            currentPlaces = new ArrayList<>(),
            currentWeapons = new ArrayList<>();
    private int it = 0;

    @Override
    public void setUp(int numPlayers, int index, ArrayList<Card> ppl, ArrayList<Card> places, ArrayList<Card> weapons) {
        this.suspects = ppl;
        this.places = places;
        this.weapons = weapons;
        this.index = index;
        this.numPlayers = numPlayers;

        this.currentSuspects.addAll(suspects);
        this.currentPlaces.addAll(places);
        this.currentWeapons.addAll(weapons);

        Collections.shuffle(currentPlaces);
        Collections.shuffle(currentWeapons);
        Collections.shuffle(currentSuspects);

    }

    @Override
    public void setCard(Card c) {
        cards.add(c);
        ignoreCard(c);
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Card canAnswer(Guess g, IPlayer ip) {
        if (hasCard(g.getWhere().getValue()))
            return g.getWhere();
        if (hasCard(g.getHow().getValue()))
            return g.getHow();
        if (hasCard(g.getWho().getValue()))
            return g.getWho();
        return null;
    }

    @Override
    public Guess getGuess() {
        Card where = currentPlaces.get(it == 0 ? 0 : (it + 1) % currentPlaces.size());
        Card who = currentSuspects.get(it == 0 ? 0 : (it + 1) % currentSuspects.size());
        Card how = currentWeapons.get(it == 0 ? 0 : (it + 1) % currentWeapons.size());
        boolean accusation = (currentWeapons.size() + currentSuspects.size() + currentPlaces.size()) == 3;
        it++;
        return new Guess(who, where, how, accusation);
    }

    @Override
    public void receiveInfo(IPlayer ip, Card c) {
        if (ip == null || c == null)
            return;
        ignoreCard(c);
    }

    private void ignoreCard(Card c) {
        switch (c.getType()) {
            case Weapon:
                currentWeapons.removeIf(w -> w.getValue().equalsIgnoreCase(c.getValue()));
                break;
            case Suspect:
                currentSuspects.removeIf(w -> w.getValue().equalsIgnoreCase(c.getValue()));
                break;
            case Location:
                currentPlaces.removeIf(w -> w.getValue().equalsIgnoreCase(c.getValue()));
                break;
        }
    }

    private Card getCard(String card) {
        Card retCard = null;
        for (Card c : cards) {
            if (c.getValue().equalsIgnoreCase(card)) {
                retCard = c;
                break;
            }
        }
        return retCard;
    }

    private boolean hasCard(String card) {
        return getCard(card) != null;
    }

    public List<Card> getCards() {
        return cards;
    }
}
