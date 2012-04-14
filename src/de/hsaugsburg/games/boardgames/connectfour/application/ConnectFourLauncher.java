package de.hsaugsburg.games.boardgames.connectfour.application;

import de.hsaugsburg.games.boardgames.connectfour.ConnectFourBoard;
import de.hsaugsburg.games.boardgames.connectfour.ConnectFourEngine;
import de.hsaugsburg.games.boardgames.connectfour.consoleui.*;
import de.hsaugsburg.games.boardgames.connectfour.exceptions.ColumnFullException;
import de.hsaugsburg.games.boardgames.connectfour.strategy.RandomRobot;
import de.hsaugsburg.games.boardgames.exceptions.OutsideBoardException;

public class ConnectFourLauncher {
	
	/**
	 *  "connectFourEngine" has to know the "connectFourBoard" to control it.
	 *  "boardView" has to know the "connectFourBoard" to be able to render it.
	 *  "randomRobot" has to know the "connectFourBoard" and the "connectFourEngine"
	 *  to make halfway smart drops.
	 *  
	 * @throws ColumnFullException 
	 * @throws OutsideBoardException 
	 * @throws BoardNotInitializedException 
	 */
	public static void main(String[] args) {
		ConnectFourBoard board = new ConnectFourBoard();
		RandomRobot robot = new RandomRobot(board);
		ConnectFourEngine engine = new ConnectFourEngine(board, robot);
		BoardView view = new BoardView(board);
		CommandProcessor commandProcessor = new CommandProcessor(engine, view);
		robot.setEngine(engine);
		commandProcessor.process();
	}
	
}
