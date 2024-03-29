package org.hexlet.testproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
@SuppressLint({ "WrongCall", "ClickableViewAccessibility" })
public class GameView extends SurfaceView {  

	public Game game;
    
    private SurfaceHolder holder;
    private DrawThread gameLoopThread;
    private GameView  gameView = this;
    
    public GameView(Context context) 
    {
          super(context);
          gameLoopThread = new DrawThread(this);
          holder = getHolder();
          holder.addCallback(new SurfaceHolder.Callback() 
          {
        	  	public void surfaceCreated(SurfaceHolder holder) 
        	  	{
        	  		if(gameLoopThread == null){
        	  			gameLoopThread = new DrawThread(gameView);
        	  		}
                     gameLoopThread.setRunning(true);
                     gameLoopThread.start();
        	  	}

              
        	  	public void surfaceChanged(SurfaceHolder holder, int format,
                            int width, int height) 
        	  	{
        	  	}
              
                public void surfaceDestroyed(SurfaceHolder holder) 
                {
                        boolean retry = true;
                        
                       	gameLoopThread.setRunning(false);
                       
                        while (retry) 
                        {
                               try 
                               {
                                     gameLoopThread.join();
                                     retry = false;
                               } catch (InterruptedException e) 
                               {
                               }
                        }
                        gameLoopThread = null;
                }                
          });  
          game = getGame();
          game.createObjects();
    }

    protected void onDraw(Canvas canvas) 
    {
    	game = getGame();
    	if(canvas != null)
    	{
    		game.drawOnCanvas(canvas);
    	}
    }
       
    public  Game getGame()
    {
    	if(game != null)
    	{
    		return game;
    	} else 
    	{
    		game = new Game(this);
    		return game;
    	}
    }
}
       
       
       
       
       
       
       
       
       
       
       
       