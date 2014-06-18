package nl.mprog.apps.hangman5878241;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


// Hangman highscores class
public class Highscores
{
	private Context context;
	
	int score;
	String date;
	String scores;
	
	// find scores
	private SharedPreferences highscorePrefs;
	
	// create arraylist scoreStrings
	List<Score> scoreStrings = new ArrayList<Score>();
	
	public Highscores(Context context)
	{
		this.context = context;
		
		// load scores
		highscorePrefs = context.getSharedPreferences("highscores", Context.MODE_PRIVATE);
		scores = highscorePrefs.getString("highscores", "No highscores yet!");
		
		// split scores for arraylist
		String [] hssplit = scores.split("\\|");
		
		// loop through scores and add highscores in arraylist
		for(String newList : hssplit)
		{
			// check if score is valid before adding
			if (newList.length() > 4 && newList.contains(" - ")) 
			{
				String[] parts = newList.split(" - ");
				Log.d("HANGMAN", "Loaindg highscore to scroeString : " + newList);
			    scoreStrings.add(new Score(parts[0], Integer.parseInt(parts[1])));
			}

		}
		
		// Sort array
		Collections.sort(scoreStrings);
	}
	
	/**
	 * Model for sorting score
	 */
	public class Score implements Comparable<Score>
	{
		String date;
		int score;
		
		public Score(String name, int score)
		{
			this.date = name;
			this.score = score;
		}
		
		// Sort score from low wrongtries to high
		public int compareTo(Score sc)
		{
			// if score is greater than new score
			if (sc.score > this.score) 
			{
				return -1; 
			}
			// if score is lesser than new score
			else if (sc.score < this.score)
			{
				return 1;
			} 
			else 
			{
				return 0;
			}

		}
		
		// Score for view
		public String toString() 
		{
			return date + " - " + score;
		}
		 
	}
 

	public void AddScore(int score)
	{
		SharedPreferences highscorePrefs = context.getSharedPreferences("highscores", Context.MODE_PRIVATE);
		SharedPreferences.Editor highscoreEdit = highscorePrefs.edit();

		// Get date and store in dateOutput
		SimpleDateFormat date = new SimpleDateFormat("d MMM yyyy"); 
		String dateOutput = date.format(new Date());
		
		// Store date + score
		String newScore = dateOutput + " - " +score;
		String previousScore = highscorePrefs.getString("highscores", "");
		
		highscoreEdit.putString("highscores", previousScore + "|" + newScore);
		highscoreEdit.commit();
	}
}
