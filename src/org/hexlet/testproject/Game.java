package org.hexlet.testproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	private GameView gameView;
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
			blockCreated = true;
			createBlocks();	
		}
		
		if (start)
		{
			int distance;
			nearObjects = new ArrayList<Sprite>();
			if(!blocks.isEmpty())
			{
				for(Block block : blocks)
				{
					distance = block.getCenter().calculateDistance(ball.getNextCenter());
					if(distance < calculateMaxDistance(block))
					{
						nearObjects.add(block);	
					}
				}
				if(!nearObjects.isEmpty())
				{
					Point nearestPoint = new Point(-200,-200);
					for(Sprite sprite : nearObjects)
					{
						Point point = sprite.isCollision(ball);
						if(point != null && ball.getCenter().calculateDistance(point) < ball.getCenter().calculateDistance(nearestPoint))
						{
							nearestPoint = point;
							spriteForDelete = sprite;
						}
					}
					blocks.remove(spriteForDelete);
				}	
			}
		}
		
		
		
		
		
		
		Line line = platform.getLine(ball.getCorrection());
		Point intersect = ball.getLine().intersect(line); 
		if(intersect != null)
		{
			ball.bounce(intersect, false);		
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
	
	
	
	
    
	
    
    
    public static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
	
	
	public void start()
	{
		start = true;
		ball.start();
		platform.start();
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
		
		
		int positionX = (gameView.getWidth() - horizontalCount * bmp.getWidth())/2;
		int positionY = bmp.getWidth();
		
		
		
		for (int i = 0; i < horizontalCount; i++)
		{
			for (int j = 0; j < verticalCount; j++)
			{
				points.add(new Point(positionX, positionY));
				positionY += bmp.getHeight() + 2;
			}
			positionX += bmp.getWidth()+2;
			positionY = bmp.getWidth();
		}
		
//		points.add(new Point(gameView.getWidth()/2,gameView.getHeight()/2));
		
		return points;
	} 
	
	
	private int calculateMaxDistance(Block block)
	{
		int speed = (int) Math.sqrt(Math.pow(ball.xSpeed, 2) + Math.pow(ball.ySpeed, 2));
		int distance = speed + ball.width/2 + block.bmp.getWidth();
		return distance;
	}
	
	
	
	
	
	
	
}
