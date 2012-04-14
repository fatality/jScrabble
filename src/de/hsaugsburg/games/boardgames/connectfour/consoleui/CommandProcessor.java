package de.hsaugsburg.games.boardgames.connectfour.consoleui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import de.hsaugsburg.games.boardgames.PlayerMode;
import de.hsaugsburg.games.boardgames.connectfour.ConnectFourEngine;
import de.hsaugsburg.games.boardgames.connectfour.exceptions.ColumnFullException;
import de.hsaugsburg.games.boardgames.connectfour.exceptions.SwitchNotAllowedException;
import de.hsaugsburg.games.boardgames.exceptions.GameException;
import de.hsaugsburg.games.boardgames.exceptions.OutsideBoardException;

/**
 * @author Marc Rochow, Anja Radtke
 * @param  robot reference for RandomRobot or a human player.
 */
public class CommandProcessor {
	
	private ConnectFourEngine connectFourEngine;
	private BoardView boardView;
	private String input;
	private Command command;
	
	public CommandProcessor(ConnectFourEngine connectFourEngine, BoardView boardView) {
		command = Command.HELP;
		this.connectFourEngine = connectFourEngine;
		this.boardView = boardView;
		this.connectFourEngine.setPlayerMode(PlayerMode.SINGLE);
	}
	
	public void process() {
		while(command != Command.EXIT) {
			switch(command) {
				case NEWGAME:
					connectFourEngine.reset();
					boardView.render();
					getCommand();
					break;
				case ADD:
					try {
						if (connectFourEngine.nextDropAllowed(getColumn())) {
							connectFourEngine.drop(getColumn());
							boardView.render();
							connectFourEngine.switchPiece();
						}
					} catch (SwitchNotAllowedException snaex) {
						boardView.render(connectFourEngine.getConnectFourBoard().getPiece());
					} catch (OutsideBoardException obex) {
						System.out.println(obex.getMessage());
					} catch (ColumnFullException cfex) {
						System.out.println(cfex.getMessage());
					} catch (NumberFormatException nfex) {
						System.out.println("Unknown command: " + input);
					}
					getCommand();
					break;
				case HELP:
					try {
						File helpme = new File ("./src/de/hsaugsburg/games/boardgames/connectfour/ressources/ReadMe");
						FileReader fileReader = new FileReader(helpme); 
						BufferedReader reader = new BufferedReader(fileReader);
						String s = null;
						while ((s = reader.readLine()) != null) {
							System.out.println(s);
						}
						reader.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					getCommand();
					break;
			}	
		} 
	}
	
	/**
	 * In the single player mode either a human or a computer choice is returned
	 * while in multi player mode it always returns a human choice.
	 * 
	 * @return	an integer value which is parsed from the command string or generated from the computer enemy.	
	 */
	public int getColumn() {
		if (connectFourEngine.singlePlayer()) {
			return connectFourEngine.getChoice();
		} else {
			int ix = input.lastIndexOf(' ');
			return Integer.parseInt(input.substring(ix+1))-1;
		}
	}
	
	/**
	 * In the single player mode the first if returns always an ADD command for RandomRobot
	 * while on the a human's turn the input gets analyzed by the if's below.
	 * It sets a command in a global variable used by the <code>process()</code> method.
	 */
	public void getCommand() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			if (connectFourEngine.singlePlayer() && connectFourEngine.nextDropAllowed(getColumn())) {
				command = Command.ADD;
				return;
			}
			input = reader.readLine();
		} catch (GameException gex) {
			System.err.println(gex.getMessage());
		} catch(Exception e) {
			System.out.println("Unknown command: " + input);
			getCommand();
		} if (input.toUpperCase().startsWith(Command.ADD.name())) {
			command = Command.ADD;
			return;
		} if (input.toUpperCase().startsWith(Command.HELP.name())) {
			command = Command.HELP;
			return;
		} if (input.toUpperCase().startsWith(Command.EXIT.name())) {
			command = Command.EXIT;
			return;
		} if (input.toUpperCase().startsWith(Command.NEWGAME.name())) {
			command = Command.NEWGAME; 
			int ix = input.indexOf('-');
			if (ix > -1) {
				if ("SP".compareToIgnoreCase(input.substring(ix+1, ix+3)) == 0) {
					connectFourEngine.setPlayerMode(PlayerMode.SINGLE);
				} else if ("MP".compareToIgnoreCase(input.substring(ix+1, ix+3)) == 0) {
					connectFourEngine.setPlayerMode(PlayerMode.MULTI);
				}
			}
			return;
		}
		System.out.println("Unknown command: " + input);
		getCommand();
	}
	
	public enum Command {
		NEWGAME, ADD, HELP, EXIT;
	}
	
}
