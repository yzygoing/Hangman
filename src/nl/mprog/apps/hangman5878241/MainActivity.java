package nl.mprog.apps.hangman5878241;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;


public class MainActivity extends Activity 
{
	// Test for game over (TODO: create difficulty settings)
	public static final int MAX_TRIES = 10;
	
	// Global Variables
	Gameplay game;
	String randomWord = "";
	int SizeRandomWord = randomWord.length();
	char UserInput;
	EditText keyboardinput;
	TextView guessWordView;
	TextView wrongGuessesView;
	ImageView hangmanImage;
	int[] hangmanViews;
    private String[] randomWordString;
    private static final Random rgenerator = new Random();
    private Resources res;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// Create our game instance
		game = new Gameplay();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// for debug purposes
		Log.d("onCreate", "hangman");
		
		// set button 'new game'
		//final Button newgame = (Button) findViewById(R.id.button1);
		
		// set button 'settings'
		//final Button settings = (Button) findViewById(R.id.button2);
		//Log.d("init button settings", "test");
		
		// set keyboard
		keyboardinput = (EditText)findViewById(R.id.editText1);
		keyboardinput.addTextChangedListener(new WordTextWatcher());
		
		// load resources
		res = getResources();
		
		// create a random word
		game.setWord(getRandomWord());
		Log.d("Randomword = "+game.word, "hangman");
		Log.d("Guessword = "+game.guessWord, "hangman");
	
		// set guessword
		guessWordView = (TextView)findViewById(R.id.textView2);
		guessWordView.setText(game.guessWord);
		
		// set wrongguesses
		wrongGuessesView = (TextView)findViewById(R.id.textView3);
		wrongGuessesView.setText(game.wrongGuesses);

		// set image
		hangmanImage = (ImageView)findViewById(R.id.imageView1);
		
		// create image list
		hangmanViews = new int[]
				{
				R.drawable.hangmanstart,
				R.drawable.hangman0, 
				R.drawable.hangman1,
				R.drawable.hangman2, 
				R.drawable.hangman3,
				R.drawable.hangman4, 
				R.drawable.hangman5,
				R.drawable.hangman6, 
				R.drawable.hangman7,
				R.drawable.hangman8,
				R.drawable.hangmanwin,
				R.drawable.hangmangameover,
				};
		
	}
	
	/**
	 * Get random word from string array from words_small.xml
	 */
	public String getRandomWord() 
	{
		
		String[] randomWordString;
		randomWordString = res.getStringArray(R.array.words_small);
		String randomWord = randomWordString[rgenerator.nextInt(randomWordString.length)];
		
		return randomWord;
	}
	
	private class WordTextWatcher implements TextWatcher 
	{
		 public void afterTextChanged(Editable s) 
	     {
			 	String input = s.toString();
	        	Log.d("text changed to "+input, "hangman");
			 	if (input.length() <= 0) 
			 	{
			 		return;
			 	}
			 	char letter = input.toUpperCase().charAt(0);
	        	
	        	// One letter at a time
	        	if (keyboardinput.length() > 1)
	        	{
	        	//	keyboardinput.setText(letter);
	        	}
	        	
	        	// remove letter for game purpose
	        	game.guess(letter);
	        	s.delete(0, 1);
	        	
	        	// update view 
	        	hangmanImage.setImageResource(hangmanViews[game.wrongTries]);
	        	guessWordView.setText(game.guessWord);
	        	wrongGuessesView.setText(game.wrongGuesses);
	        	Log.d(game.guessWord, "hangman");
	        	
	        	// check if game is won
	        	if (game.finished() == true)
	        	{
	        		hangmanImage.setImageResource(R.drawable.hangmanwin);
	        	}
	        	
	        	// gameover?
	        	if (game.gameover() == true)
	        	{
	        		hangmanImage.setImageResource(R.drawable.hangmangameover);
	        	}
	        	
	        }
	        
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){}
	}
}
