package org.hexlet.testproject;

import android.graphics.Bitmap;

public class Ball extends Sprite {

	
	
	public  Ball(GameView gameView, Bitmap bmp, int x, int y, int xSpeed, int ySpeed)
	{
		super(gameView, bmp, x, y, xSpeed, ySpeed);
		this.stop();
	}
	
	 public void update() 
     {
  	   if (origin.x >= gameView.getWidth() - width - xSpeed || origin.x + xSpeed <= 0) 
         {        
            bounce(true);
            
         }else if ( origin.y + ySpeed <= 0) 
         {
            bounce(false);
         }
        
  	   
   	 origin.x = origin.x + xSpeed;
     origin.y = origin.y + ySpeed;
         
         
       
     }
	 
	 public void bounce(boolean isVertical)
	 {
		 if(isVertical)
		 {
			 xSpeed = -xSpeed;
		 } else 
		 {
			 ySpeed = -ySpeed;
		 }
		 xSpeed = upperSpeed(xSpeed, 13);
         ySpeed = upperSpeed(ySpeed, 13);
	 }
	 
	 public void start()
	 {
		 xSpeed = 5;
		 ySpeed = -5;
	 }
	 
	 public void stop()
	 {
		 xSpeed = 0;
		 ySpeed = 0;		 
	 }
	 public void startPosition()
	{
		 origin.x = gameView.getWidth()/2 - width/2;
		 origin.y = gameView.getHeight() - height*4;
	}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
