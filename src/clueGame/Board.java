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
	private static Board theInstance = new Board();
	private int numRows;
	private int numColumns;
	public final static int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private Map<Character, String> rooms;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private String exceptionsLog = "exceptionsLog.txt";
	
	private Board() {
		adjMatrix = new HashMap<>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		rooms = new HashMap<Character, String>();
	}

	public static Board getInstance(){
		return theInstance;
	}
	
	public void initialize() {
		
		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch (FileNotFoundException e) {
			System.out.println("Uhhhh Ohhhhhh");
		} catch (BadConfigFormatException f) {
			try {
			System.out.println(f.getMessage());
			PrintWriter out = new PrintWriter(exceptionsLog);
			out.println(f.getMessage());
			out.close();
			} catch (FileNotFoundException g) {
				System.out.println("Log file not found.");
			}
		}
		
	}
	
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
				adjMatrix.put(board[i][j], getAdjList(board[i][j]));
			}
		}
	}
	
	public void calcTargets(BoardCell startCell, int pathLength){
		visited.clear();
		targets.clear();
		visited.add(board[startCell.getRow()][startCell.getColumn()]);
		findAllTargets(startCell, pathLength);
	}
	
	public void findAllTargets(BoardCell startCell, int pathLength){
		for(BoardCell adjCell : adjMatrix.get(startCell)){
			if(!visited.contains(adjCell)){
				if(pathLength == 1){
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
	
	public Set<BoardCell> getTargets(){
		return targets;
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell){
		Set<BoardCell> adjSet = new HashSet<BoardCell>();
		int i = cell.getRow();
		int j = cell.getColumn();
				if((i + 1 >= 0) && (j >= 0) && (i + 1 < numRows) && (j < numColumns)){
					adjSet.add(board[i +1][j]);
				}
				
				if((i - 1 >= 0) && (j >= 0) && (i -1 < numRows) && (j < numColumns)){
					adjSet.add(board[i - 1][j]);
				}
				if((i >= 0) && (j + 1 >= 0) && (i < numRows) && (j + 1 < numColumns)){
					adjSet.add(board[i][j + 1]);
				}
				if((i >= 0) && (j - 1 >= 0) && (i < numRows) && (j - 1 < numColumns)){
					adjSet.add(board[i][j - 1]);
				}
		for(BoardCell currentCell : adjSet){
			if(visited.contains(currentCell)){
				adjSet.remove(currentCell);
			}
		}
		return adjSet;
	}
	
	public BoardCell getCellAt(int r, int c){
		return board[r][c];
	}

	public void setConfigFiles(String string, String string2) {
		boardConfigFile = string;
		roomConfigFile = string2;
	}

	public Map<Character, String> getLegend() {
		return rooms;
	}

	public int getNumRows() {
		// TODO Auto-generated method stub
		return numRows;
	}

	public int getNumColumns() {
		// TODO Auto-generated method stub
		return numColumns;
	}
}
