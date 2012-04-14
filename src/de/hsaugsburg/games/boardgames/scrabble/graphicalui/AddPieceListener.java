package de.hsaugsburg.games.boardgames.scrabble.graphicalui;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddPieceListener extends MouseAdapter {
	
	private Controller controller;
	
	public AddPieceListener(Controller controller) {
		this.controller = controller;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		if (e.getButton() == MouseEvent.BUTTON1 && controller.getPiece() != null) {
			int column = (int)((e.getX()/(double)controller.getBoardArea().getWidth())*(double)controller.getBoardWidth());
			int row = (int)((e.getY()/(double)controller.getBoardArea().getHeight())*(double)controller.getBoardHeight());
			controller.addPiece(column, row);
		}
	}
	
}
