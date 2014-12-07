package org.hexlet.testproject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.graphics.Bitmap;

public class Ball extends Sprite {

	private boolean bounce = false;
	public float accelerate = 0.2f;
	public float maxSpeed = 13.f;
	private long timeLastBounce = 0;
	private float dx,dy;
	
	
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
	
	 public Sprite findLinesWithAccuracy(ArrayList<Line> lines, float accuracy)
	 {
		 ArrayList<Line> linesWithIntersect = new ArrayList<Line>();
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
					 
					linesWithIntersect.add(line);
				 }
			 }
			if(linesWithIntersect.size() > 0){
				
				Line lineForBounce = findLineForBounce(linesWithIntersect);
				
				return makeBounceAndGetSpriteFromLine(lineForBounce);
			}
		 }
		 return null;
	 }
	 
	 
	 
	 private Sprite makeBounceAndGetSpriteFromLine(Line line)
	 {
		 if(line.platform != null){
			 
			 makeBounceFromPlatform(line);
			 return null;
			 
		 }else if (line.sprite != null){
			 
			 bounceBall(line.getAngle());
			 return line.sprite.get();
			 
		 } else {
			 
			 bounceBall(line.getAngle());
			 return null;
		 }
	 }
	 
	 
	 private Line findLineForBounce(ArrayList<Line> lines)
	 {
		 if(lines.size() == 1)
		 {
			 return lines.get(0);
			 
		 } else if (lines.size() == 2)
		 {
			 return pickLineFromTwoLines(lines);
		 } else if (lines.size() == 3 )
		 {
			bounceFromPlatformNearWall(lines); 
		 } else if (lines.size() > 3){
			 
			 return findLineFromArrayWithManyLines(lines);
		 }
		 return null;
	 } 
	 
	 private Line findLineFromArrayWithManyLines(ArrayList<Line> lines)
	 {
		 for (Line line1 : lines){
			 for(Line line2 : lines){
				
				 if(line1.getAngle() == line2.getAngle()) return line1;
			 }
		 }
		 return null;
	 }
	 
	 
	 private void bounceFromPlatformNearWall(ArrayList<Line> lines)
	 {
		 double lineAngle = Line.getAngle(getCenter().x, getCenter().y, 0,0);
		 lineAngle += 180.f;
		 bounceBall(lineAngle);
	 }
	 
	 private Line pickLineFromTwoLines(ArrayList<Line> lines)
	 {
		 Line line1 = lines.get(0);
		 Line line2 = lines.get(1);
		 WeakReference<Sprite> sprite1 = line1.sprite;
		 WeakReference<Sprite> sprite2 = line2.sprite;
		 
		 if(sprite1 != null && sprite2 != null)
		 { 
			 Line lineToSpriteCenter = new Line(getCenter().x + dx, getCenter().y + dy,
				 		sprite1.get().getCenter().x, sprite1.get().getCenter().y); 
			 
			 if(lineToSpriteCenter.intersect(line1)) 
			 {
				 return line1;
			 } else {
				 return line2;
			 }
		 } else if (sprite1 == null && sprite2 == null){
			 double angle = currentBallAngle() + 90.f;
			 bounceBall(angle);
			 return null;
		 } else {
			 return lines.get(0);
		 }
	 }
	 
	 private double currentBallAngle()
	 {
		 return Line.getAngle(getCenter().x, getCenter().y,getCenter().x + xSpeed, getCenter().y + ySpeed);
	 }
	 
	 
	 
	 private Sprite makeBounceFromPlatform(Line line)
	 {
		 if(currentBallAngle() > 0  &&  System.currentTimeMillis() - timeLastBounce > 45)
		 {
			 double lineAngle;
			 Platform platform = line.platform.get();
		 
			 lineAngle = platform.getAngleFromPlatformToBallCenter(new Point(getCenter().x + dx,
				 														getCenter().y + dy),
				 														line);
			 bounceBall(lineAngle);
		 }
		 return null;
	 }
	 
	 
	 private void bounceBall(double lineAngle)
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
