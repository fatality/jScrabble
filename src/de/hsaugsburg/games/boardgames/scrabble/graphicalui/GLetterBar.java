package de.hsaugsburg.games.boardgames.scrabble.graphicalui;

import java.awt.*;
import java.util.List;
import de.hsaugsburg.games.boardgames.scrabble.LetterPiece;

public class GLetterBar extends Container {
	
	private static final long serialVersionUID = -8076184891301209583L;
	private Controller controller;
	
	public GLetterBar(Controller controller) {
		setLayout(new GridLayout(1,7));
		this.controller = controller;
	}
	
	public void setLetters(List<LetterPiece> pieces) {
		this.removeAll();
		for(LetterPiece piece : pieces) {

			this.add(new GLetterPiece(piece, controller));
		}
		paintAll(getGraphics());
		
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(230, 40);
	}
	
}
