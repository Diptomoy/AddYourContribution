import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class LudoGameHandler {
	
	private static String[] colorList = { "Green", "Yellow", "Red", "Blue" };
	private static List<Player> playerList = new ArrayList<Player>();
	private static Player currentPlayer;
	private static LudoBoard ludoBoard;
	
	public static void main(String[] args) {
		
		/*
		 * 
		 *  initialize game by asking for the number of players
		 *  
		 */
		System.out.println("Welcome! Type in the number of players (2-4).");		
		boolean inputCorrect = false;
		Scanner scanner = null;
		
		while(!inputCorrect) {
			
			scanner = new Scanner(System.in);
			int number = 0;
			try {
				number = scanner.nextInt();
			} catch(Exception e) {
				//ignore
			}
			
			if(number>1 && number<5) {
				
				inputCorrect = true;
				
				for(int i=0; i<number; i++)
					playerList.add(new Player(colorList[i]));
				
			} else {
				
				System.out.println("Something went wrong. Type in the number of players (2-4).");
				
			}
			
		}
				
		for(int i=0; i<playerList.size(); i++)
			System.out.println(playerList.get(i) + " has joined the game.");
		
		/*
		 * 
		 * roll the dice to determine who goes first
		 * 
		 */
		
		System.out.println("The players have to roll the dice to " + 
							"determine who goes first. To roll a dice, "
							+ "type in \"r\".");
		boolean initComplete = false;
		int playerCounter = 0;
		
		while(!initComplete) {
				
			Player currentPlayer = playerList.get(playerCounter);
			System.out.println(currentPlayer + "'s turn.");
				
			scanner = new Scanner(System.in);
			String input = "a";
				
			try {
				input = scanner.next();
			} catch(Exception e) {
				// ignore
			}
				
			if(input.equals("r")) {
					
				currentPlayer.rollDice();
				System.out.println(currentPlayer + " has rolled " + currentPlayer.getNumberRolled());
					
			} else {
				
				System.out.println("Something went wrong. Type in \"r\".");
				continue;
				
			}
			
			if(++playerCounter==playerList.size())
				initComplete = true;
						
		}
		
		for(int i=0; i<playerList.size(); i++)
			System.out.println(playerList.get(i) + " has rolled " + playerList.get(i).getNumberRolled());
		
		List<Player> highestRollers = determineHighestRoller(playerList);
		
		/*
		 * 
		 * if there are multiple high rollers, do a loop
		 * to end up with just 1 highest roller
		 * 
		 */
		
		boolean onlyOneHighest = highestRollers.size()==1;
		
		while(!onlyOneHighest) {
			
			String playerNames = "";
			
			for(int i=0; i<highestRollers.size(); i++)
				playerNames += highestRollers.get(i) + ", ";
			
			System.out.println("There are multiple highest rollers. Players " +
								playerNames + "have to roll again. Remember, to " +
								"roll a dice, type in \"r\"");

			boolean rollsComplete = false;
			int rollerCounter = 0;
			
			while(!rollsComplete) {
					
				Player currentPlayer = highestRollers.get(rollerCounter);
				System.out.println(currentPlayer + "'s turn.");
					
				scanner = new Scanner(System.in);
				String input = "a";
					
				try {
					input = scanner.next();
				} catch(Exception e) {
					// ignore
				}
					
				if(input.equals("r")) {
						
					currentPlayer.rollDice();
					System.out.println(currentPlayer + " has rolled " + currentPlayer.getNumberRolled());
						
				} else {
					
					System.out.println("Something went wrong. Type in \"r\".");
					continue;
					
				}
				
				if(++rollerCounter==highestRollers.size())
					rollsComplete = true;
							
			}
						
			for(int i=0; i<highestRollers.size(); i++)
				System.out.println(highestRollers.get(i) + " has rolled " + highestRollers.get(i).getNumberRolled());
			
			highestRollers = determineHighestRoller(highestRollers);
			
			onlyOneHighest = highestRollers.size()==1;
			
		}
				
		currentPlayer = highestRollers.get(0);
		
		System.out.println(currentPlayer + " starts the game.");
		
		ludoBoard = new LudoBoard();
		for(int i=0; i<playerList.size(); i++)
			ludoBoard.initializePieces(playerList.get(i));
		
		runGame();
		
	}
	
	/*
	 * 
	 * Controls the game sequence
	 * 
	 */
	
	private static void runGame() {
		
		Scanner scanner = null;
		boolean gameCompleted = false;
		
		game: while(!gameCompleted) {
			
			ludoBoard.printBoard();
			
			System.out.println(currentPlayer + "'s turn. The only thing you can do is roll - \"r\".");
			
			boolean rollComplete = false;
			
			while(!rollComplete) {
										
				scanner = new Scanner(System.in);
				String input = "a";
					
				try {
					input = scanner.next();
				} catch(Exception e) {
					// ignore
				}
					
				if(input.equals("r")) {
						
					currentPlayer.rollDice();
					rollComplete = true;
						
				} else {
					
					System.out.println("Something went wrong. Type in \"r\".");
					continue;
					
				}
							
			}
			
			boolean movesArePossible = ludoBoard.movesArePossible(currentPlayer, currentPlayer.getNumberRolled());
			
			if(!movesArePossible) {
				
				System.out.println("There are no possible moves. Moving on...");
				setNextPlayer();
				continue;
				
			}
			
			System.out.println(currentPlayer + " has rolled " + currentPlayer.getNumberRolled() + 
					". Commands:\n" + 
					"\"t (piece number)\" without the brackets to take a piece out of the home circle;\n" +  //Type t 1 to move piece 1 out of home circle , t 2 for piece two and t 3 and so on (t(spacebar)(piece number))
					"\"m (piece number)\" without the brackets to move a piece."); //Type m 1 to move piece 1, m 2 to move piece 2, m 3 and so on (m(spacebar)(piece number))
			
			boolean turnComplete = false;
			
			while(!turnComplete) {
				
				scanner = new Scanner(System.in);
				
				String command = null;
				boolean commandSuccessful = false;
				
				try {
					command = scanner.next();
				} catch(Exception e) {
					System.out.println("Invalid command. Try again.");
					continue;
				}
				
				if(command.equals("t")) {
					
					// if did not roll 6, can't take a piece out
					if(!currentPlayer.hasRolledSix()) {
						System.out.println("Invalid move. Pieces can be taken out only " + 
								"when a 6 has been rolled.");
						continue;
					}
					
					int pieceNumber = 0;
					
					try {
						pieceNumber = scanner.nextInt() - 1;
					} catch(Exception e) {
						System.out.println("Invalid piece number provided. Try again.");
						continue;
					}
					
					if(pieceNumber<0 || pieceNumber>3) {
						
						System.out.println("Wrong piece number!");
						continue;
						
					}
					
					Piece piece = currentPlayer.getPiece(pieceNumber);
					
					commandSuccessful = ludoBoard.takePieceOut(piece);

				} else if(command.equals("m")) {
					
					int pieceNumber = 0;
					
					try {
						pieceNumber = scanner.nextInt() - 1;
					} catch(Exception e) {
						System.out.println("Invalid piece number provided. Try again.");
						continue;
					}
					
					if(pieceNumber<0 || pieceNumber>3) {
						
						System.out.println("Wrong piece number!");
						continue;
						
					}
					
					Piece piece = currentPlayer.getPiece(pieceNumber);
					int squareAmount = currentPlayer.getNumberRolled();
					
					commandSuccessful = ludoBoard.movePiece(piece, squareAmount);
					
				} else {
					
					System.out.println("Invalid command. Try again.");
					continue;
					
				}
				
				if(commandSuccessful) {
					
					if(currentPlayer.hasWon()) {
						
						System.out.println("Congratulations! " + currentPlayer + " has won the game!");
						break game;
						
					}
					
					if(currentPlayer.hasRolledSix()) {
						System.out.println(currentPlayer + " has rolled a six, meaning that " + 
								"they get another turn. Roll the dice.");
						continue game;
					}
					
					setNextPlayer();
					turnComplete = true;
					
				} else System.out.println("Move cannot be completed. Try something else.");
				
			}
			
		}
		
		scanner.close();
		
	}
	
	/*
	 * 
	 * Return type is list in case there are multiple
	 * high-rollers.
	 * 
	 */
	private static List<Player> determineHighestRoller(List<Player> playersWhoRolled) {
		
		List<Player> highRollers = new ArrayList<Player>();
		Player highestRoller = playersWhoRolled.get(0);
		boolean complete = false;
		int playerCounter = 1;
		
		while(!complete) {
			
			Player nextPlayer = playersWhoRolled.get(playerCounter++);
			
			int currentHighest = highestRoller.getNumberRolled();
			int nextPlayersRoll = nextPlayer.getNumberRolled();
			
			if(currentHighest>nextPlayersRoll) {
				
				// do nothing, highestRoller already points
				// to the highest rolling player
				
			} else if(currentHighest<nextPlayersRoll) {
				
				// assign next player as the highest roller
				highestRoller = nextPlayer;
				
				// clear the multiple roller list
				// because nextPlayer has rolled
				// more than both previous players
				highRollers.clear();
				
			} else if(currentHighest==nextPlayersRoll) {
				
				// might already contain if there are 3
				// high rollers
				if(!highRollers.contains(highestRoller))
					highRollers.add(highestRoller);
				if(!highRollers.contains(nextPlayer))
					highRollers.add(nextPlayer);
				
			}
			
			if(playerCounter==playersWhoRolled.size())
				complete = true;
			
		}
		
		if(highRollers.size()==0)
			highRollers.add(highestRoller);
		
		return highRollers;
		
	}
	
	/*
	 * 
	 * Sets the next player as current
	 * in a circular queue fashion
	 * 
	 */
	private static void setNextPlayer() {
		
		int nextIndex = playerList.indexOf(currentPlayer) + 1;
		
		if(nextIndex==playerList.size())
			nextIndex = 0;
		
		currentPlayer = playerList.get(nextIndex);
		
	}

}

