package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;
	public static final int NUM_ROWS = 4;
	public static final int NUM_COLUMNS = 4;

	public IntBoard() {
		adjMtx = new HashMap<>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		grid = new BoardCell[NUM_ROWS][NUM_COLUMNS];
		createGrid();
		calcAdjacencies();
		
	}
	
	public void createGrid(){
		for(int i = 0; i < NUM_ROWS; i++){
			for(int j = 0; j < NUM_COLUMNS; j++){
				grid[i][j] = new BoardCell(i,j);
			}
		}
	}
	
	public void calcAdjacencies(){
		for(int i = 0;i < NUM_ROWS;i++){
			for(int j = 0;j < NUM_COLUMNS;j++){
				adjMtx.put(grid[i][j], getAdjList(grid[i][j]));
			}
		}
	}
	
	public void calcTargets(BoardCell startCell, int pathLength){
		visited.clear();
		targets.clear();
		visited.add(grid[startCell.getRow()][startCell.getColumn()]);
		findAllTargets(startCell, pathLength);
	}
	
	public void findAllTargets(BoardCell startCell, int pathLength){
		for(BoardCell adjCell : adjMtx.get(startCell)){
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
				if((i + 1 >= 0) && (j >= 0) && (i + 1 < NUM_ROWS) && (j < NUM_COLUMNS)){
					adjSet.add(grid[i +1][j]);
				}
				
				if((i - 1 >= 0) && (j >= 0) && (i -1 < NUM_ROWS) && (j < NUM_COLUMNS)){
					adjSet.add(grid[i - 1][j]);
				}
				if((i >= 0) && (j + 1 >= 0) && (i < NUM_ROWS) && (j + 1 < NUM_COLUMNS)){
					adjSet.add(grid[i][j + 1]);
				}
				if((i >= 0) && (j - 1 >= 0) && (i < NUM_ROWS) && (j - 1 < NUM_COLUMNS)){
					adjSet.add(grid[i][j - 1]);
				}
		for(BoardCell currentCell : adjSet){
			if(visited.contains(currentCell)){
				adjSet.remove(currentCell);
			}
		}
		return adjSet;
	}
	public BoardCell getCell(int r, int c){
		//System.out.println(grid[r][c].toString());
		return grid[r][c];
	}
	
	

}
