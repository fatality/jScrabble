package de.hsaugsburg.games.boardgames.scrabble;

import de.hsaugsburg.games.boardgames.ICommand;

public enum Command implements ICommand {
	
	NEWGAME ("newgame",  "<sp | mp | cp> * start new game in single- or multiplayer mode", true,  String.class),
	PLAYER	("player" ,  "<name> <terminal> * add new players or else default players will be used", true, String.class),
	ADD     ("add"    ,  "<letter> <rowlt> <columnnr>  * a letter and coordinates in range", false,  LetterPiece.class, char.class, int.class),
	REMOVE	("remove" ,	 "<rowlt> <columnnr>  * a letter and an integer in range", false,  char.class, int.class),
	COMMIT	("commit" ,  "* commit word", false),
	PASS	("pass"   ,  "* skip one turn", false),
	AGREE	("agree"  ,  "* agree that the lastly commited word(s) is(are) real", false),
	REJECT	("reject" ,	 "* reject lastly commited word(s) ", false),
	RENDER  ("render" ,  "* render board", false), 
	HELP    ("help"   ,  "* list all commands", false),
	SAVE	("save"   ,  "* save current game", false),
	LOAD	("load"   ,  "* load lastly saved game", false),
	EXIT    ("exit"   ,  "* exit program", false);
	
	private String     token; 
	private String     helpText; 
	private Class<?>[] paramTypes;
	private Object[]   params;
	private boolean	   optionalParams;
	
	private Command(String token, String helpText, boolean optionalParams, Class<?>... paramTypes) {
		this.token = token;
		this.helpText = helpText;
		this.paramTypes = paramTypes;
		this.optionalParams  = optionalParams;
		if (paramTypes.length > 0) {
			params = new Object[paramTypes.length];
		}
	}
	
	public String getToken() {
		return token;
	}
	
	public String getHelpText() {
		return helpText;
	}
	
	public Class<?>[] getParamTypes() {
		return paramTypes;
	}
	
	public Object[] getParams() {
		return params;
	}
	
	public boolean hasOptionalParams() {
		return optionalParams;
	}
	
}
