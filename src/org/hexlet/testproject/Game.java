package org.hexlet.testproject;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

@SuppressLint("WrongCall")
public class Game {

    private Sprite spriteForDelete;  
    private List<Sprite>nearObjects;
    private List<Block> blocks = new ArrayList<Block>();
    public boolean blockCreated = false;
    public boolean start = false;
    public Platform platform;
    public Ball ball;
    private Bitmap bmp;
    private float accuracyOfCalculatingIntersect = 10.f;
    ArrayList<Line> arrayOfLines;
    private boolean initBorders = false; 
	private GameView gameView;
	private Line borderLine1;
	private Line borderLine2;
	private Line borderLine3;
	public Game(GameView gameView)
	{
		this.gameView = gameView;
	}
	
	public void createObjects()
	{
		 ball = createBall();
         platform = createPlatform();    
	}
	
	public void drawOnCanvas(Canvas canvas)
	{
		canvas.drawColor(Color.BLACK);
		
		if(!start)
        {
			platform.startPosition();
			ball.startPosition();	 
        }
		if (!blockCreated)
		{
			createBlocks();
			blockCreated = true;
		}
		
		if (start)
		{
			arrayOfLines = new ArrayList<Line>();
			
			addLinesFromNearestObjects();
			addBorders();
			addNewPlatformLines();
			
			spriteForDelete = ball.findLinesWithAccuracy(arrayOfLines, accuracyOfCalculatingIntersect);
			deleteSprite();
		}
		
         ball.onDraw(canvas);
         platform.onDraw(canvas);
         if(!blocks.isEmpty())
         {
        	 for(Block block : blocks)
             { 
            	 block.onDraw(canvas);
             }
         }    
	}
	
	public void deleteSprite()
	{
		if(spriteForDelete != null)
		{
			blocks.remove(spriteForDelete);
			spriteForDelete = null;
		}
	}
    
	public void start()
	{	
		ball.start();
		platform.start();
		start = true;
	}
	
	private Platform createPlatform(){
    	bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.platform);
    	
    	return new Platform(gameView, bmp, 0, 0, 0, 0);
    }
	private Ball createBall() {
   	 bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.ball);
   	 
   	 return new Ball(gameView,bmp, 0, 0, 0, 0);
   }
	
	private void createBlocks()
	{
		bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.block);	
		
		ArrayList<Point> points = calculatePointsForBlocks(bmp);
		
		for(Point point : points)
		{
			blocks.add(new Block(gameView, bmp, point.x, point.y, 0,0));
		}
	}
	
	private ArrayList<Point> calculatePointsForBlocks(Bitmap bmp)
	{
		ArrayList<Point> points = new ArrayList<Point>();
		
		int horizontalCount = gameView.getWidth() / bmp.getWidth();
		int verticalCount = (gameView.getHeight() / 2) / bmp.getHeight();
		
		
		float positionX = (gameView.getWidth() - horizontalCount * bmp.getWidth())/2;
		float positionY = bmp.getWidth();
		
		
		
		for (int i = 0; i < horizontalCount; i++)
		{
			for (int j = 0; j < verticalCount; j++)
			{
				points.add(new Point(positionX, positionY));
				positionY += bmp.getHeight() +1;
			}
			positionX += bmp.getWidth() +1;
			positionY = bmp.getWidth();
		}
		return points;
	} 
	
	
	private double calculateMaxDistance(Block block)
	{
		double speed = Math.sqrt(Math.pow(ball.xSpeed, 2) + Math.pow(ball.ySpeed, 2));
		double distance = speed + ball.width/2 + block.bmp.getWidth()/2 + 1;
		return distance;
	}
	
	
	public void findNearestObjects()
	{
		double distance;
		nearObjects = new ArrayList<Sprite>();
		if(!blocks.isEmpty())
		{
			for(Block block : blocks)
			{
				distance = block.getCenter().calculateDistance(ball.getCenter());
				if(distance < calculateMaxDistance(block))
				{
					nearObjects.add(block);	
				}
			}
		}
	}
	
	public void addLinesFromNearestObjects()
	{
		findNearestObjects();
		if(!nearObjects.isEmpty())
		{
			
			for(Sprite sprite : nearObjects)
			{
				Line[] lines = sprite.getLines();
				for (int i =  0; i < lines.length; i++)
				{
					arrayOfLines.add(lines[i]);
				}
			}
		}
	}
	
	
	private void addNewPlatformLines()
	{
		Line[] lines = platform.getLines();
		for (int i =  0; i < lines.length; i++)
		{
			arrayOfLines.add(lines[i]);
		}
	}
	
	
	private void addBorders()
	{
		if (!initBorders)
		{
			borderLine1 = new Line(0 , gameView.getHeight(), 0, 0);
			borderLine2 = new Line(0 , 0, gameView.getWidth(), 0);
			borderLine3 = new Line(gameView.getWidth(), 0, gameView.getWidth(), gameView.getHeight());
			
			initBorders = true;
		}
		arrayOfLines.add(borderLine1);
		arrayOfLines.add(borderLine2);
		arrayOfLines.add(borderLine3);
	}
}
