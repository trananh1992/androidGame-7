package org.hexlet.testproject;

import android.graphics.Bitmap;

public class Platform extends Sprite{
	private boolean start = false;
	public float xMove;
	public  Platform(GameView gameView, Bitmap bmp, int x, int y)
	{
		super(gameView, bmp, x, y);
		
	}
	
	
	public void update() 
    {
		if(!start)
		{
			x = xMove;
			if(x < 0)x=0;
			if(x > gameView.getWidth() - width) x = gameView.getWidth() - width;
			y = gameView.getHeight() - height*2;
		}
 	  
        
        
        
      
    }
	
}
