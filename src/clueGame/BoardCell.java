package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	
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
		return 0;
	}

	public int getColumn() {
		return 0;
	}
	
}
