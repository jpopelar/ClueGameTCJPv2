package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel {
	private static final long serialVersionUID = 1L;

	public ControlGUI() {
		//2 row layout
		setLayout(new GridLayout(2,0));
		JPanel panel = topRowPanel();
		add(panel);
		panel = bottomRowPanel();
		add(panel);
	}
	
	public JPanel topRowPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,0));
		
		//Setup for the turn indicator
		JPanel textPanel = new JPanel();
		JTextField turn = new JTextField(30);
		turn.setEditable(false);
		JLabel textLabel1 = new JLabel("Acitve Player");
		textPanel.add(textLabel1, BorderLayout.NORTH);
		textPanel.add(turn, BorderLayout.CENTER);
		
		JButton passTurn = new JButton("Next Player");
		JButton accuse = new JButton("Accuse a Player");
		
		mainPanel.add(textPanel, BorderLayout.WEST);
		mainPanel.add(passTurn, BorderLayout.EAST);
		mainPanel.add(accuse, BorderLayout.EAST);
		
		return mainPanel;
	}
	
	public JPanel bottomRowPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());
		
		JPanel roll = new JPanel();
		JPanel guess = new JPanel();
		JPanel result = new JPanel();
		
		roll.setLayout(new GridLayout(2,0));
		JTextField rollField = new JTextField(3);
		rollField.setEditable(false);
		JLabel textLabel1 = new JLabel("Roll");
		roll.add(textLabel1, BorderLayout.WEST);
		roll.add(rollField, BorderLayout.CENTER);
		roll.setBorder(new TitledBorder(new EtchedBorder(), "Dice"));
		
		guess.setLayout(new GridLayout(2,0));
		JTextField guessField = new JTextField(20);
		guessField.setEditable(false);
		JLabel textLabel2 = new JLabel("Sugestion");
		guess.add(textLabel2, BorderLayout.NORTH);
		guess.add(guessField, BorderLayout.CENTER);
		guess.setBorder(new TitledBorder(new EtchedBorder(), "Insinuations"));
		
		result.setLayout(new GridLayout(2,0));
		JTextField resultField = new JTextField(10);
		resultField.setEditable(false);
		JLabel textLabel3 = new JLabel("Evidence");
		result.add(textLabel3, BorderLayout.NORTH);
		result.add(resultField, BorderLayout.CENTER);
		result.setBorder(new TitledBorder(new EtchedBorder(), "Proof"));
		
		mainPanel.add(roll);
		mainPanel.add(guess);
		mainPanel.add(result);
		return mainPanel;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Control"); //Subject to change
		frame.setSize(500, 200);
		
		ControlGUI gui = new ControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}

}
