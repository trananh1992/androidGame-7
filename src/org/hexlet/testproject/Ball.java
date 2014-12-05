package org.hexlet.testproject;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class Ball extends Sprite {

	private boolean bounce = false;
	public float accelerate = 0.2f;
	public float maxSpeed = 12.f;
	public  Ball(GameView gameView, Bitmap bmp, int x, int y, int xSpeed, int ySpeed)
	{
		super(gameView, bmp, x, y, xSpeed, ySpeed);
		this.stop();
	}
	
	 public void update() 
     {
//  	   if (origin.x >= gameView.getWidth() - width - xSpeed || origin.x + xSpeed <= 0) 
//         {        
//            changeDirection(true);
//            
//         }else if ( origin.y + ySpeed <= 0) 
//         {
//            changeDirection(false);
//         }
//        
//  	   if (!bounce)
//  	   {
//  		 origin.x = origin.x + (float)xSpeed;
//  	     origin.y = origin.y + (float)ySpeed;
//  	   } else 
//  	   {
//  		   bounce = false;
//  	   }
//   	     
//  	   if(origin.y > 1300)origin.y = 200;
//         
//       
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
		 xSpeed = upperSpeed(xSpeed, 5);
         ySpeed = upperSpeed(ySpeed, 5);
	 }
	 
	 public void start()
	 {
		 xSpeed = 16;
		 ySpeed = 15;
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
		
  	   center = new Point(origin.x + width/2.f,origin.y + height/2.f);
  	   
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
	
	public Point getNextCenter()
	 {
		 return new Point(center.x + xSpeed, center.y + ySpeed);
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
	 public double getAngle(float x1, float y1, float x2, float y2)
		{
			return (Math.atan2(y2 - y1, x2 - x1)) * 180 / Math.PI;
		}

	 public boolean makeBounceFromLinesWithAccuracy(ArrayList<Line> lines, float accuracy)
	 {
		 float dx,dy;
		 dx = 0;
		 dy = 0;
		 

		 while(Math.abs(dx) < Math.abs(xSpeed) && Math.abs(dy) < Math.abs(ySpeed))
		 { 
			 dx = dx + (float)xSpeed/accuracy;
			 dy = dy + (float)ySpeed/accuracy;
			 
//			 boolean sign = true;	 
//			 if(sign)
//			 {
//				if(xSpeed < 0) dx = -dx;
//				if(ySpeed < 0) dy = -dy;
//				sign = false;
//			 }
			 
			 
			 
			 
			 
			 
//			 boolean stop = false;
//			 if(origin.x > gameView.getWidth() - xSpeed - width)
//			 {
//				 stop = true;
//			 }
//			 if (stop)stop = false;
//			if(origin.y > 1122){
//			 stop = true;
//		 	}
			 for (Line line : lines)
			 {
				 
				 if(checkIntersectCircleForLine(getCenter().x + dx, getCenter().y + dy, width/2, line))
				 {
					 double a,b,c,y;
					 
					 a = getAngle(0,0,dx,dy);
					 b = getAngle(line.x1, line.y1, line.x2, line.y2);
					 y = 2.f * b - a;
					 c = Math.sqrt(Math.pow(xSpeed, 2) + Math.pow(ySpeed, 2)) + accelerate;
					 
					 if(c > maxSpeed) c = maxSpeed;
					 y = Math.toRadians(y);
					 xSpeed = (float)(c * Math.cos(y));
					 ySpeed = (float)(c * Math.sin(y)); 
					
					 
					 origin.x = origin.x + dx;
					 origin.y = origin.y + dy;
					 return true;
				 }
			 }
		 }
		 return false;
	 }
	 
	 
	 
	 
	 
}
