package nl.mprog.apps.hangman5878241;

import java.util.ArrayList;


/**
 * Hangman gameplay class
 */
public class Gameplay 
{
	int correctTries;
	int wrongTries;
	
	String word;
	String guessWord;
	
	String wrongGuesses;
	
	/**
	 * Gameplay variables
	 */
	public Gameplay() 
	{
		correctTries = 0;
		wrongTries = 0;
		
		wrongGuesses = "Wrong Guesses: ";
	}
	
	
	/**
	 * Check if input letter is in guessWord
	 */
	public void guess(char letter) 
	{
		int pos = letterPositionInWord(letter);
		char[] guessLetters = guessWord.toCharArray();
		if (pos > -1) 
		{
			guessLetters[pos] = letter;
			guessWord = new String(guessLetters);
			correctTries += 1;
		}
		else
		{
			if (wrongGuesses.indexOf(letter) == -1) 
			{
				wrongGuesses = wrongGuesses + letter;
				wrongTries += 1;
			}
		}
	}
	
	/**
	 * 
	 * Check if game is won
	 */
	public boolean finished() 
	{
		if (guessWord.indexOf('-') == -1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Game Over
	 */
	public boolean gameover()
	{
		// TODO: create difficulty settings
		int tries = MainActivity.MAX_TRIES;
		
		if (wrongTries == tries)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Remember position for view
	 */
	public int letterPositionInWord(char letter) 
	{		
		return word.indexOf(letter);
	}
	
	/**
	 * Create hyphens for guessword in view
	 */
	public void setWord(String w) 
	{
		word = w;
		// empty word for every new game
		guessWord = "";
		
		for (int i = 0; i < word.length(); i++)
		{
			guessWord = guessWord + "-";
		}
	}
	
	
}
