package org.hexlet.testproject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.graphics.Bitmap;

public class Ball extends Sprite {

	private boolean bounce = false;
	public float accelerate = 0.2f;
	public float maxSpeed = 13.f;
	private long timeLastBounce = 0;
	
	public  Ball(GameView gameView, Bitmap bmp, int x, int y, int xSpeed, int ySpeed)
	{
		super(gameView, bmp, x, y, xSpeed, ySpeed);
		this.stop();
	}
	
	 public void update() 
     {
		 if(!bounce)
		 {
			 origin.x += xSpeed;
			 origin.y += ySpeed;
		 }
		 bounce = false;
     }
	 
	 public void start()
	 {
		 xSpeed = 7;
		 ySpeed = -8;
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
	public Point getCenter()
	{	
  	   return new Point(origin.x + width/2.f,origin.y + height/2.f);
	}   
	
	 public Sprite makeBounceFromLinesWithAccuracy(ArrayList<Line> lines, float accuracy)
	 {
		 float dx,dy;
		 dx = 0;
		 dy = 0;
		 
		 while(Math.abs(dx) < Math.abs(xSpeed) && Math.abs(dy) < Math.abs(ySpeed))
		 { 
			 dx = dx + (float)xSpeed/accuracy;
			 dy = dy + (float)ySpeed/accuracy;
			 
			 for (Line line : lines)
			 {
				 if(checkIntersectCircleForLine(getCenter().x + dx, getCenter().y + dy, width/2, line)) 
				 {
					 
					 double lineAngle = Line.getAngle(line.x1, line.y1, line.x2, line.y2);
					 
					 WeakReference<Sprite> sprite = line.sprite;
					 if(sprite != null)
					 { 
						 //if intersect with anySprite
						 Line lineToSpriteCenter = new Line(getCenter().x + dx, getCenter().y,
								 		sprite.get().getCenter().x, sprite.get().getCenter().y);
						 
						 if(lineToSpriteCenter.intersect(line)) 
						 {
							//if intersect with Platform
							 if(line.platform != null && line.platform.get().getClass().equals(Platform.class))
							 { 
								 return makeBounceFromPlatform(sprite, dx, dy, line);
								 
							 } else {
								//if intersect with Block
								 if(System.currentTimeMillis() - timeLastBounce < 25)
								 {
									return null;
								 }
								 bounceBall(lineAngle, dx,dy);
								 return sprite.get();
							 } 
						 }
						 continue;
					 }
					 bounceBall(lineAngle, dx,dy);
					 return null;
				 }
			 }
			
		 }
		 return null;
	 }
	 
	 
	 
	 
	 private Sprite makeBounceFromPlatform(WeakReference<Sprite> sprite, float dx, float dy, Line intersectLine)
	 {
		 if(System.currentTimeMillis() - timeLastBounce > 45)
		 {
			 double lineAngle;
			 Platform platform = (Platform)sprite.get();
		 
			 lineAngle = platform.getAngleFromPlatformToBallCenter(new Point(getCenter().x + dx,
				 														getCenter().y + dy),
				 														intersectLine);
			 bounceBall(lineAngle, dx,dy);
			 
		 }
		 return null;
	 }
	 
	 
	 private void bounceBall(double lineAngle, float dx, float dy)
	 {
		 double ballAngle, ballSpeed, nextBallAngle; 
		 if(System.currentTimeMillis() - timeLastBounce < 45)
		 {
			return;
		 }
		 ballAngle = Line.getAngle(0,0,dx,dy);   							
		 nextBallAngle = 2.f * lineAngle - ballAngle;
		 
		 ballSpeed = Math.sqrt(Math.pow(xSpeed, 2) + Math.pow(ySpeed, 2)) + accelerate; 	
		 
		 if(ballSpeed > maxSpeed) ballSpeed = maxSpeed;
		
		 nextBallAngle = Math.toRadians(nextBallAngle);
		 xSpeed = (float)(ballSpeed * Math.cos(nextBallAngle));
		 ySpeed = (float)(ballSpeed * Math.sin(nextBallAngle)); 
		 
		 origin.x = origin.x + dx;
		 origin.y = origin.y + dy;
		 timeLastBounce = System.currentTimeMillis();
		 bounce = true;
	 }
	 
	 
	 private boolean checkIntersectCircleForLine(float centerx, float centery, double radius, Line line)
	 {
		 float dx,dy,a,x01,x02,y01,y02;
		 double b, c;
		 boolean result = false;
		 x01 = line.x1 - centerx;
		 y01 = line.y1 - centery;
		 x02 = line.x2 - centerx;
		 y02 = line.y2 - centery;
		 dx = x02 - x01;
		 dy = y02 - y01;
		 a = dx * dx + dy * dy;
		 b = 2.f * (x01 *dx + y01 * dy);
		 c = x01 * x01  + y01 * y01 - radius * radius;
		 
		 if(-b < 0)
		 {
			 result = (c < 0);	 
		 } else if(-b < (2.f * a))
		 {
			 result = (4.f * a * c - b * b < 0);
		 } else 
		 {
			 result = (a + b + c < 0);
		 }
		 return result;
	 }
	 
	 
	 
}
