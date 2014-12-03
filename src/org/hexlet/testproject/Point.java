package org.hexlet.testproject;

public class Point {

	public int x;
	public int y;
	
	public Point (int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	
	public int calculateDistance(Point point)
	{
		int distance;
		distance = (int)Math.sqrt(Math.pow(point.x - x,2) + Math.pow(point.y - y, 2));
		return distance;
	}
}