class LudoBoard {
	
	// board layout arrays
	String[][] square = new String[15][15];
	String[][] border = new String[16][15];
	
	// record of pieces in a block
	// first two ints are coords, third is the piece
	// max 4 pieces possible in one block
	Piece[][][] pieceRecord = new Piece[15][15][4];
	
	// x and y coordinates of home (starting) positions
	int[][] greenHomePos = { { 2, 2 }, { 2, 3 }, { 3, 2 }, { 3, 3 } };
	int[][] yellowHomePos = { { 2, 11 }, { 2, 12 }, { 3, 11 }, { 3, 12 } };
	int[][] redHomePos = { { 11, 2 }, { 11, 3 }, { 12, 2 }, { 12, 3 } };
	int[][] blueHomePos = { { 11, 11 }, { 11, 12 }, { 12, 11 }, { 12, 12 } };
	
	Map<String, int[][]> positionMap;
	
	// x and y coordinates of each color's path
	int[][] greenPath = { { 6, 1 }, { 6, 2 }, { 6, 3 }, { 6, 4 }, { 6, 5 }, 
			{ 5, 6 }, { 4, 6 }, { 3, 6 }, { 2, 6 }, { 1, 6 }, { 0, 6 }, 
			{ 0, 7 }, { 0, 8 }, { 2, 8 }, { 3, 8 }, { 4, 8 }, { 5, 8 }, 
			{ 6, 9 }, { 6, 10 }, { 6, 11 }, { 6, 12 }, { 6, 13 }, { 6, 14 }, 
			{ 7, 14 }, { 8, 14 }, { 8, 12 }, { 8, 11 }, { 8, 10 }, { 8, 9 }, 
			{ 9, 8 }, { 10, 8 }, { 11, 8 }, { 12, 8 }, { 13, 8 }, { 14, 8 }, 
			{ 14, 7}, { 14, 6 }, { 12, 6 }, { 11, 6 }, { 10, 6 }, { 9, 6 }, 
			{ 8, 5 }, { 8, 4 }, { 8, 3 }, { 8, 2 }, { 8, 1 }, { 8, 0 }, 
			{ 7, 0 }, { 7, 1 }, { 7, 2 }, { 7, 3 }, { 7, 4 }, { 7, 5 }, { 7, 6 } };
	
