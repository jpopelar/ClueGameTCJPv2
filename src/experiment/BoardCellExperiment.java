package experiment;
	
public class BoardCellExperiment {
	// variable to store the row index
	private int row;
	// variable to store the column index
	private int column;

	// constructor to initialize the row index and column index
	public BoardCellExperiment(int r, int c) {
		row = r;
		column = c;
	}

	// get the row index
	public int getRow() {
		return row;
	}

	// get the column index
	public int getColumn() {
		return column;
	}

	// toString method for printing
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}

}
