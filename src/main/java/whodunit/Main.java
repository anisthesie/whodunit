package whodunit;

import whodunit.game.Card;
import whodunit.game.CardType;
import whodunit.game.Model;
import whodunit.players.ComputerPlayer;
import whodunit.players.HumanPlayer;
import whodunit.players.IPlayer;

import java.util.*;

public class Main {

    public static final List<Card> allLocations = Arrays.asList(
            new Card(CardType.Location, "hallway"), new Card(CardType.Location, "washroom"),
            new Card(CardType.Location, "bedroom"), new Card(CardType.Location, "garden"),
            new Card(CardType.Location, "kitchen"), new Card(CardType.Location, "terrace"),
            new Card(CardType.Location, "living room"), new Card(CardType.Location, "garage")
    );
    public static final List<Card> allSuspects = Arrays.asList(
            new Card(CardType.Suspect, "jack"), new Card(CardType.Suspect, "donald"),
            new Card(CardType.Suspect, "daniel"), new Card(CardType.Suspect, "elon"),
            new Card(CardType.Suspect, "john"), new Card(CardType.Suspect, "sarah"),
            new Card(CardType.Suspect, "suzan"), new Card(CardType.Suspect, "marie")
    );
    public static final List<Card> allWeapons = Arrays.asList(
            new Card(CardType.Weapon, "gun"), new Card(CardType.Weapon, "sword"),
            new Card(CardType.Weapon, "knife"), new Card(CardType.Weapon, "axe"),
            new Card(CardType.Weapon, "taser"), new Card(CardType.Weapon, "poison"),
            new Card(CardType.Weapon, "archery"), new Card(CardType.Weapon, "baseball bat")
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
