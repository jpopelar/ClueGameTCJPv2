package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ControlGUI extends JPanel {
	
	public ControlGUI() {
		//2 row layout
		setLayout(new GridLayout(2,0));
		JPanel panel = topRowPanel();
		add(panel);
		panel = bottomRowPanel();
		add(panel);
	}
	
	public JPanel topRowPanel() {
		return null;
	}
	
	public JPanel bottomRowPanel() {
		return null;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Control"); //Subject to change
		frame.setSize(250, 150);
		
		ControlGUI gui = new ControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}

}
