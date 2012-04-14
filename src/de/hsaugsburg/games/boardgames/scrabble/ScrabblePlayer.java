package de.hsaugsburg.games.boardgames.scrabble;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import de.hsaugsburg.games.boardgames.ICommandScanner;
import de.hsaugsburg.games.boardgames.Player;
import de.hsaugsburg.games.boardgames.exceptions.GameException;

public class ScrabblePlayer extends Player implements ICommandScanner {
	
	private static final long serialVersionUID = -7752681585513763790L;
	private List<LetterPiece> myPieces = new ArrayList<LetterPiece>(7);
	private ICommandScanner scanner;
	
	public ScrabblePlayer(String s, ICommandScanner scanner) {
		super(s);
		this.scanner = scanner;
	}
	
	public void receive(LetterPiece piece) {
		myPieces.add(piece);
	}
	
	public void receiveAll(List<LetterPiece> pieceList) {
		Iterator<LetterPiece> it  = pieceList.iterator();
		while (it.hasNext()) {
			myPieces.add(it.next());
		}
	}
	
	public void giveAway(LetterPiece piece) {
		myPieces.remove(piece);
	}
	
	public List<LetterPiece> getPieces(){
		return myPieces;
	}
	
	public Object next() throws GameException {
		return next(this.getId());
	}
	
	public Object next(int id) throws GameException {
		return scanner.next(id);
	}
	
	public boolean isTerminal() {
		return scanner.isTerminal();
	}
	
	public ICommandScanner getScanner() {
		return scanner;
	}
	
	@Override
	public String toString() {
		return getName() + " " + "\nPieces: " + getPieces().toString();
	}
	
}