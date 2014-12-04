package org.hexlet.testproject;

import android.graphics.Bitmap;

public class Ball extends Sprite {

	private boolean bounce = false;
	
	public  Ball(GameView gameView, Bitmap bmp, int x, int y, int xSpeed, int ySpeed)
	{
		super(gameView, bmp, x, y, xSpeed, ySpeed);
		this.stop();
	}
	
	 public void update() 
     {
  	   if (origin.x >= gameView.getWidth() - width - xSpeed || origin.x + xSpeed <= 0) 
         {        
            changeDirection(true);
            
         }else if ( origin.y + ySpeed <= 0) 
         {
            changeDirection(false);
         }
        
  	   if (!bounce)
  	   {
  		 origin.x = origin.x + xSpeed;
  	     origin.y = origin.y + ySpeed;
  	   } else 
  	   {
  		   bounce = false;
  	   }
   	     
  	   if(origin.y > 1300)origin.y = 200;
         
       
     }
	 
	 public void changeDirection(boolean isVertical)
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
		 xSpeed = 3;
		 ySpeed = 20;
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
//		 origin.x = 200;
//		 origin.y = 200;
	}
	public Point getCenter(){
		
  	   center = new Point((int)(origin.x - width/2),(int)( origin.y - height/2));
  	   
  	   return center;
	}   
	public Line getLine()
	{
		center = getCenter();
		int i = getDirection();
		
		switch(i)
		{
		case 0:
			return new Line(origin.x + width, origin.y, origin.x + width + xSpeed, origin.y + ySpeed);
		case 1:
			return new Line(origin.x, origin.y, origin.x + xSpeed, origin.y + ySpeed);
		case 2:
			return new Line(origin.x, origin.y + height,origin.x + xSpeed, origin.y + height + ySpeed);
		case 3:
			return new Line(origin.x + width, origin.y + height,origin.x + xSpeed + width, origin.y + height + ySpeed);
		}
		
		
		
		return new Line(center.x, center.y, center.x + xSpeed, center.y + ySpeed);
	}
	 
	public void bounce(Point intersect, boolean isVertical)
	{
		bounce = true;
		int i = getDirection();
		
		switch(i)
		{
		case 0:
			origin.x = intersect.x - width;
	 	    origin.y = intersect.y;
	 	    break;
		case 1:
			origin.x = intersect.x;
	 	    origin.y = intersect.y;
	 	    break;
		case 2:
			origin.x = intersect.x;
	 	    origin.y = intersect.y - height;
	 	    break;
		case 3:
			origin.x = intersect.x - width;
	 	    origin.y = intersect.y - height;
	 	    break;
		}
 	    changeDirection(isVertical);
	}
	
	public int getDirection()
	{
//		direction = 0 up, 1 left, 2 down, 3 right,
		double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
		int direction = (int) Math.round(dirDouble) % 4;
		return direction;
	}
	public int getCorrection()
	{
		int i = getDirection();
		int correction = 0;
		switch(i)
		{
		case 0:
			correction = 0;
	 	    break;
		case 1:
			correction = 0;
	 	    break;
		case 2:
	 	    correction = -width/2;
	 	    break;
		case 3:
			correction = width/2;
	 	    break;
		}
	 return correction;
	}
	 
	 public Point getNextCenter()
	 {
		 return new Point(center.x + xSpeed, center.y + ySpeed);
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
