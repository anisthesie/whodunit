package whodunit.players;

import whodunit.game.Card;
import whodunit.game.Guess;

import java.util.ArrayList;

public interface IPlayer {

    void setUp(int numPlayers, int index, ArrayList<Card> ppl,
               ArrayList<Card> places, ArrayList<Card> weapons);

    void setCard(Card c);

    int getIndex();

    Card canAnswer(Guess g, IPlayer ip);

    Guess getGuess();

    void receiveInfo(IPlayer ip, Card c);

}