	int[][] yellowPath = { { 1, 8 }, { 2, 8 }, { 3, 8 }, { 4, 8 }, { 5, 8 }, 
			{ 6, 9 }, { 6, 10 }, { 6, 11 }, { 6, 12 }, { 6, 13 }, { 6, 14 }, 
			{ 7, 14 }, { 8, 14 }, { 8, 12 }, { 8, 11 }, { 8, 10 }, { 8, 9 }, 
			{ 9, 8 }, { 10, 8 }, { 11, 8 }, { 12, 8 }, { 13, 8 }, { 14, 8 }, 
			{ 14, 7}, { 14, 6 }, { 12, 6 }, { 11, 6 }, { 10, 6 }, { 9, 6 }, 
			{ 8, 5 }, { 8, 4 }, { 8, 3 }, { 8, 2 }, { 8, 1 }, { 8, 0 }, 
			{ 7, 0 }, { 6, 0 }, { 6, 2 }, { 6, 3 }, { 6, 4 }, { 6, 5 }, 
			{ 5, 6 }, { 4, 6 }, { 3, 6 }, { 2, 6 }, { 1, 6 }, { 0, 6 }, 
			{ 0, 7 }, { 1, 7 }, { 2, 7 }, { 3, 7 }, { 4, 7 }, { 5, 7 }, { 6, 7 } };
	
	int[][] redPath = { { 13, 6 }, { 12, 6 }, { 11, 6 }, { 10, 6 }, { 9, 6 }, 
			{ 8, 5 }, { 8, 4 }, { 8, 3 }, { 8, 2 }, { 8, 1 }, { 8, 0 }, 
			{ 7, 0 }, { 6, 0 }, { 6, 2 }, { 6, 3 }, { 6, 4 }, { 6, 5 }, 
			{ 5, 6 }, { 4, 6 }, { 3, 6 }, { 2, 6 }, { 1, 6 }, { 0, 6 }, 
			{ 0, 7 }, { 0, 8 }, { 2, 8 }, { 3, 8 }, { 4, 8 }, { 5, 8 }, 
			{ 6, 9 }, { 6, 10 }, { 6, 11 }, { 6, 12 }, { 6, 13 }, { 6, 14 }, 
			{ 7, 14 }, { 8, 14 }, { 8, 12 }, { 8, 11 }, { 8, 10 }, { 8, 9 }, 
			{ 9, 8 }, { 10, 8 }, { 11, 8 }, { 12, 8 }, { 13, 8 }, { 14, 8 }, 
			{ 14, 7}, { 13, 7}, { 12, 7}, { 11, 7}, { 10, 7}, { 9, 7}, { 8, 7} };
	
	int[][] bluePath = { { 8, 13 }, { 8, 12 }, { 8, 11 }, { 8, 10 }, { 8, 9 }, 
			{ 9, 8 }, { 10, 8 }, { 11, 8 }, { 12, 8 }, { 13, 8 }, { 14, 8 }, 
			{ 14, 7}, { 14, 6 }, { 12, 6 }, { 11, 6 }, { 10, 6 }, { 9, 6 }, 
			{ 8, 5 }, { 8, 4 }, { 8, 3 }, { 8, 2 }, { 8, 1 }, { 8, 0 }, 
			{ 7, 0 }, { 6, 0 }, { 6, 2 }, { 6, 3 }, { 6, 4 }, { 6, 5 }, 
			{ 5, 6 }, { 4, 6 }, { 3, 6 }, { 2, 6 }, { 1, 6 }, { 0, 6 }, 
			{ 0, 7 }, { 0, 8 }, { 2, 8 }, { 3, 8 }, { 4, 8 }, { 5, 8 }, 
			{ 6, 9 }, { 6, 10 }, { 6, 11 }, { 6, 12 }, { 6, 13 }, { 6, 14 }, 
			{ 7, 14 }, { 7, 13 }, { 7, 12 }, { 7, 11 }, { 7, 10 }, { 7, 9 }, { 7, 8 } };
	
	Map<String, int[][]> pathMap;
	
