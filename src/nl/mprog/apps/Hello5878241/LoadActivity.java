package nl.mprog.apps.Hello5878241;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LoadActivity extends Activity{
Thread timer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_hangman);
		 timer=new Thread(){	
	        	public void rwn(){
	        	try{
	        		sleep(5000);
	        		
	        	}
	        	catch(InterruptedException e){
	        		
	        		e.printStackTrace();
	        	}
	        	finally
	        	{
	        		Intent nn = new Intent(LoadActivity.this, LoadActivity.class);
	        		startActivity(nn);
	        	}
	        }
	     };
	        	timer.start();
	}

}
