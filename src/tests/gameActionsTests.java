package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class gameActionsTests {
	
	private static Board board;
	public static final int TARGET_RUNS = 30;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		board.setConfigFiles("TCJPClueLayout.csv", "TCJPClueLayoutLegend.txt");		
		board.initialize();
		
		board.setPeopleFile("TCJPSuspects.txt");
		board.loadPeopleConfig();
		
		board.setWeaponFile("TCJPWeapons.txt");
		board.loadWeaponConfig();
	}

	@Test
	public void testAccusation() { //Tests solution checking on board
		Solution theSolution = new Solution("Natsuki Subaru","Katana","Server Room");
		board.setSolution(theSolution); //Hard code a solution 
		
		assert(board.checkAccusation(theSolution)); //Ensure that the game registers it as correct
		
		Solution badSol1 = new Solution("Jotaro Kujo","Katana","Server Room"); //Wrong person
		assert(!board.checkAccusation(badSol1));
		
		Solution badSol2 = new Solution("Natsuki Subaru","Dominator","Server Room"); //Wrong weapon
		assert(!board.checkAccusation(badSol2));
		
		Solution badSol3 = new Solution("Natsuki Subaru","Katana","Patio"); //Wrong room
		assert(!board.checkAccusation(badSol3));
	}
	
	@Test
	public void testTargeting() { //Tests solution checking on board
		ComputerPlayer cpuPlay = new ComputerPlayer("Motoko Kusanagi", 3, 4, Color.blue); //Make a CPU player
		board.calcTargets(cpuPlay.getRow(),cpuPlay.getCol(),1); //Assume a roll of 1 for movement, so no rooms in target list
		
		Set<BoardCell> targets = board.getTargets(); //Get target list
				
		boolean got24 = false;
		boolean got22 = false;
		boolean got33 = false;
		
		for (int i = 0; i < TARGET_RUNS; i++) { //Run a number of target selection, turn on bools as their squares are selected
			BoardCell targCell = cpuPlay.pickLocation(targets);
			
			if (targCell.equals(board.getCellAt(2, 4))) got24 = true;
			if (targCell.equals(board.getCellAt(2, 2))) got22 = true;
			if (targCell.equals(board.getCellAt(3, 3))) got33 = true;
		}
		
		assert(got22 && got24 && got33); //Check all were selected at some point
		
		cpuPlay.setLocation(5, 3); //Put one door within range, assume unvisited
		board.calcTargets(cpuPlay.getRow(),cpuPlay.getCol(),3); //Fix roll of 3
		
		targets = board.getTargets();
		for (int i = 0; i < TARGET_RUNS; i++) {
			assert(cpuPlay.pickLocation(targets).equals(board.getCellAt(3, 2))); //Ensure the CPU picks the unvisited room
			cpuPlay.setLastRoom(null); //Trick into thinking each run is fresh
		}
		
		Map<Character, String> legend = board.getLegend();
		cpuPlay.setLastRoom(legend.get('G')); //Set gym as last visited room
		
		boolean got34 = false;
		boolean got32 = false;
		boolean got23 = false;
		boolean got54 = false;
		boolean got43 = false;
		
		for (int i = 0; i < TARGET_RUNS; i++) { //Run a number of target selection, turn on bools as their squares are selected
			BoardCell targCell = cpuPlay.pickLocation(targets);
			
			if (targCell.equals(board.getCellAt(3, 4))) got34 = true;
			if (targCell.equals(board.getCellAt(3, 2))) got32 = true;
			if (targCell.equals(board.getCellAt(2, 3))) got23 = true;
			if (targCell.equals(board.getCellAt(5, 4))) got54 = true;
			if (targCell.equals(board.getCellAt(4, 3))) got43 = true;
		}
		
		assert(got34 && got32 && got23 && got54 && got43); //Check all were selected at some point
		
		cpuPlay.setLocation(3, 4); //Set location again
		board.calcTargets(cpuPlay.getRow(),cpuPlay.getCol(),2); //Fix roll of 2, now 2 rooms in range with 1 recently visited
		
		targets = board.getTargets();
		
		for (int i = 0; i < TARGET_RUNS; i++) {
			assert(cpuPlay.pickLocation(targets).equals(board.getCellAt(3, 2))); //Ensure the CPU picks the unvisited room
			cpuPlay.setLastRoom(legend.get('G')); //Trick into thinking it visited the gym last
		}
	}

}
