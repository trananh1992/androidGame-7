package org.hexlet.testproject;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

public class Block extends Sprite {

	private Line line1;
	private Line line2;
	private Line line3;
	private Line line4;
	private Line arrayOfLines[];
	private List<Point> points;
	private Point point;
	private boolean initLines = false;
	public Block(GameView gameView, Bitmap bmp, int x, int y, int xSpeed,
			int ySpeed) {
		super(gameView, bmp, x, y, xSpeed, ySpeed);
	}

	
	public void initLines()
	{
		line1 = new Line(origin.x, origin.y, origin.x + width, origin.y);
		line2 = new Line(origin.x + width, origin.y, origin.x + width, origin.y + height);
		line3 = new Line( origin.x + width, origin.y + height, origin.x, origin.y + height);
		line4 = new Line( origin.x, origin.y + height,  origin.x, origin.y);

		arrayOfLines = new Line[]{line1, line2, line3, line4};
	}
	
	
	public Point isCollision(Ball ball)
	{
		if(!initLines)initLines();

		points = new ArrayList<Point>();
		
		for (int i = 0; i < 4; i++)
		{
			point = ball.getLine().intersect(arrayOfLines[i]); 
			if(point != null)
			{
				points.add(point);
			};
		}
		Point nearestPoint = new Point(-200,-200);
		if(!points.isEmpty())
		{
			for (Point point : points)
			{
				if(ball.getCenter().calculateDistance(point) < ball.getCenter().calculateDistance(nearestPoint))
				{
					nearestPoint = point;
				}
			}
		}
		if(ball.getCenter().calculateDistance(nearestPoint) < 200) return nearestPoint;
		return null;
	}














}
