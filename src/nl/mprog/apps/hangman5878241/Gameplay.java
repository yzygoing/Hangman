package nl.mprog.apps.hangman5878241;

import java.util.ArrayList;


/**
 * Main Hangman gameplay class
 */
public class Gameplay 
{
	int correctTries;
	int wrongTries;
	
	String word;
	String guessWord;
	
	String wrongGuesses;
	
	/**
	 * Constructor
	 */
	public Gameplay() 
	{
		correctTries = 0;
		wrongTries = 0;
		
		wrongGuesses = "Wrong Guesses: ";
	}
	
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
	
	public boolean gameover()
	{
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
	 * 
	 */
	public int letterPositionInWord(char letter) 
	{		
		return word.indexOf(letter);
	}
	
	/**
	 * 
	 */
	public void setWord(String w) 
	{
		word = w;
		guessWord = "";
		
		for (int i = 0; i < word.length(); i++)
		{
			guessWord = guessWord + "-";
		}
	}
	
	
}
