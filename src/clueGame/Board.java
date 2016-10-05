package clueGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import experiment.BoardCellExperiment;


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
	
	private Board() {
		initialize();
	}

	public static Board getInstance(){
		return theInstance;
	}
	
	public void initialize(){
		adjMatrix = new HashMap<>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		board = new BoardCell[numRows][numColumns];
		createGrid();
		calcAdjacencies();
	}
	
	public void loadRoomConfig(){
		
	}
	
	public void loadBoardConfig(){
		
	}
	
	public void calcAdjacencies(){
		for(int i = 0;i < numRows;i++){
			for(int j = 0;j < numColumns;j++){
				adjMatrix.put(board[i][j], getAdjList(board[i][j]));
			}
		}
	}
	
	public void createGrid(){
	
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
		// TODO Auto-generated method stub
		
	}

	public Map<Character, String> getLegend() {
		// TODO Auto-generated method stub
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
