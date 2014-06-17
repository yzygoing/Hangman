package nl.mprog.apps.hangman5878241;

import android.util.Log;


/**
 * Hangman gameplay class
 */
public class Gameplay 
{
	int maxWrongTries;
	
	int correctTries;
	int wrongTries;
	
	String word;
	String guessWord;
	
	String wrongGuesses;
	
	/**
	 * Gameplay variables
	 */
	public Gameplay(int maxTries) 
	{
		maxWrongTries = maxTries;
		correctTries = 0;
		wrongTries = 0;
		
		wrongGuesses = " ";
	}
	
	
	/**
	 * Check if input letter is in guessWord
	 */
	public void guess(char letter) 
	{
		char[] guessLetters = guessWord.toCharArray();
		
		// If input letter is wrong
		if (word.indexOf(letter) == -1 && wrongGuesses.indexOf(letter) == -1) 
		{
			wrongGuesses = wrongGuesses + letter;
			wrongTries += 1;
		}
		
		// If input letter is correct
		else
		{
			// Iterate over word to check for double correct input
			for (int i = 0; i < word.length(); i++) 
			{
				if (word.charAt(i) == letter)
				{
					Log.d("HANGMAN", "Letter is "+letter+" OK");
					guessLetters[i] = letter;
				}
			}
			
			// Count correct tries + update guessWord
			correctTries += 1;
			guessWord = new String(guessLetters);
		}
	}

	
	/**
	 * 
	 * Check if game is won
	 */
	public boolean finished() 
	{
		// If all hyphens are not empty, game is won
		if (guessWord.indexOf("-") == -1)
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
		if (wrongTries == maxWrongTries)
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
		
		// Create empty row of hyphens from word length (for view + win)
		for (int i = 0; i < word.length(); i++)
		{
			guessWord = guessWord + "-";
		}
	}
	
	
}
