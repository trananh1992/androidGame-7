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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
@SuppressLint({ "WrongCall", "ClickableViewAccessibility" })
public class GameView extends SurfaceView {  

    private Bitmap bmp;
    private SurfaceHolder holder;

    private DrawThread gameLoopThread;
    

    private Sprite sprite;   
    private List<Sprite> sprites = new ArrayList<Sprite>();
    
    public Platform platform;
    public Ball ball;
    
    
    public GameView(Context context) 
    {
          super(context);
          gameLoopThread = new DrawThread(this);
          holder = getHolder();
          
          /*Рисуем все наши объекты и все все все*/
          holder.addCallback(new SurfaceHolder.Callback() 
          {
        	  	public void surfaceCreated(SurfaceHolder holder) 
        	  	{
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
                }                
          });
          

          ball = createBall();

          platform = createPlatform();
          
    }

    /**Функция рисующая все спрайты и фон*/
    protected void onDraw(Canvas canvas) 
    {
          canvas.drawColor(Color.BLACK);
         
          
          if(ball.y > this.getHeight() - platform.height - ball.height && ball.x > platform.x &&
        		  ball.x + ball.width < platform.x + platform.width)
          {
        	  ball.ySpeed = -ball.ySpeed;
          }
          
          
          ball.onDraw(canvas);
          platform.onDraw(canvas);
    }
    
    private Platform createPlatform(){
    	bmp = BitmapFactory.decodeResource(getResources(), R.drawable.platform);
    	
    	return new Platform(this, bmp, 0, 0);
    }
    
    private Ball createBall() {
    	 bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
    	 
    	 return new Ball(this,bmp, 0, 0);
    	}
    
    public static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
 
	
}
       
       
       
       
       
       
       
       
       
       
       
       