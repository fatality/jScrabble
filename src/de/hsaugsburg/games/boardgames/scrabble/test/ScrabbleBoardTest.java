package de.hsaugsburg.games.boardgames.scrabble.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import de.hsaugsburg.games.boardgames.scrabble.strategy.GreedyScrabbleBot;
import de.hsaugsburg.games.boardgames.scrabble.IScrabbleEngine;
import de.hsaugsburg.games.boardgames.scrabble.LetterPiece;
import de.hsaugsburg.games.boardgames.scrabble.ScrabbleBoard;
import de.hsaugsburg.games.boardgames.scrabble.ScrabbleEngine;
import de.hsaugsburg.games.boardgames.scrabble.ScrabbleEngine.State;

public class ScrabbleBoardTest {

	GreedyScrabbleBot bot;
	ScrabbleBoard fakeBoard;
	ScrabbleBoard realBoard;
	IScrabbleEngine engine;
	
	@Before
	public void setUp() throws Exception {
		fakeBoard = new ScrabbleBoard();
		realBoard = new ScrabbleBoard();
		fakeBoard.reset();
		realBoard.reset();
		engine = new ScrabbleEngine(realBoard, new RandomPoolDummy<LetterPiece>());
		bot = new GreedyScrabbleBot(engine, realBoard);
		bot.getWordList().clear();
		bot.getWordList().add("ER");
	}
	

	@Test
	public void testEqualsObject() {
		
		try {
			// NEWGAME
			engine.engageState(State.INITIAL);
			engine.reset();
			engine.addDefaultPlayers(2);
			engine.fillPool();
			engine.getList().previous();
			engine.getManager().setPlayer(engine.getList().next());
			engine.givePieces();
			System.out.println(engine.getManager().getPlayer().getPieces().toString());
			
			// ADD (Player1)
			engine.engageState(State.DROPPING);
			
			engine.addPiece(LetterPiece.E, 7, 7);
			engine.addPiece(LetterPiece.S, 7, 8);
			fakeBoard.putPiece(LetterPiece.E, 7, 7);
			fakeBoard.putPiece(LetterPiece.S, 7, 8);
			assertEquals(fakeBoard, realBoard);
			
			// COMMIT (Player1)
			engine.engageState(State.COMMITED);
			engine.getManager().commitLetterSequence(engine.isFirst());

			// AGREE (Player1)
			engine.engageState(State.AGREEING);
			engine.getManager().changePreliminaryStatus();
			engine.getManager().setPlayer(engine.getList().next());
			engine.givePieces();
			System.out.println(engine.getManager().getPlayer().getPieces().toString());
			
			// compare fixed
			fakeBoard.getDetails(7, 7).setFixed();
			fakeBoard.getDetails(7, 8).setFixed();
			assertEquals(fakeBoard, realBoard);
			
			// execute bot algorithm
			bot.next();
			// AGREE (Computer)
			engine.engageState(State.AGREEING);
			engine.getManager().changePreliminaryStatus();
			engine.getManager().setPlayer(engine.getList().next());
			engine.givePieces();
			System.out.println(engine.getManager().getPlayer().getPieces().toString());
			
			fakeBoard.putPiece(LetterPiece.R, 8, 7);
			fakeBoard.getDetails(8, 7).setFixed();
			assertEquals(fakeBoard, realBoard);
	
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
}