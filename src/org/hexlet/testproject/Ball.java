package org.hexlet.testproject;

import android.graphics.Bitmap;

public class Ball extends Sprite {

	public boolean move;
	
	public  Ball(GameView gameView, Bitmap bmp, int x, int y)
	{
		super(gameView, bmp, x, y);
		this.stop();
	}
	
	
	 public void update() 
     {
  	   if (x >= gameView.getWidth() - width - xSpeed || x + xSpeed <= 0) 
         {
             xSpeed = -xSpeed;
             xSpeed = upperSpeed(xSpeed, 13);
             ySpeed = upperSpeed(ySpeed, 13);
         }
         if ( y + ySpeed <= 0) 
         {
             ySpeed = -ySpeed;
             ySpeed = upperSpeed(ySpeed, 13);
             xSpeed = upperSpeed(xSpeed, 13);
         }
         
         if(!move)
    	   {
    		   x = gameView.getWidth()/2 - width/2;
    		   y = gameView.getHeight()- height*5;
    	   } else 
    	   {
    		 x = x + xSpeed;
    		 y = y + ySpeed;
    	   }  
         
       
     }
	 
	 public void start()
	 {
		 xSpeed = 5;
		 ySpeed = -5;
		 move = true; 
	 }
	 
	 public void stop()
	 {
		 xSpeed = 0;
		 ySpeed = 0;
		 move = false;
		 
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
