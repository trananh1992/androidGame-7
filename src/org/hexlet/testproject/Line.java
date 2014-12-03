package org.hexlet.testproject;

import android.util.Log;
public class Line {

	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int x3;
	private int y3;
	private int x4;
	private int y4;
	public float x;
	public float y;
	public Line(int x1, int y1, int x2, int y2){
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y1;
		
	}
	
	public boolean intersect(Line line)
	{
		x3 = line.x1;
		y3 = line.y1;
		x4 = line.x2;
		y4 = line.y2;
		
		x = ((x1*y2-x2*y1)*(x4-x3)-(x3*y4-x4*y3)*(x2-x1))/((y1-y2)*(x4-x3)-(y3-y4)*(x2-x1));
		y = ((y3-y4)*x-(x3*y4-x4*y3))/(x4-x3);
		
		if (x1<= x && x2 >= x && x3 <= x && x4 >= x || y1 <= y && y2 >= y && y3 <= y && y4 >= y){
			return true;
		}
		return false; 
	}
	
	
	
	
	
	
	
	
	
	
	
	public void Foo(){
		
//		Log.d("ADebugTag", "Value: " + Float.toString(myFloatVar));		
	}
	
	
	
}