package org.hexlet.testproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
 public class Sprite {
    /**Объект класса GameView*/
	 protected GameView gameView;
    
	 protected Bitmap bmp;
    
   
	 protected float x;
	 protected float y;
	 
	 protected int xSpeed;
	 protected int ySpeed;
    

    protected int currentFrame = 0;

    protected int width;
    protected int height;
      
       /**Конструктор*/
       public Sprite(GameView gameView, Bitmap bmp, int x, int y) 
       {
             this.gameView=gameView;
             this.bmp=bmp;
             this.xSpeed = x;
             this.ySpeed = y;
             this.width = bmp.getWidth();
             this.height = bmp.getHeight();
       }
 
       /**Перемещение объекта, его направление*/
       public void update() 
       {
    	 
          
         
       }

      /**Рисуем наши спрайты*/
       public void onDraw(Canvas canvas) 
       {
             update();
             canvas.drawBitmap(bmp, x , y, null);
       }
       
       protected int upperSpeed(int speed, int maxSpeed){
    	   
    	   if(speed  <  0 && speed > -maxSpeed)
    	   {
    		   speed --;
    		   
    	   } else if ( speed > 0 && speed < maxSpeed)
    	   {
    		   speed++;
    	   }
    		   
    	   return speed;
       }
       
       public Point getCenter(){
    	   Point center = new Point((int)(x - width/2),(int)( y - height/2));
    	   return center;
       }
       
       
       
       
       
       
       
       
       
       
       
       
       
}  