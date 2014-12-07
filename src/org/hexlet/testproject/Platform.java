package org.hexlet.testproject;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;

public class Platform extends Block{
	public boolean start = false;
	private float xMove;
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
	 public Point getCenter()
	{
		 return new Point(origin.x + width/2.f,origin.y + height/2.f);
	}
	 
	public void move(float xMove)
	{
		this.xMove = xMove;
	}

	
	public void initLines()
   	{	   
   	   line1 = new Line(origin.x, origin.y, origin.x + width, origin.y);
   	   line2 = new Line(origin.x + width , origin.y, origin.x + width, origin.y + height);
	   line3 = new Line(origin.x, origin.y, origin.x, origin.y + height);

	   WeakReference<Platform> weakSelf = new WeakReference<Platform>(this);
	   line1.platform = weakSelf;
	   line2.platform = weakSelf;
	   line3.platform = weakSelf;
	  
	   WeakReference<Sprite> weakSelfSprite = new WeakReference<Sprite>(this);
	   line1.sprite = weakSelfSprite;
	   line2.sprite = weakSelfSprite;
	   line3.sprite = weakSelfSprite;
	   
	   arrayOfLines = new Line[]{line1, line2, line3};
   	}

	public Line[] getLines()
	{
		initLines();
	   		return arrayOfLines;
   	}
	
	public double getAngleFromPlatformToBallCenter(Point ballCenter, Line line)
	{
		double lineAngle;
		if(line.equals(line1)){
			lineAngle = Line.getAngle(ballCenter.x, ballCenter.y, getCenter().x, getCenter().y + 200.f);		
			lineAngle -= 90.f;	
		} else {
			lineAngle = Line.getAngle(line.x1, line.y1, line.x2, line.y2);
		}
		
		return lineAngle;
	}
	
	
	
	
	
	
	
	
	
	
	
}
