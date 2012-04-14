package de.hsaugsburg.games.boardgames.scrabble.strategy;

import java.util.ArrayList;
import java.util.List;

public class Trie {
	
   private TrieNode root;
   
   /**
    * Constructor
    */
   public Trie() {
      root = new TrieNode();
   }
   
   /**
    * Adds a word to the Trie
    * @param word
    */
   public void add(String word) {
      root.add(word.toLowerCase());
   }
   
   /**
    * Get the words in the Trie with the given
    * prefix
    * @param prefix
    * @return a List containing String objects containing the words in
    *         the Trie with the given prefix.
    */
   public List<String> getWords(String prefix) {
      //Find the node which represents the last letter of the prefix
	  prefix = prefix.toLowerCase();
      TrieNode lastNode = root;
      for (int i=0; i < prefix.length(); i++) {
		 lastNode = lastNode.getNode(prefix.charAt(i));
		 
		 //If no node matches, then no words exist, return empty list
		 if (lastNode == null) return new ArrayList<String>();	 
      }
      
      //Return the words which eminate from the last node
      return lastNode.getWords();
   }
   
   public boolean containsPrefix(String prefix){
	   prefix = prefix.toLowerCase();
	   TrieNode lastNode = root;
	   for (int i=0; i < prefix.length(); i++) {
		   lastNode = lastNode.getNode(prefix.charAt(i));
		   //If no node matches, then no words exist, return empty list
		   if (lastNode == null) {
			   return false;	 
		   }
		}
	    return true;
   }
   
   public boolean containsPrefix(List<String> prefix) {
//		System.out.println("prefixes: " + prefix);
		if (prefix.size() == 0) return false;
		for (String string : prefix) {
			if (!containsPrefix(string)) return false;
		}
		return true;
	}
   
   public boolean containsWord(String word){
	   word = word.toLowerCase();
	   TrieNode lastNode = root;
	   for (int i=0; i < word.length(); i++) {
		   lastNode = lastNode.getNode(word.charAt(i));
		   //If no node matches, then no words exist, return empty list
		   if (lastNode == null) return false;
		}
	    if (lastNode.isWord()) return true;
	    return false;
   }
   
   public boolean contains(List<String> words, Trie wordList) {
		if (words.isEmpty()) return false;
		for (String word : words){
			if (!wordList.containsWord(word)) return false;
		}
		return true;
	}

}
