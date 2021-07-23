import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Main { 
	
	
	
	public static void main(String[] args) {
		GameField gameFrame = new GameField("My Tetris v1");
		gameFrame.setBackground(new Color(50,120,120));
		gameFrame.go();
		gameFrame.setSize(gameFrame.getWidth(),gameFrame.getHeight()+40);
	}
}
