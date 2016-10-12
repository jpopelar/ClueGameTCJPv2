package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	// variable to store the initialized board
	private static Board theInstance = new Board();
	// variable to store the number of rows on the board
	private int numRows;
	// variable to store the number of columns on the board
	private int numColumns;
	// constant to store the max size of the game board
	public final static int MAX_BOARD_SIZE = 50;
	// array to store all the board cells for the game board
	private BoardCell[][] board;
	// Map array to store the information for the different rooms
	private Map<Character, String> rooms;
	// Map array to store the adjacent cells for each cell
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	// Set to store the cells previously visited
	private Set<BoardCell> visited;
	// Set to store the target cells for the players move
	private Set<BoardCell> targets;
	// 
	private String boardConfigFile;
	// 
	private String roomConfigFile;
	// 
	private String exceptionsLog = "exceptionsLog.txt";
	
	// constructor to initialize all the arrays, sets, and map variables
	// It is private so that it is initialize instantly and never changed
	private Board() {
		adjMatrix = new HashMap<>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		rooms = new HashMap<Character, String>();
	}

	// method to return the initialized board
	public static Board getInstance(){
		return theInstance;
	}
	
	// Initialize 
	public void initialize() {
		// Try/catch statement to catch and fix any FileNotFoundExceptions and BadConfigFormatException
		try {
			// Read in the room legend and configure the room information
			loadRoomConfig();
			// Read in the board information and set the cell variables
			loadBoardConfig();
		// catch statement for FileNotFoundException
		} catch (FileNotFoundException e) {
			System.out.println("Uhhhh Ohhhhhh");
		// catch statement for BadConfigFormatException
		} catch (BadConfigFormatException f) {
			// try/catch statement for FileNotFoundException
			try {
				// print out error message
				System.out.println(f.getMessage());
				// Write error to log file
				PrintWriter out = new PrintWriter(exceptionsLog);
				out.println(f.getMessage());
				out.close();
			// catch FileNotFoundException
			} catch (FileNotFoundException g) {
				System.out.println("Log file not found.");
			}
		}
		// calculate adjacent cells for each cell
		calcAdjacencies();
	}
	
	// 
	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException{
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()) {
			String Line = in.nextLine();
			String[] roomInfo = Line.split(", ");
			if (!(roomInfo[2].equals("Card") || roomInfo[2].equals("Other"))) {
				throw new BadConfigFormatException("Not a Card or Other, Check Legend");
			}
			else {
				char character = roomInfo[0].charAt(0);
				String room = roomInfo[1];
				rooms.put(character, room);
			}
		}
	}
	
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException{
		numRows = 0;
		numColumns = 0;
		int lastColumn = 0;
		FileReader reader = new FileReader(boardConfigFile);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()) {
			String Line = in.nextLine();
			numColumns = 0;
			for (String roomString: Line.split(",")) {
				char roomInit = roomString.charAt(0);
				if(!rooms.containsKey(roomInit)){
					throw new BadConfigFormatException("Invalid board space Initial");
				} else if (roomString.length() == 2) {
					char door = roomString.charAt(1);
					switch (door) {
					case 'U':
						board[numRows][numColumns] = new BoardCell(numRows,numColumns,roomInit,DoorDirection.UP);
						break;
					case 'D':
						board[numRows][numColumns] = new BoardCell(numRows,numColumns,roomInit,DoorDirection.DOWN);
						break;
					case 'L':
						board[numRows][numColumns] = new BoardCell(numRows,numColumns,roomInit,DoorDirection.LEFT);
						break;
					case 'R':
						board[numRows][numColumns] = new BoardCell(numRows,numColumns,roomInit,DoorDirection.RIGHT);
						break;
					case 'N':
						board[numRows][numColumns] = new BoardCell(numRows,numColumns,roomInit,DoorDirection.NONE);
						break;
					}
				} else {
					board[numRows][numColumns] = new BoardCell(numRows,numColumns,roomInit,DoorDirection.NONE);
				}
				numColumns = numColumns + 1;
			}
			if((numColumns != lastColumn) && (numRows != 0)){
				throw new BadConfigFormatException("Different number of columns per row");
			}
			lastColumn = numColumns;
			
			numRows = numRows + 1;
		}
	}
	
	public void calcAdjacencies(){
		for(int i = 0;i < numRows;i++){
			for(int j = 0;j < numColumns;j++){
				adjMatrix.put(board[i][j], getAdjList(i,j));
			}
		}
	}
	
	public void calcTargets(int i, int j, int pathLength){
		visited.clear();
		targets.clear();
		visited.add(board[i][j]);
		findAllTargets(getCellAt(i,j), pathLength);
	}
	
	//
	public void findAllTargets(BoardCell startCell, int pathLength){
		for(BoardCell adjCell : adjMatrix.get(startCell)){
			if(!visited.contains(adjCell)){
				if((pathLength == 1) || adjCell.isDoorway()){
					targets.add(adjCell);
				}
				else{
					visited.add(adjCell);
					findAllTargets(adjCell, pathLength - 1);
				}
				visited.remove(adjCell);
			}
		}
	}
	
	// return the current target set
	public Set<BoardCell> getTargets(){
		return targets;
	}
	
	// method to return the adjacent list for the cell at the given location
	public Set<BoardCell> getAdjList(int i, int j){
		// Initialize the adjacent set as a HashSet
		Set<BoardCell> adjSet = new HashSet<BoardCell>();
		// 
		if(getCellAt(i, j).isRoom() && !getCellAt(i, j).isDoorway()){
			
		// if the cell at the location is a doorway
		}else if(getCellAt(i,j).isDoorway()){
			// determine the door direction and save it
			DoorDirection whichWay = getCellAt(i,j).getDoorDirection();
			// add an adjacent cell to the adjSet set depending on the door direction
			switch(whichWay){
			case UP:
				adjSet.add(board[i - 1][j]);
				break;
			case LEFT:
				adjSet.add(board[i][j - 1]);
				break;
			case RIGHT:
				adjSet.add(board[i][j + 1]);
				break;
			case DOWN:
				adjSet.add(board[i + 1][j]);
				break;
			}
		// Determine if there is a door next to the current cell and add it to the adjacent list
		}else{
			// if the cell below is on the board
			if((i + 1 >= 0) && (j >= 0) && (i + 1 < numRows) && (j < numColumns)){
				// if the cell below it is a walkway, add it to the adjacent list
				if(getCellAt(i + 1, j).isWalkway()){
					adjSet.add(board[i +1][j]);
				// if the cell below is a doorway
				}else if(getCellAt(i + 1, j).isDoorway()){
					// determine the direction the doorway opens
					DoorDirection whichWay = getCellAt(i + 1, j).getDoorDirection();
					// if the doorway opens up, add it to the adjacent list
					switch(whichWay){
					case UP:
						adjSet.add(board[i +1][j]);
						break;
					case LEFT:
					case RIGHT:
					case DOWN:
						break;
					}
				}
			}
			// if the cell above is on the board
			if((i - 1 >= 0) && (j >= 0) && (i -1 < numRows) && (j < numColumns)){
				// if the cell above is a walkway, add it to the adjacent list
				if(getCellAt(i - 1, j).isWalkway()){
					adjSet.add(board[i - 1][j]);
				// if the cell above is a doorway
				}else if(getCellAt(i - 1, j).isDoorway()){
					// determine the direction the door opens
					DoorDirection whichWay = getCellAt(i - 1, j).getDoorDirection();
					// if the doorway opens down, add it to the adjacent list
					switch(whichWay){
					case DOWN:
						adjSet.add(board[i - 1][j]);
						break;
					case LEFT:
					case RIGHT:
					case UP:
						break;
					}
				}
			}
			// if the cell to the right is on the board
			if((i >= 0) && (j + 1 >= 0) && (i < numRows) && (j + 1 < numColumns)){
				// if the cell to the right is a walkway, add it to the adjacent list
				if(getCellAt(i, j + 1).isWalkway()){
					adjSet.add(board[i][j + 1]);
				// if the cell to the right is a doorway
				}else if(getCellAt(i, j + 1).isDoorway()){
					// determine the direction the door opens
					DoorDirection whichWay = getCellAt(i, j + 1).getDoorDirection();
					// if the doorway opens left, add it to the adjacent list
					switch(whichWay){
					case LEFT:
						adjSet.add(board[i][j + 1]);
						break;
					case UP:
					case RIGHT:
					case DOWN:
						break;
					}
				}
			}
			// if the cell to the right is on the board
			if((i >= 0) && (j - 1 >= 0) && (i < numRows) && (j - 1 < numColumns)){
				// if the cell to the right is a walkway, add it to the adjacent list
				if(getCellAt(i, j - 1).isWalkway()){
					adjSet.add(board[i][j - 1]);
				// if the cell to the right is a doorway
				}else if(getCellAt(i, j - 1).isDoorway()){
					// determine the direction the door opens
					DoorDirection whichWay = getCellAt(i,j - 1).getDoorDirection();
					// add the cell to the adjacent list if the cell opens to the right
					switch(whichWay){
					case RIGHT:
						adjSet.add(board[i][j - 1]);
						break;
					case LEFT:
					case UP:
					case DOWN:
						break;
					}
				}
			}
		}
		// Remove the cell from the adjacent cell set if the cell has been visited
		for(BoardCell currentCell : adjSet){
			if(visited.contains(currentCell)){
				adjSet.remove(currentCell);
			}
		}
		return adjSet;
	
	}
	
	// Return the cell at the given row and column location
	public BoardCell getCellAt(int r, int c){
		return board[r][c];
	}

	// set the name of the configuration files for the room legend and board layout
	public void setConfigFiles(String string, String string2) {
		// initialize the name of the configuration file for the board layout
		boardConfigFile = string;
		// initialize the name of the configuration file for the room legend
		roomConfigFile = string2;
	}

	// return the map array with room legend information stored in it
	public Map<Character, String> getLegend() {
		return rooms;
	}

	// return the number of rows on the board
	public int getNumRows() {
		return numRows;
	}

	// return the number of columns on the board
	public int getNumColumns() {
		return numColumns;
	}
	
}