	LudoBoard() {
		
		/*
		 * 
		 * initialize position map
		 * 
		 */
		
		positionMap = new HashMap<String, int[][]>();
		positionMap.put("Green", greenHomePos);
		positionMap.put("Yellow", yellowHomePos);
		positionMap.put("Red", redHomePos);
		positionMap.put("Blue", blueHomePos);
		
		/*
		 * 
		 * Initialize path map
		 * 
		 */
		
		pathMap = new HashMap<String, int[][]>();
		pathMap.put("Green", greenPath);
		pathMap.put("Yellow", yellowPath);
		pathMap.put("Red", redPath);
		pathMap.put("Blue", bluePath);
		
		/*
		 * 
		 * Initialize pieceRecord
		 * 
		 */
		
		for(int i=0; i<pieceRecord.length; i++) {
			
			pieceRecord[i] = new Piece[15][4];
			
			for(int j=0; j<pieceRecord[i].length; j++) {
				
				pieceRecord[i][j] = new Piece[4];
				
				for(int k=0; k<pieceRecord[i][j].length; k++)
					
					pieceRecord[i][j][k] = null;
				
			}
			
		}
		
		System.out.println(pieceRecord[3][3]);
		System.out.println(pieceRecord[3][3][0]);
		
		/*
		 * 
		 * Initialize the board's layout
		 * 
		 */
		
		square[0][0] = "|Green";
		square[0][1] = "      ";
		square[0][2] = "      ";
		square[0][3] = "      ";
		square[0][4] = "      ";
		square[0][5] = "     |";
		square[0][6] = "|    |";
		square[0][7] = "|    |";
		square[0][8] = "|    |";
		square[0][9] = "|     ";
		square[0][10] = "      ";
		square[0][11] = "      ";
		square[0][12] = "      ";
		square[0][13] = "     Y";
		square[0][14] = "ellow|";
		square[1][0] = "|    |";
		square[1][1] = "|     ";
		square[1][2] = "      ";
		square[1][3] = "      ";
		square[1][4] = "     |";
		square[1][5] = "|    |";
		square[1][6] = "|    |";
		square[1][7] = "|    |";
		square[1][8] = "|    |";
		square[1][9] = "|    |";
		square[1][10] = "|     ";
		square[1][11] = "      ";
		square[1][12] = "      ";
		square[1][13] = "     |";
		square[1][14] = "|    |";
		square[2][0] = "|    |";
		square[2][1] = "|    |";
		square[2][2] = "|    |";
		square[2][3] = "|    |";
		square[2][4] = "|    |";
		square[2][5] = "|    |";
		square[2][6] = "|    |";
		square[2][7] = "|    |";
		square[2][8] = "|    |";
		square[2][9] = "|    |";
		square[2][10] = "|    |";
		square[2][11] = "|    |";
		square[2][12] = "|    |";
		square[2][13] = "|    |";
		square[2][14] = "|    |";
		square[3][0] = "|    |";
		square[3][1] = "|    |";
		square[3][2] = "|    |";
		square[3][3] = "|    |";
		square[3][4] = "|    |";
		square[3][5] = "|    |";
		square[3][6] = "|    |";
		square[3][7] = "|    |";
		square[3][8] = "|    |";
		square[3][9] = "|    |";
		square[3][10] = "|    |";
		square[3][11] = "|    |";
		square[3][12] = "|    |";
		square[3][13] = "|    |";
		square[3][14] = "|    |";
		square[4][0] = "|    |";
		square[4][1] = "|     ";
		square[4][2] = "      ";
		square[4][3] = "      ";
		square[4][4] = "     |";
		square[4][5] = "|    |";
		square[4][6] = "|    |";
		square[4][7] = "|    |";
		square[4][8] = "|    |";
		square[4][9] = "|    |";
		square[4][10] = "|     ";
		square[4][11] = "      ";
		square[4][12] = "      ";
		square[4][13] = "     |";
		square[4][14] = "|    |";
		square[5][0] = "|     ";
		square[5][1] = "      ";
		square[5][2] = "      ";
		square[5][3] = "      ";
		square[5][4] = "      ";
		square[5][5] = "     |";
		square[5][6] = "|    |";
		square[5][7] = "|    |";
		square[5][8] = "|    |";
		square[5][9] = "|     ";
		square[5][10] = "      ";
		square[5][11] = "      ";
		square[5][12] = "      ";
		square[5][13] = "      ";
		square[5][14] = "     |";
		square[6][0] = "|    |";
		square[6][1] = "|    |";
		square[6][2] = "|    |";
		square[6][3] = "|    |";
		square[6][4] = "|    |";
		square[6][5] = "|    |";
		square[6][6] = "|     ";
		square[6][7] = "      ";
		square[6][8] = "     |";
		square[6][9] = "|    |";
		square[6][10] = "|    |";
		square[6][11] = "|    |";
		square[6][12] = "|    |";
		square[6][13] = "|    |";
		square[6][14] = "|    |";
		square[7][0] = "|    |";
		square[7][1] = "|    |";
		square[7][2] = "|    |";
		square[7][3] = "|    |";
		square[7][4] = "|    |";
		square[7][5] = "|    |";
		square[7][6] = "|     ";
		square[7][7] = "      ";
		square[7][8] = "     |";
		square[7][9] = "|    |";
		square[7][10] = "|    |";
		square[7][11] = "|    |";
		square[7][12] = "|    |";
		square[7][13] = "|    |";
		square[7][14] = "|    |";
		square[8][0] = "|    |";
		square[8][1] = "|    |";
		square[8][2] = "|    |";
		square[8][3] = "|    |";
		square[8][4] = "|    |";
		square[8][5] = "|    |";
		square[8][6] = "|     ";
		square[8][7] = "      ";
		square[8][8] = "     |";
		square[8][9] = "|    |";
		square[8][10] = "|    |";
		square[8][11] = "|    |";
		square[8][12] = "|    |";
		square[8][13] = "|    |";
		square[8][14] = "|    |";
		square[9][0] = "|     ";
		square[9][1] = "      ";
		square[9][2] = "      ";
		square[9][3] = "      ";
		square[9][4] = "      ";
		square[9][5] = "     |";
		square[9][6] = "|    |";
		square[9][7] = "|    |";
		square[9][8] = "|    |";
		square[9][9] = "|     ";
		square[9][10] = "      ";
		square[9][11] = "      ";
		square[9][12] = "      ";
		square[9][13] = "      ";
		square[9][14] = "     |";
		square[10][0] = "|    |";
		square[10][1] = "|     ";
		square[10][2] = "      ";
		square[10][3] = "      ";
		square[10][4] = "     |";
		square[10][5] = "|    |";
		square[10][6] = "|    |";
		square[10][7] = "|    |";
		square[10][8] = "|    |";
		square[10][9] = "|    |";
		square[10][10] = "|     ";
		square[10][11] = "      ";
		square[10][12] = "      ";
		square[10][13] = "     |";
		square[10][14] = "|    |";
		square[11][0] = "|    |";
		square[11][1] = "|    |";
		square[11][2] = "|    |";
		square[11][3] = "|    |";
		square[11][4] = "|    |";
		square[11][5] = "|    |";
		square[11][6] = "|    |";
		square[11][7] = "|    |";
		square[11][8] = "|    |";
		square[11][9] = "|    |";
		square[11][10] = "|    |";
		square[11][11] = "|    |";
		square[11][12] = "|    |";
		square[11][13] = "|    |";
		square[11][14] = "|    |";
		square[12][0] = "|    |";
		square[12][1] = "|    |";
		square[12][2] = "|    |";
		square[12][3] = "|    |";
		square[12][4] = "|    |";
		square[12][5] = "|    |";
		square[12][6] = "|    |";
		square[12][7] = "|    |";
		square[12][8] = "|    |";
		square[12][9] = "|    |";
		square[12][10] = "|    |";
		square[12][11] = "|    |";
		square[12][12] = "|    |";
		square[12][13] = "|    |";
		square[12][14] = "|    |";
		square[13][0] = "|    |";
		square[13][1] = "|     ";
		square[13][2] = "      ";
		square[13][3] = "      ";
		square[13][4] = "     |";
		square[13][5] = "|    |";
		square[13][6] = "|    |";
		square[13][7] = "|    |";
		square[13][8] = "|    |";
		square[13][9] = "|    |";
		square[13][10] = "|     ";
		square[13][11] = "      ";
		square[13][12] = "      ";
		square[13][13] = "     |";
		square[13][14] = "|    |";
		square[14][0] = "|Red  ";
		square[14][1] = "      ";
		square[14][2] = "      ";
		square[14][3] = "      ";
		square[14][4] = "      ";
		square[14][5] = "     |";
		square[14][6] = "|    |";
		square[14][7] = "|    |";
		square[14][8] = "|    |";
		square[14][9] = "|     ";
		square[14][10] = "      ";
		square[14][11] = "      ";
		square[14][12] = "      ";
		square[14][13] = "      ";
		square[14][14] = " Blue|";
		
		border[0][0] = "------";
		border[0][1] = "------";
		border[0][2] = "------";
		border[0][3] = "------";
		border[0][4] = "------";
		border[0][5] = "------";
		border[0][6] = "------";
		border[0][7] = "------";
		border[0][8] = "------";
		border[0][9] = "------";
		border[0][10] = "------";
		border[0][11] = "------";
		border[0][12] = "------";
		border[0][13] = "------";
		border[0][14] = "------";
		border[1][0] = "      ";
		border[1][1] = "------";
		border[1][2] = "------";
		border[1][3] = "------";
		border[1][4] = "------";
		border[1][5] = "      ";
		border[1][6] = "------";
		border[1][7] = "------";
		border[1][8] = "------";
		border[1][9] = "      ";
		border[1][10] = "------";
		border[1][11] = "------";
		border[1][12] = "------";
		border[1][13] = "------";
		border[1][14] = "      ";
		border[2][0] = "      ";
		border[2][1] = "      ";
		border[2][2] = "------";
		border[2][3] = "------";
		border[2][4] = "      ";
		border[2][5] = "      ";
		border[2][6] = "------";
		border[2][7] = "------";
		border[2][8] = "------";
		border[2][9] = "      ";
		border[2][10] = "      ";
		border[2][11] = "------";
		border[2][12] = "------";
		border[2][13] = "      ";
		border[2][14] = "      ";
		border[3][0] = "      ";
		border[3][1] = "      ";
		border[3][2] = "------";
		border[3][3] = "------";
		border[3][4] = "      ";
		border[3][5] = "      ";
		border[3][6] = "------";
		border[3][7] = "------";
		border[3][8] = "------";
		border[3][9] = "      ";
		border[3][10] = "      ";
		border[3][11] = "------";
		border[3][12] = "------";
		border[3][13] = "      ";
		border[3][14] = "      ";
		border[4][0] = "      ";
		border[4][1] = "      ";
		border[4][2] = "------";
		border[4][3] = "------";
		border[4][4] = "      ";
		border[4][5] = "      ";
		border[4][6] = "------";
		border[4][7] = "------";
		border[4][8] = "------";
		border[4][9] = "      ";
		border[4][10] = "      ";
		border[4][11] = "------";
		border[4][12] = "------";
		border[4][13] = "      ";
		border[4][14] = "      ";
		border[5][0] = "      ";
		border[5][1] = "------";
		border[5][2] = "------";
		border[5][3] = "------";
		border[5][4] = "------";
		border[5][5] = "      ";
		border[5][6] = "------";
		border[5][7] = "------";
		border[5][8] = "------";
		border[5][9] = "      ";
		border[5][10] = "------";
		border[5][11] = "------";
		border[5][12] = "------";
		border[5][13] = "------";
		border[5][14] = "      ";
		border[6][0] = "------";
		border[6][1] = "------";
		border[6][2] = "------";
		border[6][3] = "------";
		border[6][4] = "------";
		border[6][5] = "------";
		border[6][6] = "------";
		border[6][7] = "------";
		border[6][8] = "------";
		border[6][9] = "------";
		border[6][10] = "------";
		border[6][11] = "------";
		border[6][12] = "------";
		border[6][13] = "------";
		border[6][14] = "------";
		border[7][0] = "------";
		border[7][1] = "------";
		border[7][2] = "------";
		border[7][3] = "------";
		border[7][4] = "------";
		border[7][5] = "------";
		border[7][6] = "      ";
		border[7][7] = "      ";
		border[7][8] = "      ";
		border[7][9] = "------";
		border[7][10] = "------";
		border[7][11] = "------";
		border[7][12] = "------";
		border[7][13] = "------";
		border[7][14] = "------";
		border[8][0] = "------";
		border[8][1] = "------";
		border[8][2] = "------";
		border[8][3] = "------";
		border[8][4] = "------";
		border[8][5] = "------";
		border[8][6] = "      ";
		border[8][7] = "      ";
		border[8][8] = "      ";
		border[8][9] = "------";
		border[8][10] = "------";
		border[8][11] = "------";
		border[8][12] = "------";
		border[8][13] = "------";
		border[8][14] = "------";
		border[9][0] = "------";
		border[9][1] = "------";
		border[9][2] = "------";
		border[9][3] = "------";
		border[9][4] = "------";
		border[9][5] = "------";
		border[9][6] = "------";
		border[9][7] = "------";
		border[9][8] = "------";
		border[9][9] = "------";
		border[9][10] = "------";
		border[9][11] = "------";
		border[9][12] = "------";
		border[9][13] = "------";
		border[9][14] = "------";
		border[10][0] = "      ";
		border[10][1] = "------";
		border[10][2] = "------";
		border[10][3] = "------";
		border[10][4] = "------";
		border[10][5] = "      ";
		border[10][6] = "------";
		border[10][7] = "------";
		border[10][8] = "------";
		border[10][9] = "      ";
		border[10][10] = "------";
		border[10][11] = "------";
		border[10][12] = "------";
		border[10][13] = "------";
		border[10][14] = "      ";
		border[11][0] = "      ";
		border[11][1] = "      ";
		border[11][2] = "------";
		border[11][3] = "------";
		border[11][4] = "      ";
		border[11][5] = "      ";
		border[11][6] = "------";
		border[11][7] = "------";
		border[11][8] = "------";
		border[11][9] = "      ";
		border[11][10] = "      ";
		border[11][11] = "------";
		border[11][12] = "------";
		border[11][13] = "      ";
		border[11][14] = "      ";
		border[12][0] = "      ";
		border[12][1] = "      ";
		border[12][2] = "------";
		border[12][3] = "------";
		border[12][4] = "      ";
		border[12][5] = "      ";
		border[12][6] = "------";
		border[12][7] = "------";
		border[12][8] = "------";
		border[12][9] = "      ";
		border[12][10] = "      ";
		border[12][11] = "------";
		border[12][12] = "------";
		border[12][13] = "      ";
		border[12][14] = "      ";
		border[13][0] = "      ";
		border[13][1] = "      ";
		border[13][2] = "------";
		border[13][3] = "------";
		border[13][4] = "      ";
		border[13][5] = "      ";
		border[13][6] = "------";
		border[13][7] = "------";
		border[13][8] = "------";
		border[13][9] = "      ";
		border[13][10] = "      ";
		border[13][11] = "------";
		border[13][12] = "------";
		border[13][13] = "      ";
		border[13][14] = "      ";
		border[14][0] = "      ";
		border[14][1] = "------";
		border[14][2] = "------";
		border[14][3] = "------";
		border[14][4] = "------";
		border[14][5] = "      ";
		border[14][6] = "------";
		border[14][7] = "------";
		border[14][8] = "------";
		border[14][9] = "      ";
		border[14][10] = "------";
		border[14][11] = "------";
		border[14][12] = "------";
		border[14][13] = "------";
		border[14][14] = "      ";
		border[15][0] = "------";
		border[15][1] = "------";
		border[15][2] = "------";
		border[15][3] = "------";
		border[15][4] = "------";
		border[15][5] = "------";
		border[15][6] = "------";
		border[15][7] = "------";
		border[15][8] = "------";
		border[15][9] = "------";
		border[15][10] = "------";
		border[15][11] = "------";
		border[15][12] = "------";
		border[15][13] = "------";
		border[15][14] = "------";
		
	}
	
