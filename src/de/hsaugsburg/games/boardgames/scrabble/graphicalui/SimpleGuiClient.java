package de.hsaugsburg.games.boardgames.scrabble.graphicalui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import de.hsaugsburg.games.boardgames.scrabble.ScrabbleBoard;
import de.hsaugsburg.games.boardgames.scrabble.ScrabbleEngine;
import de.hsaugsburg.games.boardgames.scrabble.terminal.Terminal;
import de.hsaugsburg.games.boardgames.scrabble.terminal.TerminalUtils;

public class SimpleGuiClient extends Terminal {
	
	private JFrame frame;
	private GBoardView boardArea;
	private JTextField textInput;
	private JLabel promptLabel;
	private JLabel messageLabel;
	
	public SimpleGuiClient(int id) {
		super(id);
		frame = new JFrame("Scrabble Terminal");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		boardArea = new GBoardView(new ScrabbleBoard());
		frame.add(BorderLayout.SOUTH, createControlPanel());
		frame.add(BorderLayout.CENTER, boardArea);
		frame.setLocation(200, 200);
		frame.pack();
		frame.setVisible(true);
	}
	
	protected JPanel createControlPanel() {
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(2,1));
		JPanel upperControls = new JPanel();
		JPanel lowerControls = new JPanel();
		lowerControls.setLayout(new BorderLayout());
		promptLabel = new JLabel();
		promptLabel.setPreferredSize(new Dimension(20,20));
		messageLabel = new JLabel();
		textInput = new JTextField();
		textInput.setEnabled(false);
		textInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textInput.isEnabled()) {
					TerminalUtils.writeFile(new File(TerminalUtils.INPUT_RESPONSE + ID), textInput.getText());
					promptLabel.setText(null);
					textInput.setText(null);
					SimpleGuiClient.this.textInput.setEnabled(false);
				}
				
			}
			
		});
		upperControls.add(messageLabel);
		lowerControls.add(BorderLayout.CENTER, textInput);
		lowerControls.add(BorderLayout.WEST, promptLabel);
		controlPanel.add(upperControls);
		controlPanel.add(lowerControls);
		return controlPanel;
	}
	
	protected Object readObject() {
		File file = new File(TerminalUtils.SERVER_OBJ + ID);
		if (file.exists() && file.canRead()) {
			Object obj = TerminalUtils.readObject(file);
			while (!file.delete()) {
				try {
					Thread.sleep(10);
				} 
				catch (InterruptedException e) {
					
				}
			}
			return obj;
		}
		return null;
	}
	

	private void run() {
		while (true) {
			String message = readMessage();
			if (message != null) {
				messageLabel.setText(message);
			}
			
			ScrabbleBoard board = (ScrabbleBoard) readObject();
			if (board != null) {
				boardArea.setBoard(board);
				boardArea.repaint();
			}
			try {
				Thread.sleep(5);
			}
			catch (InterruptedException e) {
				
			}
			
			String request = checkRequest();
			if (request != null) {
				promptLabel.setText(request);
				this.textInput.requestFocusInWindow();
				this.textInput.setEnabled(true);
			}
			try {
				Thread.sleep(5);
			}
			catch (InterruptedException e) {
				
			}
		}
	}
	
	public static void main(String[] args) {
		SimpleGuiClient gui = new SimpleGuiClient(1);
		gui.run();
	}
	
}
