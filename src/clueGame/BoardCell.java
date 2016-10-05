package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection opensWhichWay;
	
	public BoardCell() {
		// TODO Auto-generated constructor stub
	}

	public boolean isWalkway(){
		return false;
	}
	
	public boolean isRoom(){
		return false;
	}
	
	public boolean isDoorway(){
		return false;
	}
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return opensWhichWay;
	}

	public char getInitial() {
		// TODO Auto-generated method stub
		return initial;
	}
	
}