	void printBoard() {
		
		for(int i=0; i<16; i++) {
			
			for(int j=0; j<15; j++)
				System.out.print(border[i][j]);
			
			System.out.println();
			
			if(i==15)
				break;
			
			for(int j=0; j<15; j++) 
				System.out.print(renderBlock(i, j));
			
			System.out.println();
			
		}
		
	}
	
	/*
	 * 
	 * Method to render the block with
	 * its current contents
	 * 
	 */
	
	private String renderBlock(int yCoord, int xCoord) {
		
		String defaultBlock = square[yCoord][xCoord];
		Piece[] pieceArray = pieceRecord[yCoord][xCoord];
		String contents = "";
		
		for(int i=0; i<pieceArray.length; i++) {
			
			Piece piece = pieceArray[i];
			if(piece==null)
				break;
			
			if(contents.length()==0) {
				
				String color = piece.getColor();
				contents += color.substring(0, 1) + piece.getPieceNumber();
				
			}
			// if already contains characters,
			// the color's letter is already in
			else contents += piece.getPieceNumber();
			
		}
		
		String renderedBlock = defaultBlock.substring(0, 1) + contents + defaultBlock.substring(contents.length()+1);
		
		return renderedBlock;
		
	}
	
	/*
	 * 
	 * Called at the beginning of a game
	 * 
	 */
	
