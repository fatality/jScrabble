package de.hsaugsburg.games.boardgames.scrabble.graphicalui;

import de.hsaugsburg.games.boardgames.IRobot;
import de.hsaugsburg.games.boardgames.exceptions.GameException;
import de.hsaugsburg.games.boardgames.scrabble.Command;
import de.hsaugsburg.games.boardgames.scrabble.IScrabbleEngine;
import de.hsaugsburg.games.boardgames.scrabble.LetterPiece;
import de.hsaugsburg.games.boardgames.scrabble.ScrabblePlayer;
import de.hsaugsburg.games.boardgames.scrabble.ScrabbleEngine.State;
import de.hsaugsburg.games.boardgames.scrabble.terminal.TerminalServer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.*;
import java.util.Iterator;
import java.util.List;
import javax.swing.SwingUtilities;

public class Controller {
	
	IScrabbleEngine engine;
	ScrabbleGui gui;
	private TerminalServer server;
	private Point point;
	private GLetterPiece piece;
	
	public Controller(IScrabbleEngine engine, ScrabbleGui gui) {
		this.engine = engine;
		this.gui = gui;
	}
	
	public void newgame() {
		try {
			engine.engageState(State.INITIAL);
			engine.reset();
			engine.addDefaultPlayers(2);
			engine.fillPool();
			engine.getList().previous();
			engine.getManager().setPlayer(engine.getList().next());
			engine.givePieces();
			repaintLetterBar();
			repaintBoardArea();
		} catch (GameException e) {
			displayMessage(e.getMessage());
		}
	}

	public GLetterPiece getPiece() {
		return piece;
	}

	public void setPiece(GLetterPiece piece) {
		this.piece = piece;
	}
	
//	public void addPlayer() {
//		engine.engageState(State.PLAYER);
//		engine.addPlayer((String)params[0]);
//	}
	
	public void addPiece(int column, int row) {
		try {
			engine.engageState(State.DROPPING);
			engine.addPiece(this.piece.getPiece(), row, column);	
		} catch (GameException ex) {
			displayMessage(ex.getMessage());
		}
		this.piece = null;
		repaintLetterBar();
		repaintBoardArea();
	}
	
//	public void removePiece {
//		engine.engageState(State.REMOVING);
//		engine.removePiece((Integer)params[0], (Integer)params[1]);
//		view.render();
//	}
	
	public void commit() {
		try {
			engine.engageState(State.COMMITED);
			engine.getManager().commitLetterSequence(engine.isFirst());
			displayMessage(engine.getManager().getProducedWords());
		} catch (GameException ex) {
			displayMessage(ex.getMessage());
		}
	}
	
	public void pass() {
		try {
			engine.engageState(State.PASSING);
			engine.getManager().removePreliminaryPieces();
			engine.getManager().calcScore();
			displayMessage(engine.getList().current().getName() 
					+ " NEW/TOTAL POINTS: " 
					+ engine.getList().current().getLastPoints() 
					+ "/" + engine.getList().current().getPoints());
			engine.getManager().setPlayer(engine.getList().next());
			engine.givePieces();
			repaintBoardArea();
			repaintLetterBar();
		} catch (GameException ex) {
			displayMessage(ex.getMessage());
		}
	}
	
	public void agree() {
		try {
			engine.engageState(State.AGREEING);
			engine.getManager().calcScore();
			engine.getManager().changePreliminaryStatus();
			displayMessage(engine.getList().current().getName() 
					+ " NEW/TOTAL POINTS: " 
					+ engine.getList().current().getLastPoints() 
					+ "/" + engine.getList().current().getPoints());
			engine.getManager().setPlayer(engine.getList().next());
			engine.givePieces();
			nextBotMove(engine.getList().current());
			if (!(engine.getList().current() instanceof IRobot)) {
				repaintBoardArea();
				repaintLetterBar();
			}
		} catch (Exception e) {
			displayMessage(e.getMessage());
		}
	}
	
	public void reject() {
		try {
			engine.engageState(State.REJECTING);
			engine.getManager().removePreliminaryPieces();
			repaintBoardArea();
			repaintLetterBar();
		} catch (GameException ex){
			displayMessage(ex.getMessage());
		}
	}

//	public void load() {
//		this.engine = engine.load();
//		view.setBoard(engine.getBoard());
//		view.setPlayer(engine.getList().current());
//		engine.getManager().resetLogger();
//		GreedyScrabbleBot.loadList();
//		view.render();
//	}
	
//	public void save() {
//		engine.save();
//	}
	
	public void sendMessage(String unicast) {
		if (!engine.getTerminalIds().isEmpty()) {
			server.sendObject(engine.getBoard(), engine.getTerminalIds());
			if (engine.getTerminalId() != null) {
				server.sendMessage(unicast, engine.getTerminalId());
			}
		}	
	}
	
	public void displayMessage(List<String> list){
		StringBuffer buffer = new StringBuffer();
		buffer.append("Word(s) to agree or reject:");
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			buffer.append(" ");
			buffer.append(it.next());
		}
		displayMessage(buffer.toString());
	}
	
	public void displayMessage(String msg) {
//		sendMessage(msg);
		gui.displayMessage(msg);
	}
	
//	public void help() {
//		view.printHelp();
//	}
	
	public void nextBotMove(final ScrabblePlayer player) {
		if (player.getScanner() instanceof IRobot) {
			try {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						Command command = null;
						try {
							command = (Command)player.next();
						} catch (GameException ex) {
							displayMessage(ex.getMessage());
						}
						if (command == Command.AGREE) {
							agree();
						}
						if (command == Command.PASS) {
							pass();
						}
					}
				});
			} catch (Exception ex) {
				displayMessage(ex.getMessage());
			}
		}
	}
	
	public void exit() {
		System.exit(0);
	}
	
	public int getBoardWidth() {
		return engine.getBoard().getWidth();
	}
	
	public int getBoardHeight() {
		return engine.getBoard().getHeight();
	}
	
	public GBoardView getBoardArea() {
		return gui.getBoardArea();
	}
	
	public void repaintLetterBar() {
		gui.getLetterBar().setLetters(engine.getList().current().getPieces());
		gui.repaintLetterBar();
	}
	
	public void repaintBoardArea() {
		gui.repaintBoardArea();
	}
	
}
