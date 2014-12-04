package org.hexlet.testproject;

public class Line {

	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int x3;
	private int y3;
	private int x4;
	private int y4;
	
	public int x;
	public int y;
	public Line(int x1, int y1, int x2, int y2){
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	
		
	}
	
	public Point intersect(Line line)
	{
		x3 = line.x1;
		y3 = line.y1;
		x4 = line.x2;
		y4 = line.y2;
		
		if (((y1-y2)*(x4-x3)-(y3-y4)*(x2-x1)) == 0 || (x4-x3) == 0) return null;
		
		x = -((x1*y2-x2*y1)*(x4-x3)-(x3*y4-x4*y3)*(x2-x1))/((y1-y2)*(x4-x3)-(y3-y4)*(x2-x1));
		y = ((y3-y4)*(-x)-(x3*y4-x4*y3))/(x4-x3);
		
		
		boolean firstLine = checkPointForLine(x1,y1,x2,y2,x,y);
		boolean secondLine = checkPointForLine(x3,y3,x4,y4,x,y);
		
		if(firstLine && secondLine) return new Point(x,y);

		return null;		
	}
	
	
	private boolean checkPointForLine(int x1, int y1, int x2, int y2, float X, int Y)
	{
		if(x1<= X && x2 >= X && y1 <= Y && y2 >= Y) return true;
		if(x1<= X && x2 >= X && y2 <= Y && y1 >= Y) return true;
		if(x2<= X && x1 >= X && y1 <= Y && y2 >= Y) return true;
		if(x2<= X && x1 >= X && y2 <= Y && y1 >= Y) return true;	
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}