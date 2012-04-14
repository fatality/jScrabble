package de.hsaugsburg.games.boardgames;

import java.io.Serializable;
import de.hsaugsburg.games.boardgames.exceptions.GameException;

/**
 * This interface provides the method for a computer
 * algorithm to return the next command.
 * 
 * @author Marc Rochow, Anja Radtke
 */
public interface ICommandScanner extends Serializable {
	
	public abstract Object next(int id) throws GameException;
	
	/**
	 * Checks if player is remote.
	 * @return true if remote player.
	 */
	public abstract boolean isTerminal();
	
}
