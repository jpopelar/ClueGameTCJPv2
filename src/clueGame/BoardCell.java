package clueGame;

import java.util.Map;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection opensWhichWay;
	
	

	public BoardCell(int row, int column, char initial, DoorDirection opensWhichWay) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.opensWhichWay = opensWhichWay;
	}

	public boolean isWalkway(){
		if(initial == 'W') {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isRoom(){
		if(initial != 'W') {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isDoorway(){
		System.out.println(opensWhichWay);
		System.out.println(row + " " + column);
		if (opensWhichWay != DoorDirection.NONE) {
			return true;
		} else {
			return false;
		}
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
