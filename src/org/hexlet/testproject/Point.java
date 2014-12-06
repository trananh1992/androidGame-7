package org.hexlet.testproject;

public class Point {

	public float x;
	public float y;
	
	public Point (float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double calculateDistance(Point point)
	{
		double distance;
		distance = Math.sqrt(Math.pow(point.x - x,2) + Math.pow(point.y - y, 2));
		return distance;
	}
}


