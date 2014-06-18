package nl.mprog.apps.hangman5878241;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import java.util.Random;
import android.app.ActionBar;
 

public class MainActivity extends Activity 
{	
	// Global Variables
	Gameplay game; 
	Highscores highscores;
	String randomWord = "";
	int SizeRandomWord = randomWord.length();
	char UserInput;
	EditText keyboardinput;
	TextView guessWordView;
	TextView wrongGuessesView;
	ImageView hangmanImage;
	Button highscore;
	int[] hangmanViews;
    private static final Random rgenerator = new Random();
    private Resources res;

		
	// Access to shared preferences to get the Settings
    SharedPreferences sharedPrefs;
    
	// Global variables for settings
	int max_tries;
	int difficulty_settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// Setup the top bar title and show it
		ActionBar actionBar = getActionBar();
		actionBar.setSubtitle("Hangman");
		actionBar.setTitle("By Yzygoings"); 
		actionBar.show();
		
		// This has the settings of the app which we need later for difficulty 
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		max_tries = Integer.parseInt(sharedPrefs.getString("max_wrong_tries", "7"));
		difficulty_settings = Integer.parseInt(sharedPrefs.getString("difficulty", "5"));
		
		// Create new game
		game = new Gameplay(max_tries); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// hide highscore button
		highscore = (Button) findViewById(R.id.button1);
		highscore.setVisibility(View.INVISIBLE);
		 
		// set keyboard
		keyboardinput = (EditText)findViewById(R.id.editText1);
		keyboardinput.addTextChangedListener(new WordTextWatcher());
		
		// load resources
		res = getResources();
		
		// get a random word
		game.setWord(getRandomWord());
	
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
	
	@Override
	// settings menu
	public boolean onCreateOptionsMenu(Menu menu) 
	{
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	} 
	
	
	@Override
	// settings menu: set restart + difficulty activities
	  public boolean onOptionsItemSelected(MenuItem item) 
	{
	    switch (item.getItemId()) 
	    {
	    	// restart (use new game activity)
		    case R.id.restart:
		    	Intent newgameActivity = new Intent(getApplicationContext(), MainActivity.class);
		    	startActivity(newgameActivity);
		    	break;
		    // difficulty
		    case R.id.difficulty:
		    	Intent settingsActivity = new Intent(getApplicationContext(), SettingsActivity.class);
		    	startActivity(settingsActivity);
		      break;
		    default:
		      break;  
	    }

	    return true;
	  } 
	
	/**
	 * Get random word from string from words_small.xml
	 */
	public String getRandomWord() 
	{
		
		String[] randomWordString;
		randomWordString = res.getStringArray(R.array.words_small);
		
		// Get random word from string
		String randomWord = randomWordString[rgenerator.nextInt(randomWordString.length)];
		
		// Check if random word is larger than difficulty settings, if so load new random word and check again
		while (randomWord.length() > difficulty_settings)
		{
			randomWord = randomWordString[rgenerator.nextInt(randomWordString.length)];
		}
		
		return randomWord;
	}
	
	
	private class WordTextWatcher implements TextWatcher
	{
		 public void afterTextChanged(Editable s) 
	     { 
			 	String input = s.toString();
	        	
	        	// Prevent crash
			 	if (input.length() <= 0) 
			 	{
			 		return;
			 	}
			 	
			 	char letter = input.toUpperCase().charAt(0);
	        	
	        	// One letter at a time
	        	if (keyboardinput.length() > 1)
	        	{
	        		keyboardinput.setText(letter);
	        	}	
	        	
	        	// remove letter for game purpose
	        	game.guess(letter);
	        	s.delete(0, 1);
	        	
	        	// update view
	        	int currentImage = game.wrongTries;
	        	
	        	// (Not enough images) if not game-over, if not game won, stick with hangman7.png
	        	if (!game.gameover() && !game.finished() && currentImage > 7)
	        		currentImage = 7;
	        	// if hangmanimage is >= than 7, stick with hangman7
	        	if (currentImage >= hangmanViews.length)
	        		currentImage = hangmanViews.length-1;
	        	hangmanImage.setImageResource(hangmanViews[currentImage]);
	        	
	        	guessWordView.setText(game.guessWord);
	        	wrongGuessesView.setText(game.wrongGuesses);
	        	
	        	// check if game is won
	        	if (game.finished() == true)  
	        	{
	        		hangmanImage.setImageResource(R.drawable.hangmanwin);
	        		
	        		// stop keyboard input if game is won
	        		keyboardinput.setEnabled(false);
	        		
	        		// show button
	        		highscore.setVisibility(View.VISIBLE);
	        			        		
	        		// add highscore
	        		Highscores hs = new Highscores(getApplicationContext());
	        		hs.AddScore(game.wrongTries);

	        		// listen to button
	        		highscore.setOnClickListener(new View.OnClickListener() 
	        		{
	        			// start new intent
	        			Intent highscoreView =  new Intent(getApplicationContext(), HighscoresActivity.class);

						@Override
						public void onClick(View v) 
						{
							startActivity(highscoreView);
						}
	        		});   
	        	} 
	        
	        	   
	        	// gameover?
	        	if (game.wrongTries == max_tries)
	        	{
	        		hangmanImage.setImageResource(R.drawable.hangmangameover);
	        		
	        		// stop keyboard input if game is over
	        		keyboardinput.setEnabled(false);
	        	
	        		// show button
	        		highscore.setVisibility(View.VISIBLE);
	        		
	        		// listen to button
	        		highscore.setOnClickListener(new View.OnClickListener() 
	        		{
	        			// start new intent
	        			Intent highscoreView =  new Intent(getApplicationContext(), HighscoresActivity.class);

						@Override
						public void onClick(View v) 
						{
							startActivity(highscoreView);
						}
	        		});	
	        	}
	        }
	        
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){}
	}
}