	void initializePieces(Player player) {
		
		String color = player.getColor();
		
		int[][] coords = positionMap.get(color);
		
		for(int i=0; i<4; i++) {
			
			int yCoord = coords[i][0];
			int xCoord = coords[i][1];
			
			setPieceCoords(player.getPiece(i), yCoord, xCoord);
			
		}
		
	}
	
	/*
	 * 
	 * Takes a piece out of the home circle.
	 * Returns true if successful
	 * 
	 */
	
	boolean takePieceOut(Piece piece) {
		
		if(piece.isTakenOut())
			return false;
		
		boolean successfullyMoved = movePiece(piece, 6);
		
		if(successfullyMoved) {
			
			piece.setTakenOut(true);
			return true;
			
		}
		
		return false;
		
	}
	
	/*
	 * 
	 * Moves a piece if possible.
	 * Returns true if successful
	 * 
	 */
	
	boolean movePiece(Piece piece, int howManySquares) {
		
		if(!piece.isTakenOut() && howManySquares!=6)
			return false;
		
		String color = piece.getColor();
		int[][] path = pathMap.get(color);
		int currentY = piece.getY();
		int currentX = piece.getX();
		int currentBlock = 0;
		
		for(int i=0; i<path.length; i++) {
			
			// if piece is still at home, currentBlock
			// will remain 0
			if(path[i][0]==currentY && path[i][1]==currentX) {
				
				currentBlock = i;
				break;
				
			}
			
		}
		
		int endPosition = currentBlock + howManySquares;
		if(endPosition>=path.length)
			return false; // cannot finish without exact number
		
		for(int i=currentBlock+1; i<=endPosition; i++)
			if(isBlocked(piece, path[i][0], path[i][1]))
				return false; // blocked by enemy
		
		if(containsOneEnemyPiece(piece, path[endPosition][0], path[endPosition][1]))
			consumeEnemy(path[endPosition][0], path[endPosition][1]);
	
		if(!piece.isTakenOut()) {
			
			setPieceCoords(piece, path[endPosition-1][0], path[endPosition-1][1]);
			piece.setTakenOut(true);
			
		} else 
			setPieceCoords(piece, path[endPosition][0], path[endPosition][1]);
		
		if(endPosition+1==path.length)
			piece.setCompleted(true);
		
		return true;
					
	}
	
