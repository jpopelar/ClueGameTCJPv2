package clueGame;

public class BoardCell {
	// Integer variable to store row number
	private int row;
	// Integer variable to store column number
	private int column;
	// Character variable to store identifying character of cell
	private char initial;
	// DoorDirection variable to store the direction the door opens
	private DoorDirection opensWhichWay;
	
	
	// constructor to set the initial variables
	public BoardCell(int row, int column, char initial, DoorDirection opensWhichWay) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.opensWhichWay = opensWhichWay;
	}

	// determine if cell is a walkway
	public boolean isWalkway(){
		if(initial == 'W') {
			return true;
		} else {
			return false;
		}
	}
	
	// Determine if the cell is a room
	public boolean isRoom(){
		if(initial != 'W') {
			return true;
		} else {
			return false;
		}
	}
	
	// Determine if the cell is a Doorway
	public boolean isDoorway(){
		if (opensWhichWay != DoorDirection.NONE) {
			return true;
		} else {
			return false;
		}
	}
	
	// Return the row index for the cell
	public int getRow() {
		return row;
	}

	// Return the column index for the cell
	public int getColumn() {
		return column;
	}

	// returns the direction the door opens, if it opens
	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return opensWhichWay;
	}

	// Return the identifying character for the cell
	public char getInitial() {
		// TODO Auto-generated method stub
		return initial;
	}

	// toString function used to print the information for the cell
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + ", initial=" + initial + ", opensWhichWay="
				+ opensWhichWay + "]";
	}

}
