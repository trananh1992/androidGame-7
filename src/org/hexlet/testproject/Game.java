package org.hexlet.testproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

@SuppressLint("WrongCall")
public class Game {

    private Sprite sprite;   
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
		
		
		if (!blockCreated)
		{
			blockCreated = true;
			createBlocks();	
		}
		
		
		 if(!start)
         {
        	 platform.startPosition();
             ball.startPosition();	 
         }
		
		Line line = platform.getLine(ball.getCorrection());
		Point intersect = ball.getLine().intersect(line); 
		if(intersect != null)
		{
			ball.bounce(intersect, false);		
		}
		
         ball.onDraw(canvas);
         platform.onDraw(canvas);
        
         for(Block block : blocks)
         {
        	 block.onDraw(canvas);
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
		return points;
	} 
	
	
	
	
	
	
	
	
	
	
}
