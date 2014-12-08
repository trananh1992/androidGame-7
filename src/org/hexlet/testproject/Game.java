package org.hexlet.testproject;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;

@SuppressLint("WrongCall")
public class Game {
    private ArrayList<Sprite> spritesForDelete;  
    private List<Sprite>nearObjects;
    private List<Block> blocks = new ArrayList<Block>();
    private List<Sprite> lives;
//    private Sprite pauseSprite;
    public boolean prepareForGame = false;
    public boolean start = false;
    public boolean showTitle = false;
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
	private int livesCount;
	private int score = 0;
	private int scoreForScreen = 0;
	private Paint paint;
	private SoundPool sounds;
//	private int borderSound;
	private int blockSound;
	private int deathSound;
	public int resultGame = 3;
	public Game(GameView gameView)
	{
		this.gameView = gameView;
		this.sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
//		this.borderSound = sounds.load(MainActivity.getAppContext(), R.raw.bouncefromborder, 1);
		this.deathSound = sounds.load(MainActivity.getAppContext(), R.raw.death, 1);
		this.blockSound = sounds.load(MainActivity.getAppContext(), R.raw.destroy, 1);
		this.resultGame = 3;
	}
	
	public void createObjects()
	{
		 ball = createBall();
         platform = createPlatform();    
	}
	
	public void drawOnCanvas(Canvas canvas)
	{
		canvas.drawColor(Color.BLACK);
//		pauseSprite = createPauseSprite();		
		
		
		if (!prepareForGame) prepareForGame();
		checkAlive();
		if (start)
		{	
			arrayOfLines = new ArrayList<Line>();
			
			addLinesFromNearestObjects();
			addBorders();
			addNewPlatformLines();
			
			spritesForDelete = ball.findSpritesWichIntersectsBall(arrayOfLines, accuracyOfCalculatingIntersect);
			deleteSprite();
		}
		
		updateScore(canvas);
        
		ball.onDraw(canvas);
        platform.onDraw(canvas);
        
        if(!blocks.isEmpty())
        {
        	for(Block block : blocks)
        	{ 
        		block.onDraw(canvas);
        	}	
        }    
        if(!lives.isEmpty()){
        	for(Sprite sprite : lives){
        		sprite.onDraw(canvas);
        	}
        }
        
        showTitle(canvas);
//        if (!start) pauseSprite.onDraw(canvas);
        }
	
	private void deleteSprite()
	{
		if(spritesForDelete != null )
		{
			for(Sprite sprite : spritesForDelete)
			{
				blocks.remove(sprite);
				score += 15;
				sounds.play(blockSound, 1, 1, 0, 0, 1.f);
			}
		} else if (ball.soundForBorder){
//			sounds.play(borderSound, 1, 1, 0, 0, 1.f);
			ball.soundForBorder = false;
		}
	}
	
	private void updateScore(Canvas canvas)
	{
		if (scoreForScreen < score){
			scoreForScreen ++;
		} else if (scoreForScreen > score ) scoreForScreen = 0;
		canvas.drawText(String.valueOf(scoreForScreen), 30, 50, getPaintForScore());
	}
	private Paint getPaintForScore()
	{
		if(paint != null)
		{
			return paint;
		} else {
			paint = new Paint();
			paint.setColor(Color.BLUE);
			paint.setStyle(Paint.Style.FILL);
			paint.setAntiAlias(true);
			paint.setTextSize(50);
			return paint;
		}
	}
	private void showTitle(Canvas canvas)
	{
		if(resultGame == 1){
			canvas.drawText("You win!", 50, 
						gameView.getHeight()/2, getPaintForTitle());
		} else if(resultGame == 2){
			canvas.drawText("You lose!", 50, 
					gameView.getHeight()/2, getPaintForTitle());
		}
	}
	private Paint getPaintForTitle()
	{
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);
		paint.setTextSize(150);
		return paint;
	}
	
	private void checkAlive()
	{
		if(ball.origin.y > gameView.getHeight()){
			livesCount --;
			lives.remove(0);
			sounds.play(deathSound, 1, 1, 0, 0, 1.f);
			
			prepareForGame = false;
		}
		if (livesCount < 1)
		{
			resultGame = 2;
			showTitle = true;
		}
		if (blocks.isEmpty())
		{
			resultGame = 1;
			ball.stop();
			showTitle = true;
		}
	}
    
	private void prepareForGame()
	{
		if(resultGame > 2)
		{
			platform.startPosition();	
			createBlocks();
			livesCount = 3;
			createLives();
			score = 0;
			resultGame = 0;
			showTitle = false;
		}
		ball.stop();
		platform.stop();
		ball.startPosition();
		start = false;
		prepareForGame = true;
	}
	
	public void start()
	{	
		ball.start();
		platform.start();
		start = true;	
	}
	public void pause()
	{
		ball.stop();
		platform.stop();
		start = false;
	}
	
	private Platform createPlatform(){
    	bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.platform);
    	
    	return new Platform(gameView, bmp, 0, 0, 0, 0);
    }
	private Ball createBall() {
   	 bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.ball);
   	 
   	 return new Ball(gameView,bmp, 0, 0, 0, 0);
   }
//	private Sprite createPauseSprite()
//	{
//		bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.pause);
//    	return new Platform(gameView, bmp, 0,0, 0, 0);	
//	}
	
	private void createBlocks()
	{
		blocks = new ArrayList<Block>();
		bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.block);	
		
		ArrayList<Point> points = calculatePointsForBlocks(bmp);
		
		for(Point point : points)
		{
			blocks.add(new Block(gameView, bmp, point.x, point.y, 0,0));
		}
	}
	
	private void createLives()
	{
		bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.heart);
		
		ArrayList<Point> points = new ArrayList<Point>();
		
		points.add(new Point(gameView.getWidth() - bmp.getWidth()/2 - bmp.getWidth()*3, bmp.getHeight() / 2));
		points.add(new Point(gameView.getWidth() - bmp.getWidth()/2 - bmp.getWidth()*2, bmp.getHeight() / 2));
		points.add(new Point(gameView.getWidth() - bmp.getWidth()/2 - bmp.getWidth(), bmp.getHeight() / 2));
		
		lives = new ArrayList<Sprite>();
		for(Point point : points)
		{
			lives.add(new Block(gameView, bmp, point.x, point.y, 0,0));
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
