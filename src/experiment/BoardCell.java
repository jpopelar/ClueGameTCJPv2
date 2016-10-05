package experiment;
	
public class BoardCell {
	private int row;
	private int column;

	public BoardCell(int r, int c) {
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
