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
        Card where = currentPlaces.get(0);
        Card who = currentSuspects.get(0);
        Card how = currentWeapons.get(0);
        boolean accusation = (currentWeapons.size() + currentSuspects.size() + currentPlaces.size()) == 3;
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
                currentWeapons.remove(c);
                break;
            case Suspect:
                currentSuspects.remove(c);
                break;
            case Location:
                currentPlaces.remove(c);
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

}
