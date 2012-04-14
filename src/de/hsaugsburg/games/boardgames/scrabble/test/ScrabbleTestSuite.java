package de.hsaugsburg.games.boardgames.scrabble.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
   
@RunWith(Suite.class)
@Suite.SuiteClasses({
	CircularListTest.class, 
	GridPointTest.class, 
	WordManagerTest.class, 
	ScrabbleBoardTest.class
})

public class ScrabbleTestSuite {
    // the class remains completely empty, being used only as a holder for the above annotations
}
