package experiment;
	
public class BoardCellExperiment {
	private int row;
	private int column;

	public BoardCellExperiment(int r, int c) {
		row = r;
		column = c;
	}

	
	
	public int getRow() {
		return row;
	}



	public int getColumn() {
		return column;
	}



	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}

	
}
