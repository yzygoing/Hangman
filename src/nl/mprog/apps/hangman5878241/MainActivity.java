package nl.mprog.apps.hangman5878241;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// for debug purposes
		Log.d("onCreate", "test");
		
		// set buttons 'new game' and 'settings'
		final Button newgame = (Button) findViewById(R.id.button1);
		Log.d("init button newgame", "test");
		
		// TODO: final Button settings = (Button) findViewById(R.id.button2);
		
		// init new game
		newgame.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
	            Log.d("init new game", "test");
	            
			}

			// Load RandomWord from small.xml
			
			
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
