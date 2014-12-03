package org.hexlet.testproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
 public class MainActivity extends Activity implements OnTouchListener {

	 private GameView gm;
	 public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);

        gm = new GameView(this);
        gm.setOnTouchListener(this);
        
        
        setContentView(gm);
    }
    @Override
	public boolean onTouch(View v, MotionEvent event) {
		
		float x = event.getX();
		    
		    switch (event.getAction()) 
		    {
		    case MotionEvent.ACTION_DOWN: 
		    	gm.ball.start();
		      break;
		    case MotionEvent.ACTION_MOVE: 
		    	gm.platform.xMove = x - gm.platform.width/2;
		      break;
		   
		  }
		    
		    return true;
	}
}