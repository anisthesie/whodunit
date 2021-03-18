package whodunit.players;

import whodunit.game.Card;
import whodunit.game.CardType;
import whodunit.game.Guess;

import java.util.ArrayList;
import java.util.List;

import static whodunit.Main.getInput;

public class HumanPlayer implements IPlayer {

    private final List<Card> cards = new ArrayList<>();
    private ArrayList<Card> suspects = new ArrayList<>(), places = new ArrayList<>(), weapons = new ArrayList<>();
    int index, numPlayers;

    @Override
    public void setUp(int numPlayers, int index, ArrayList<Card> ppl, ArrayList<Card> places, ArrayList<Card> weapons) {
        this.suspects.addAll(ppl);
        this.places.addAll(places);
        this.weapons.addAll(weapons);
        this.index = index;
        this.numPlayers = numPlayers;

        System.out.println("\nHere are the names of all the suspects: ");
        for (int i = 0; i < suspects.size(); i++) {
            System.out.print(suspects.get(i).getValue() + (i == suspects.size() - 1 ? "\n" : ", "));
        }

        System.out.println("\nHere are the name of all the locations: ");
        for (int i = 0; i < places.size(); i++) {
            System.out.print(places.get(i).getValue() + (i == places.size() - 1 ? "\n" : ", "));
        }
        System.out.println("\nHere are the name of all the weapons: ");
        for (int i = 0; i < weapons.size(); i++) {
            System.out.print(weapons.get(i).getValue() + (i == weapons.size() - 1 ? "\n" : ", "));
        }
        System.out.println();
    }

    @Override
    public void setCard(Card c) {
        cards.add(c);
        System.out.println("You have been dealt a card.. " + c.getType() + " : " + c.getValue());
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Card canAnswer(Guess g, IPlayer ip) {
        System.out.println("Player " + ip.getIndex() + " has made this suggestion: " + g.getWho() + ", " + g.getHow() + ", " + g.getWhere());
        System.out.println("Can you answer him? (Y/N): ");
        String input = getInput();
        while (!(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n"))) {
            input = getInput();
            if (!(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n")))
                System.out.println("Invalid input. Please write Y for yes, N for no.");
        }
        if (input.equalsIgnoreCase("y")) {
            System.out.println("What card would you like to show him ? : ");
            String card = "";
            while (!hasCard(card)) {
                card = getInput();
                if (!hasCard(card)) {
                    System.out.println("You don't have that card. Your cards are : ");
                    for (int i = 0; i < cards.size(); i++) {
                        System.out.print(cards.get(i).getValue() + (i == cards.size() - 1 ? "" : ", "));
                    }
                    System.out.println("\nPlease try again.");
                }
            }
            System.out.println("You showed him the card: " + card);
            return getCard(card);
        } else System.out.println("Not showing any card.");
        return null;
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


    @Override
    public Guess getGuess() {
        System.out.println("It is your turn to make a guess.");
        System.out.println("Which person do you suggest? : ");
        Card suspect = null;
        while (suspect == null) {
            suspect = getFromAllCards(getInput(), CardType.Suspect);
            if (suspect == null) {
                System.out.println("Invalid person. The persons are: ");
                for (int i = 0; i < suspects.size(); i++)
                    System.out.print(suspects.get(i).getValue() + (i == suspects.size() - 1 ? "" : ", "));
                System.out.println("\nPlease try again.");
            }
        }
        System.out.println("Which place do you suggest? : ");
        Card place = null;
        while (place == null) {
            place = getFromAllCards(getInput(), CardType.Location);
            if (place == null) {
                System.out.println("Invalid location. The locations are: ");
                for (int i = 0; i < places.size(); i++)
                    System.out.print(places.get(i).getValue() + (i == places.size() - 1 ? "" : ", "));
                System.out.println("\nPlease try again.");
            }
        }
        System.out.println("Which weapon do you suggest? : ");
        Card weapon = null;
        while (weapon == null) {
            weapon = getFromAllCards(getInput(), CardType.Weapon);
            if (weapon == null) {
                System.out.println("Invalid weapon. The persons are: ");
                for (int i = 0; i < weapons.size(); i++)
                    System.out.print(weapons.get(i) + (i == weapons.size() - 1 ? "" : ", "));
                System.out.println("\nPlease try again.");
            }
        }

        System.out.println("Is this an accusation ? (Y/N) : ");
        String yesorno = "";
        while (!(yesorno.equalsIgnoreCase("y") || yesorno.equalsIgnoreCase("n"))) {
            yesorno = getInput();
            if (!(yesorno.equalsIgnoreCase("y") || yesorno.equalsIgnoreCase("n")))
                System.out.println("Invalid input. Please write Y for yes, N for no.");
        }
        boolean accusation = yesorno.equalsIgnoreCase("y");
        return new Guess(suspect, place, weapon, accusation);
    }

    private Card getFromAllCards(String value, CardType type) {
        switch (type) {
            case Weapon:
                for (Card c : weapons) {
                    if (c.getValue().equalsIgnoreCase(value))
                        return c;
                }
                break;
            case Suspect:
                for (Card c : suspects) {
                    if (c.getValue().equalsIgnoreCase(value))
                        return c;
                }
                break;
            case Location:
                for (Card c : places) {
                    if (c.getValue().equalsIgnoreCase(value))
                        return c;
                }
                break;
        }
        return null;
    }

    @Override
    public void receiveInfo(IPlayer ip, Card c) {
        if(ip == null || c == null){
            System.out.println("No one could refute your suggestion.");
            return;
        }
        System.out.println("Player "+ ip.getIndex() +" refuted your suggestion by showing you "+ c.getValue() +".");
    }
}
