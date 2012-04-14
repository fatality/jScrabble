package de.hsaugsburg.games.boardgames.scrabble.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import de.hsaugsburg.games.boardgames.scrabble.terminal.Terminal;
import de.hsaugsburg.games.boardgames.scrabble.terminal.TerminalUtils;

public class GraphicUserInterface extends Terminal {

	private JFrame frame;
	private JTextField textInput;
	private JPanel picPanel;
	private boolean request;
	private JLabel picLabel;
	
	public GraphicUserInterface(int id) {
		super(id);
		frame = new JFrame("Scrabble Terminal");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		textInput = new JTextField();
		textInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (request) {
					TerminalUtils.writeFile(new File(TerminalUtils.INPUT_RESPONSE + ID), textInput.getText());
					textInput.setText("");
					GraphicUserInterface.this.request = false;
				}
				
			}
			
		});
		
		picPanel = new JPanel();
		frame.getContentPane().add(picPanel, BorderLayout.NORTH);
		picLabel = new JLabel(new ImageIcon("./ressources/Scrabble_Spielfeld.png"));
		//Now an idea for an simple and adequate GUI, a mixture between "drag and drop" and "console GUI"
		/*TODO: split the board to :
		        a NORTH label: A to Z, 
		        a WEST label: 1 to 15 and the real board
		        a CENTER label: the Scrabble Board
		     then:
		     	<code>new GridLayout(15, 15)</code> to the Board-Label
		     	add the <code>("letter_piece" + String s + ".jpg");</code>
		     	at one Grid, the imgs are in the right dimensions to fit
		*/
		picPanel.add(picLabel);
//		picLabel.setLayout(new GridLayout(15, 15));
//		picLabel.add(new ImageIcon("./resources/letter_pieceA.jpg"));
		frame.add(BorderLayout.SOUTH, textInput);
		frame.setLocation(200, 200);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new GraphicUserInterface(1);
	}
	
}
