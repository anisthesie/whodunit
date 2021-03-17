package whodunit;

import whodunit.game.Card;
import whodunit.game.Model;
import whodunit.players.ComputerPlayer;
import whodunit.players.HumanPlayer;
import whodunit.players.IPlayer;

import java.util.*;

public class Main {

    private static final List<Card> allLocations = Arrays.asList(
            new Card(Card.Type.Location, "hallway"), new Card(Card.Type.Location, "washroom"),
            new Card(Card.Type.Location, "bedroom"), new Card(Card.Type.Location, "garden"),
            new Card(Card.Type.Location, "kitchen"), new Card(Card.Type.Location, "terrace"),
            new Card(Card.Type.Location, "living room"), new Card(Card.Type.Location, "garage")
    );
    private static final List<Card> allSuspects = Arrays.asList(
            new Card(Card.Type.Suspect, "jack"), new Card(Card.Type.Suspect, "donald"),
            new Card(Card.Type.Suspect, "daniel"), new Card(Card.Type.Suspect, "elon"),
            new Card(Card.Type.Suspect, "john"), new Card(Card.Type.Suspect, "sarah"),
            new Card(Card.Type.Suspect, "suzan"), new Card(Card.Type.Suspect, "marie")
    );
    private static final List<Card> allWeapons = Arrays.asList(
            new Card(Card.Type.Weapon, "gun"), new Card(Card.Type.Weapon, "sword"),
            new Card(Card.Type.Weapon, "knife"), new Card(Card.Type.Weapon, "axe"),
            new Card(Card.Type.Weapon, "taser"), new Card(Card.Type.Weapon, "poison"),
            new Card(Card.Type.Weapon, "archery"), new Card(Card.Type.Weapon, "baseball bat")
    );

    public static void main(String[] args) {
        System.out.println("Welcome to the WhoDunIt game.\n");
        System.out.println("Please specify the number of computer players: ");
        int playerCount = 0;
        while (playerCount <= 0) {
            playerCount = getInputInt();
            if (playerCount <= 0)
                System.out.println("Invalid number, please try again.");
        }
        int locationsCount = 0, weaponsCount = 0, suspectsCount = 0;

        System.out.println("Please specify the number of Places: ");
        while (locationsCount <= 0 || locationsCount > 8) {
            locationsCount = getInputInt();
            if (locationsCount <= 0 || locationsCount > 8)
                System.out.println("Invalid number, must be between 0-8. Try again.");
        }
        System.out.println("Please specify the number of Weapons: ");
        while (weaponsCount <= 0 || weaponsCount > 8) {
            weaponsCount = getInputInt();
            if (weaponsCount <= 0 || weaponsCount > 8)
                System.out.println("Invalid number, must be between 0-8. Try again.");
        }
        System.out.println("Please specify the number of Suspects: ");
        while (suspectsCount <= 0 || suspectsCount > 8) {
            suspectsCount = getInputInt();
            if (suspectsCount <= 0 || suspectsCount > 8)
                System.out.println("Invalid number, must be between 0-8. Try again.");
        }

        Collections.shuffle(allLocations);
        Collections.shuffle(allSuspects);
        Collections.shuffle(allWeapons);
        final ArrayList<Card> weapons = new ArrayList<>(),
                suspects = new ArrayList<>(),
                locations = new ArrayList<>();

        for (int wc = 0; wc < weaponsCount; wc++) {
            weapons.add(allWeapons.get(wc));
        }
        for (int sc = 0; sc < suspectsCount; sc++) {
            suspects.add(allSuspects.get(sc));
        }
        for (int lc = 0; lc < locationsCount; lc++) {
            locations.add(allLocations.get(lc));
        }

        final List<IPlayer> players = new ArrayList<>();
        final IPlayer humanPlayer = new HumanPlayer();
        humanPlayer.setUp(playerCount + 1, 0, suspects, locations, weapons);
        players.add(humanPlayer);
        for (int p = 1; p < playerCount + 1; p++) {
            final IPlayer computerPlayer = new ComputerPlayer();
            computerPlayer.setUp(playerCount + 1, p, suspects, locations, weapons);
            players.add(computerPlayer);
        }

        final Model model = new Model(players, suspects, locations, weapons);
        model.startGame();

    }

    private static final Scanner console = new Scanner(System.in);

    public static String getInput() {
        return console.nextLine();
    }

    public static int getInputInt() {
        try {
            return new Integer(getInput());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
