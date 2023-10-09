package battleship;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BattleField battleField1 = new BattleField();
        BattleField battleField2 = new BattleField();
        System.out.println("Player 1, place your ships on the game field");
        System.out.println();
        System.out.println(battleField1);


        try (Scanner kb = new Scanner(System.in)) {

            initializeBattlefield(battleField1, kb);

            System.out.println("Press Enter and pass the move to another player");
            kb.nextLine();
            System.out.println("...");

            System.out.println("Player 2, place your ships to the game field");
            System.out.println();
            System.out.println(battleField2);
            System.out.println();

            initializeBattlefield(battleField2, kb);

            System.out.println("Press Enter and pass the move to another player");
            kb.nextLine();
            System.out.println("...");


            do {
                hitShip(battleField2, battleField1, kb, "Player1");

                hitShip(battleField1, battleField2, kb, "Player2");

            } while (battleField1.notSankAllShips() || battleField2.notSankAllShips());

        }
    }

    private static void hitShip(BattleField battleField1, BattleField battleField2, Scanner kb, String playerName) {

        battleField1.setBattleState(BattleState.INVISIBLE);
        battleField2.setBattleState(BattleState.VISIBLE);
        System.out.println();
        System.out.println(battleField1);
        System.out.println("---------------------");
        System.out.println(battleField2);

        System.out.println();
        System.out.println(playerName + ", it's your turn:");

        System.out.println();

        boolean firingSuccessful = true;
        do {
            if (!firingSuccessful) {
                System.out.println();
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                System.out.println();
            }
            String firingPosition = kb.nextLine().trim();
            System.out.println();
            firingSuccessful = battleField1.attackCell(firingPosition);
        } while (!firingSuccessful);
        System.out.println("Press Enter and pass the move to another player");
        kb.nextLine();
        System.out.println("...");
    }

    private static void initializeBattlefield(BattleField battleField, Scanner kb) {
        String[] input;
        System.out.println();
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells eg - B3 B7):");
        System.out.println();
        do {
            input = kb.nextLine().trim().split("\\s+");
        } while (!battleField.addAircraftCarrier(input[0], input[1]));
        System.out.println();
        System.out.println(battleField);

        System.out.println();
        System.out.println("Enter the coordinates of the Battleship (4 cells eg - A1 D1):");
        System.out.println();
        do {
            input = kb.nextLine().trim().split("\\s+");
        } while (!battleField.addBattleShip(input[0], input[1]));
        System.out.println();
        System.out.println(battleField);

        System.out.println();
        System.out.println("Enter the coordinates of the Submarine (3 cells):");
        System.out.println();
        do {
            input = kb.nextLine().trim().split("\\s+");
        } while (!battleField.addSubmarine(input[0], input[1]));
        System.out.println();
        System.out.println(battleField);

        System.out.println();
        System.out.println("Enter the coordinates of the Cruiser (3 cells):");
        System.out.println();
        do {
            input = kb.nextLine().trim().split("\\s+");
        } while (!battleField.addCruiser(input[0], input[1]));
        System.out.println();
        System.out.println(battleField);

        System.out.println();
        System.out.println("Enter the coordinates of the Destroyer (2 cells):");
        System.out.println();
        do {
            input = kb.nextLine().trim().split("\\s+");
        } while (!battleField.addDestroyer(input[0], input[1]));
        System.out.println();
        System.out.println(battleField);

        System.out.println();
    }
}
