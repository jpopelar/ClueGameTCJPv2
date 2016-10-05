package clueGame;

import java.util.Map;
import java.util.Set;

import experiment.BoardCell;

public class Board {
	private static Board theInstance = new Board();
	private int numRows;
	private int numColumns;
	public final static int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private Map<Character, String> rooms;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	
	private Board() {
		
	}

	public static Board getInstance(){
		return null;
	}
	
	public void initialize(){
		
	}
	
	public void loadRoomConfig(){
		
	}
	
	public void loadBoardConfig(){
		
	}
	
	public void calcAdjacencies(){
		
	}
	
	public void createGrid(){
	
	}
	
	public void calcTargets(BoardCell startCell, int pathLength){
		
	}
	
	public void findAllTargets(BoardCell startCell, int pathLength){
		
	}
	
	public Set<BoardCell> getTargets(){
		return null;
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell){
		return null;
	}
	
	public BoardCell getCell(int r, int c){
		return null;
	}
}
