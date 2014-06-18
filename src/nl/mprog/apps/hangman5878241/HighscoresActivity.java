package nl.mprog.apps.hangman5878241;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class HighscoresActivity extends Activity 
{
	
	// Global Variables
	Button newgame;
	Highscores hs;
	TextView test;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        hs = new Highscores(getApplicationContext()); 
        setContentView(R.layout.activity_highscores); 
        TextView test = (TextView)findViewById(R.id.textView2);

		// set highscore file
        int count = 0;
        test.setText("");
        for (Highscores.Score scr : hs.scoreStrings)
        {
        	// make sure only 10 highscores are in view
        	if (count >= 10)
        		break;
        	test.setText(test.getText() + "\n" + scr.toString());
        	count++;
        }
		
		// set New Game button
        newgame = (Button) findViewById(R.id.button1);
		
        // Binding Click event to Button
        newgame.setOnClickListener(new View.OnClickListener() 
        {
        	// start new intent
        	Intent newgameActivity = new Intent(getApplicationContext(), MainActivity.class);
        	
            public void onClick(View V) 
            {
                // Starting Main Activity
                startActivity(newgameActivity);
            }
        });
    }
}
