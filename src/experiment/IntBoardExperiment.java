package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoardExperiment {
	private Map<BoardCellExperiment, Set<BoardCellExperiment>> adjMtx;
	private Set<BoardCellExperiment> visited;
	private Set<BoardCellExperiment> targets;
	private BoardCellExperiment[][] grid;
	public static final int NUM_ROWS = 4;
	public static final int NUM_COLUMNS = 4;

	public IntBoardExperiment() {
		adjMtx = new HashMap<>();
		visited = new HashSet<BoardCellExperiment>();
		targets = new HashSet<BoardCellExperiment>();
		grid = new BoardCellExperiment[NUM_ROWS][NUM_COLUMNS];
		createGrid();
		calcAdjacencies();
		
	}
	
	public void createGrid(){
		for(int i = 0; i < NUM_ROWS; i++){
			for(int j = 0; j < NUM_COLUMNS; j++){
				grid[i][j] = new BoardCellExperiment(i,j);
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
	
	public void calcTargets(BoardCellExperiment startCell, int pathLength){
		visited.clear();
		targets.clear();
		visited.add(grid[startCell.getRow()][startCell.getColumn()]);
		findAllTargets(startCell, pathLength);
	}
	
	public void findAllTargets(BoardCellExperiment startCell, int pathLength){
		for(BoardCellExperiment adjCell : adjMtx.get(startCell)){
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
	
	public Set<BoardCellExperiment> getTargets(){
		return targets;
	}
	
	public Set<BoardCellExperiment> getAdjList(BoardCellExperiment cell){
		Set<BoardCellExperiment> adjSet = new HashSet<BoardCellExperiment>();
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
		for(BoardCellExperiment currentCell : adjSet){
			if(visited.contains(currentCell)){
				adjSet.remove(currentCell);
			}
		}
		return adjSet;
	}
	public BoardCellExperiment getCell(int r, int c){
		//System.out.println(grid[r][c].toString());
		return grid[r][c];
	}
	
	

}
