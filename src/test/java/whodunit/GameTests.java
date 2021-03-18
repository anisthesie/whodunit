package whodunit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import whodunit.game.Card;
import whodunit.game.Guess;
import whodunit.game.Model;
import whodunit.players.ComputerPlayer;
import whodunit.players.IPlayer;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {


    Model game;

    @BeforeEach
    private void test1() {
        IPlayer player1 = new ComputerPlayer(), player2 = new ComputerPlayer(), player3 = new ComputerPlayer(), player4 = new ComputerPlayer();
        player1.setUp(4, 0, new ArrayList<>(Main.allSuspects),
                new ArrayList<>(Main.allLocations), new ArrayList<>(Main.allWeapons));
        player2.setUp(4, 1, new ArrayList<>(Main.allSuspects),
                new ArrayList<>(Main.allLocations), new ArrayList<>(Main.allWeapons));
        player3.setUp(4, 2, new ArrayList<>(Main.allSuspects),
                new ArrayList<>(Main.allLocations), new ArrayList<>(Main.allWeapons));
        player4.setUp(4, 3, new ArrayList<>(Main.allSuspects),
                new ArrayList<>(Main.allLocations), new ArrayList<>(Main.allWeapons));

        game = new Model(Arrays.asList(player1, player2, player3, player4), new ArrayList<>(Main.allSuspects),
                new ArrayList<>(Main.allLocations), new ArrayList<>(Main.allWeapons));
        game.startGame();
    }

    @Test
    void test2() {
        ComputerPlayer player = (ComputerPlayer) game.getPlayer(0);
        player.getCards().clear();
        assertNull(player.canAnswer(new Guess(Main.allSuspects.get(0), Main.allLocations.get(0), Main.allWeapons.get(0), false), game.getPlayer(1)));
    }

    @Test
    void test3() {
        ComputerPlayer player = (ComputerPlayer) game.getPlayer(0);
        player.getCards().clear();
        player.getCards().add(Main.allLocations.get(1));

        Guess guess = new Guess(Main.allSuspects.get(0), Main.allLocations.get(1), Main.allWeapons.get(0), false);
        Card canAnswer = player.canAnswer(guess, game.getPlayer(1));
        assertEquals(guess.getWhere(), canAnswer);
    }

    @Test
    void test4() {
        ComputerPlayer player = (ComputerPlayer) game.getPlayer(0);
        player.getCards().clear();
        player.getCards().add(Main.allLocations.get(0));
        player.getCards().add(Main.allSuspects.get(0));

        Guess guess = new Guess(Main.allSuspects.get(0), Main.allLocations.get(0), Main.allWeapons.get(0), false);
        Card canAnswer = player.canAnswer(guess, game.getPlayer(1));
        assertTrue(guess.getWhere() == canAnswer || guess.getWho() == canAnswer);
    }

    @Test
    void test5() {
        ComputerPlayer player = (ComputerPlayer) game.getPlayer(0);
        player.getCards().clear();

        player.receiveInfo(game.getPlayer(1), Main.allSuspects.get(0));

        Guess getGuess = player.getGuess();
        assertNotEquals(Main.allSuspects.get(0), getGuess.getWho());
    }

    @Test
    void test6() {
        ComputerPlayer player = (ComputerPlayer) game.getPlayer(0);
        Card notGivenWho = game.getSolution().getWho();
        Card notGivenWhere = game.getSolution().getWhere();
        Card notGivenHow = game.getSolution().getHow();

        for (Card card : Main.allSuspects) { // Giving all cards except notGivenWho
            if (card != notGivenWho) player.receiveInfo(game.getPlayer(1), card);
        }
        for (Card card : Main.allLocations) { // Same
            if (card != notGivenWhere) player.receiveInfo(game.getPlayer(1), card);
        }
        for (Card card : Main.allWeapons) { // Same
            if (card != notGivenHow) player.receiveInfo(game.getPlayer(1), card);
        }

        Guess getGuess = player.getGuess();
        assertTrue(game.isAccusationCorrect(getGuess) && getGuess.isAccusation());
    }

    @Test
    void test7() {
        ComputerPlayer player = (ComputerPlayer) game.getPlayer(0);
        Card notGivenWho = game.getSolution().getWho();
        Card notGivenWhere = game.getSolution().getWhere();
        Card notGivenHow = game.getSolution().getHow();

        for (Card card : Main.allLocations) {
            if (card != notGivenWhere) player.receiveInfo(game.getPlayer(1), card);
        }
        for (Card card : Main.allWeapons) {
            if (card != notGivenHow) player.receiveInfo(game.getPlayer(1), card);
        }

        Guess getGuess1 = player.getGuess();
        assertFalse(getGuess1.isAccusation());

        for (Card card : Main.allSuspects) {
            if (card != notGivenWho) player.receiveInfo(game.getPlayer(1), card);
        }

        Guess getGuess2 = player.getGuess();
        assertTrue(game.isAccusationCorrect(getGuess2) && getGuess2.isAccusation());
    }

}
