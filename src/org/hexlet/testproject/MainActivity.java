package org.hexlet.testproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
 @SuppressLint("ClickableViewAccessibility")
public class MainActivity extends Activity implements OnTouchListener {
	 private static Context context;
	 private GameView gameView;
	 public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
        MainActivity.context = getApplicationContext();
        gameView = new GameView(this);
        gameView.setOnTouchListener(this);
        
        setContentView(gameView);
    }
    @Override
	public boolean onTouch(View v, MotionEvent event) {
		
		float x = event.getX();
		    
		    switch (event.getAction()) 
		    {
		    case MotionEvent.ACTION_DOWN: 

		    	if(!gameView.game.start) 
		    	{
		    		gameView.game.start();	
		    	} else if (event.getY() < gameView.getHeight()/2){
		    		gameView.game.pause();
		    	} 
		    	if(gameView.game.resultGame != 0 && gameView.game.showTitle) 
		    	{
		    		gameView.game.resultGame = 3;
		    		gameView.game.prepareForGame = false;
		    	}
		    
		      break;
		    case MotionEvent.ACTION_MOVE: 
		    	gameView.game.platform.move(x - gameView.game.platform.width/2);
		      break;
		   
		  }
		    
		    return true;
	}
    
    public static Context getAppContext() {
        return MainActivity.context;
    }
}