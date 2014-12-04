package org.hexlet.testproject;

import android.graphics.Bitmap;

public class Platform extends Block{
	public boolean start = false;
	public float xMove;
	public  Platform(GameView gameView, Bitmap bmp, int x, int y, int xSpeed, int ySpeed)
	{
		super(gameView, bmp, x, y, xSpeed, ySpeed);
	}
	
	
	public void update() 
    {	
		if(start)origin.x = (int)xMove;
		if(origin.x < 0) origin.x=0;
		if(origin.x > gameView.getWidth() - width) origin.x = gameView.getWidth() - width;
    }
	public void startPosition()
	{
		origin.x = gameView.getWidth()/2 - width/2;
		origin.y = gameView.getHeight() - height*2;
		xMove = gameView.getWidth()/2 - width/2;
	}
	public void start()
	 {
		start = true;
	 }
	 
	 public void stop()
	 {
		start = false;
	 }
	 public Point getCenter(){
			
		center = new Point((int)(origin.x - width/2),(int)( origin.y - height/2));
	  	   
	    return center;
	}
	 
	 
//	 
//	 public boolean isCollision(Line ballLine)
//		{
//			initLines();
//			point = ballLine.intersect(arrayOfLines[1]); 
//				if(point != null)
//				{
//					points.add(point);
//				};
//			return true;
//		}
//		public Point pointCollision()
//		{
//			return points.get(0);
//		}
}
