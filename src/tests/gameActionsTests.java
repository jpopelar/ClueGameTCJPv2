package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Solution;

public class gameActionsTests {
	
	private static Board board;
	
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
		board.dealCards();
	}

	@Test
	public void testAccusation() { //Tests solution checking on board
		Solution theSolution = new Solution("","","");
		board.setSolution(theSolution);
		assert(board.checkAccusation(theSolution));
		
		Solution badSol1 = new Solution("","","");
		assert(!board.checkAccusation(badSol1));
		
		Solution badSol2 = new Solution("","","");
		assert(!board.checkAccusation(badSol2));
		
		Solution badSol3 = new Solution("","","");
		assert(!board.checkAccusation(badSol3));
	}

}
