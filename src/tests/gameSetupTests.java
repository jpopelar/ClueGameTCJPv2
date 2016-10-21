package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class gameSetupTests {
	
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		board.setConfigFiles("TCJPClueLayout.csv", "TCJPClueLayoutLegend.txt");		
		board.initialize();
		
		board.setPeopleFile("TCJPSuspects.txt");
		board.loadPeopleConfig();
	}

	@Test
	public void playerSetUp() {
		//Get player list, make sure the size is good
		ArrayList<Player> players = board.getPlayers();
		assertEquals(players.size(), 6);
		
		//First entry in player list should be human player with following parameters		
		Player humPlayer = players.get(0);		
		assert(humPlayer instanceof HumanPlayer);
		assertEquals(humPlayer.getName(), "Edward Elric");
		assertEquals(humPlayer.getRow(), 23);
		assertEquals(humPlayer.getCol(), 10);
		assertEquals(humPlayer.getColor(), Color.red);
		
		//Test parameters for a couple CPU players
		Player compPlayer1 = players.get(4);
		assert(compPlayer1 instanceof ComputerPlayer);
		assertEquals(compPlayer1.getName(), "Natsuki Subaru");
		assertEquals(compPlayer1.getRow(), 0);
		assertEquals(compPlayer1.getCol(), 4);
		assertEquals(compPlayer1.getColor(), Color.yellow);
		
		Player compPlayer2 = players.get(3);
		assert(compPlayer2 instanceof ComputerPlayer);
		assertEquals(compPlayer2.getName(), "Princess Mononoke");
		assertEquals(compPlayer2.getRow(), 1);
		assertEquals(compPlayer2.getCol(), 10);
		assertEquals(compPlayer2.getColor(), Color.green);
		
	}

}
