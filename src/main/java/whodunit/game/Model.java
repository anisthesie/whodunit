package whodunit.game;

import whodunit.players.IPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Model {

    private Guess solution;
    private List<IPlayer> players;
    private List<IPlayer> activePlayers = new ArrayList<>();
    private List<IPlayer> removedPlayers = new ArrayList<>();
    private List<Card>[] cards;
    private boolean gameActive = true;

    // Cards is an Array of 3 Lists.
    // cards[0] returns list of suspects
    // cards[1] returns list of places
    // cards[2] returns list of weapons
    // The order is important
    public Model(List<IPlayer> players, List<Card>... cards) {
        this.players = players;
        activePlayers.addAll(this.players);
        this.cards = cards;
    }

    public void startGame() {
        System.out.println("Choosing the answer cards...");
        Random random = new Random();
        Card where = getPlaces().get(random.nextInt(getPlaces().size()));
        Card who = getSuspects().get(random.nextInt(getSuspects().size()));
        Card how = getWeapons().get(random.nextInt(getWeapons().size()));
        getPlaces().remove(where);
        getSuspects().remove(who);
        getWeapons().remove(how);
        solution = new Guess(who, where, how, false);
        System.out.println("Answer cards selected.\n");

        ArrayList<Card> shuffledCards = new ArrayList<>();
        shuffledCards.addAll(getSuspects());
        shuffledCards.addAll(getWeapons());
        shuffledCards.addAll(getPlaces());
        Collections.shuffle(shuffledCards);

        System.out.println("Dealing cards to the players...");
        for (int i = 0; i < shuffledCards.size(); i++) {
            final IPlayer player = getPlayer((i + 1) % players.size());
            player.setCard(shuffledCards.get(i));
        }
        System.out.println("\nAll players have received their cards.");

        startLoop();

    }

    private void startLoop() {
        int round = -1;
        while (gameActive) {
            int index = (round + 1) % activePlayers.size();
            round++;
            IPlayer activePlayer = activePlayers.get(index);
            System.out.println("\nPlayer "+activePlayer.getIndex()+"'s turn.\n");
            Guess guess = activePlayer.getGuess();
            if (guess.isAccusation()) {
                if (isAccusationCorrect(guess)) {
                    System.out.println("\nPlayer " + activePlayer.getIndex() + " has won the game.");
                    System.out.println("The solution was: " + guess.getWho() + ", " + guess.getWhere() + ", " + guess.getHow());
                    gameActive = false;
                    break;
                } else {
                    System.out.println("Player " + activePlayer.getIndex() + " made a false accusation and was kicked.");
                    activePlayers.remove(activePlayer);
                    removedPlayers.add(activePlayer);
                    if (activePlayers.size() == 1) {
                        System.out.println("Player " + activePlayers.get(0).getIndex() + " is the last player remaining and has won the game.");
                        gameActive = false;
                        break;
                    }
                }
            } else {

                boolean somebodyAnswered = false;
                for (int j = activePlayer.getIndex(); j < players.size() + activePlayer.getIndex() - 1; j++) {
                    IPlayer nextPlayer = players.get((j + 1) % players.size());
                    Card answer = nextPlayer.canAnswer(guess, activePlayer);
                    if (answer != null) {
                        activePlayer.receiveInfo(nextPlayer, answer);
                        somebodyAnswered = true;
                    }
                }
                if (!somebodyAnswered)
                    activePlayer.receiveInfo(null, null);

            }
        }

    }

    public boolean isAccusationCorrect(Guess guess) {
        return guess.equals(solution);
    }

    public List<Card> getSuspects() {
        return cards[0];
    }

    public List<Card> getPlaces() {
        return cards[1];
    }

    public List<Card> getWeapons() {
        return cards[2];
    }

    public IPlayer getPlayer(int index) {
        return players.get(index);
    }

}
