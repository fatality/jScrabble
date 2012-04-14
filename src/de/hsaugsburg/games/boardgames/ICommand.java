package de.hsaugsburg.games.boardgames;

public interface ICommand {

	public String getToken();
	
	public String getHelpText();
	
	public Class<?>[] getParamTypes();
	
	public Object[] getParams();
	
	public boolean hasOptionalParams();
}
