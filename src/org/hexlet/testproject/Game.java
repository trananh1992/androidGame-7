package org.hexlet.testproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.WindowManager;

@SuppressLint("WrongCall")
public class Game {

    private Sprite sprite;   
    private List<Sprite> sprites = new ArrayList<Sprite>();
    
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
	public int i;
	public void drawOnCanvas(Canvas canvas)
	{
		canvas.drawColor(Color.BLACK);
		
		
		if(ball.origin.y > gameView.getHeight() - platform.height - ball.height && ball.origin.x > platform.origin.x &&
				ball.origin.x + ball.width < platform.origin.x + platform.width)
		{
			ball.ySpeed = -ball.ySpeed;
		}
		
         ball.onDraw(canvas);
         platform.onDraw(canvas);
         
         if(!start)
         {
        	 platform.startPosition();
             ball.startPosition();	 
         }
         
	}
	
	
	
	private Platform createPlatform(){
    	bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.platform);
    	
    	return new Platform(gameView, bmp, 0, 0, 0, 0);
    }
    
	
    private Ball createBall() {
    	 bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.ball);
    	 
    	 return new Ball(gameView,bmp, 0, 0, 0, 0);
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
	
	
	
}