	/*
	 * 
	 * Checks if there are any possible moves
	 * 
	 */
	
	boolean movesArePossible(Player player, int numberRolled) {
		
		if(numberRolled==6) {
			
			for(int i=0; i<4; i++) {
				
				Piece piece = player.getPiece(i);
				if(canTakePieceOut(piece))
					return true;
				
			}
			
		}
		
		for(int i=0; i<4; i++) {
			
			Piece piece = player.getPiece(i);
			if(canMovePiece(piece, numberRolled))
				return true;
			
		}

		return false;
		
	}
	
	private boolean canTakePieceOut(Piece piece) {
		
		if(piece.isTakenOut())
			return false;
		
		boolean canMove = canMovePiece(piece, 6);
		
		if(canMove)
			return true;
		
		return false;
		
	}
	
	private boolean canMovePiece(Piece piece, int howManySquares) {
		
		if(!piece.isTakenOut() && howManySquares!=6)
			return false;
		
		String color = piece.getColor();
		int[][] path = pathMap.get(color);
		int currentY = piece.getY();
		int currentX = piece.getX();
		int currentBlock = 0;
		
		for(int i=0; i<path.length; i++) {
			
			// if piece is still at home, currentBlock
			// will remain 0
			if(path[i][0]==currentY && path[i][1]==currentX) {
				
				currentBlock = i;
				break;
				
			}
			
		}
		
		int endPosition = currentBlock + howManySquares;
		if(endPosition>=path.length)
			return false; // cannot finish without exact number
		
		for(int i=currentBlock+1; i<=endPosition; i++)
			if(isBlocked(piece, path[i][0], path[i][1]))
				return false; // blocked by enemy
		
		return true;
		
	}

	/*
	 * 
	 * Consumes one enemy piece by
	 * sending it back home
	 * 
	 */
	
	private void consumeEnemy(int yCoord, int xCoord) {
		
		// if got this far, there is only one piece
		// and it belongs to an enemy
		Piece piece = pieceRecord[yCoord][xCoord][0];
		String color = piece.getColor();
		int[][] homeCoords = positionMap.get(color);
		
		for(int i=0; i<4; i++) {
			
			int homeY = homeCoords[i][0];
			int homeX = homeCoords[i][1];
			
			if(pieceRecord[homeY][homeX][0]==null) {
				
				piece.setTakenOut(false);
				setPieceCoords(piece, homeY, homeX);
				break;
				
			}
			
		}
		
	}
	
	/*
	 * 
	 * Methods to find out if a block
	 * contains an enemy piece (just one)
	 * or is blocked (2 or more pieces)
	 * 
	 */
	
	boolean containsOneEnemyPiece(Piece piece, int yCoord, int xCoord) {
		
		List<Piece> pieceList = new ArrayList<Piece>();
		
		for(int i=0; i<4; i++) {
			
			Piece currentPiece = pieceRecord[yCoord][xCoord][i];
			
			// if contains a friendly piece
			if(currentPiece!=null && currentPiece.getColor().equals(piece.getColor()))
				return false;
			else if(currentPiece!=null)
				pieceList.add(currentPiece);
			else break;
			
		}
			
		if(pieceList.size()==1)
			return true;
		else return false;
		
	}
	
	boolean isBlocked(Piece piece, int yCoord, int xCoord) {
		
		List<Piece>pieceList = new ArrayList<Piece>();
		
		for(int i=0; i<4; i++) {
			
			Piece currentPiece = pieceRecord[yCoord][xCoord][i];
			
			// if contains a friendly piece
			if(currentPiece!=null && currentPiece.getColor().equals(piece.getColor()))
				return false;
			else if(currentPiece!=null)
				pieceList.add(currentPiece);
			else break;
			
		}
			
		if(pieceList.size()>1)
			return true;
		else return false;
		
	}
	
	/*
	 * 
	 * Sets a piece's coordinates,
	 * updating the pieceRecord accordingly
	 * 
	 */
	
	void setPieceCoords(Piece piece, int yCoord, int xCoord) {
		
		boolean notInitialized = piece.getX()==0 && piece.getY()==0;
		
		if(notInitialized) {
			
			pieceRecord[yCoord][xCoord][0] = piece;
			piece.setY(yCoord);
			piece.setX(xCoord);
						
		} else {
			
			// sort out the block the piece was currently in
			
			int currentY = piece.getY();
			int currentX = piece.getX();
			
			for(int i=0; i<pieceRecord[currentY][currentX].length; i++) {
				
				if(pieceRecord[currentY][currentX][i]==piece) {
					
					pieceRecord[currentY][currentX][i] = null;
					
					rearrangeBlock(currentY, currentX);
					break;
					
				}
				
			}
			
			// sort out new piece's block
			
			pieceRecord[yCoord][xCoord][3] = piece;
			piece.setY(yCoord);
			piece.setX(xCoord);
			rearrangeBlock(yCoord, xCoord);
			
		}
		
	}
	
	/*
	 * 
	 * Arranges a coordinate to keep the pieces in order,
	 * as in 1 2 3 4, not 2 3 1 4
	 * 
	 */
	
	private void rearrangeBlock(int yCoord, int xCoord) {
		
		List<Piece> pieceList = new ArrayList<Piece>();
		
		for(int i=0; i<4; i++) {
			
			Piece piece = pieceRecord[yCoord][xCoord][i];
			
			if(piece!=null) {
				
				pieceList.add(piece);
				pieceRecord[yCoord][xCoord][i] = null;
				
			}
			
		}
				
		if(pieceList.size()!=0) {
			
			int numberOfPieces = pieceList.size();
			
			for(int i=0; i<numberOfPieces; i++) {
				
				int lowestPieceNumber = 5;
				int lowestPieceIndex = 5;
				
				for(int j=i; j<numberOfPieces; j++) {
					
					int nextPieceNumber = pieceList.get(j).getPieceNumber();
					boolean isLower = nextPieceNumber < lowestPieceNumber;
					
					if(isLower) {
						
						lowestPieceNumber = nextPieceNumber;
						lowestPieceIndex = j;
						
					}
					lowestPieceNumber = isLower ? nextPieceNumber : lowestPieceNumber;
					
				}
				
				pieceRecord[yCoord][xCoord][i] = pieceList.get(lowestPieceIndex);
				
			}
			
		}
		
	}
	
}

class Player {
	
	private String color;
	private int numberRolled;
	private boolean hasRolledSix;
	private Piece[] piece;
	
	Player(String color) {
		
		this.color = color;
		
		piece = new Piece[4];
		
		for(int i=0; i<4; i++)
			piece[i] = new Piece(i, color);
		
	}
	
	void rollDice() {
		
		// nextInt() gives an int from 0 to 5, adding 1
		// to make it in the range of 1-6
		numberRolled = new Dice().roll();
		
		if(numberRolled==6)
			hasRolledSix = true;
		else hasRolledSix = false;
		
	}
	
	Piece getPiece(int pieceNumber) {
		return piece[pieceNumber];
	}
	
	String getColor() {
		return color;
	}
	
	int getNumberRolled() {
		return numberRolled;
	}
	
	boolean hasRolledSix() {
		return hasRolledSix;
	}
	
	@Override
	public String toString() {
		return "Player " + color;
	}
	
	boolean hasWon() {
		
		// if any piece is not completed,
		// will return false
		for(int i=0; i<4; i++)
			if(!piece[i].isCompleted())
				return false;
		
		// otherwise true - hooray!
		return true;
		
	}
	
}

class Piece {
	
	private int pieceNumber;
	private int xCoord;
	private int yCoord;
	private String color;
	private boolean takenOut;
	private boolean completed;
	
	Piece(int pieceNumber, String color) {
		
		this.pieceNumber = pieceNumber+1;
		this.color = color;
		
	}
	
	String getColor() {
		return color;
	}
	
	int getPieceNumber() {
		return pieceNumber;
	}
	
	int getX() {
		return xCoord;
	}
	
	int getY() {
		return yCoord;
	}
	
	void setX(int x) {
		xCoord = x;
	}
	
	void setY(int y) {
		yCoord = y;
	}
	
	void setTakenOut(boolean mode) {
		takenOut = mode;
	}
	
	boolean isTakenOut() {
		return takenOut;
	}
	
	void setCompleted(boolean mode) {
		completed = mode;
	}
	
	boolean isCompleted() {
		return completed;
	}
	
}

class Dice {
	
	// nextInt() gives an int from 0 to 5, adding 1
	// to make it in the range of 1-6
	int roll() {
		return new Random().nextInt(6) + 1;
	}
	
}