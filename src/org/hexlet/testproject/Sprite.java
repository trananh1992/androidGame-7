package org.hexlet.testproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
 public class Sprite {
    /**Объект класса GameView*/
	 protected GameView gameView;
    
	 protected Bitmap bmp;
    
	 protected Point center;
	 protected Point origin;
	 
	 protected int xSpeed;
	 protected int ySpeed;

	 protected int width;
	 protected int height;
      
	 	public Sprite(GameView gameView, Bitmap bmp, int x, int y, int xSpeed, int ySpeed) 
       {
	 		
             this.gameView=gameView;
             this.bmp=bmp;
             this.origin = new Point(x,y); 
             this.xSpeed = xSpeed;
             this.ySpeed = ySpeed;
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
             canvas.drawBitmap(bmp, origin.x , origin.y, null);
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
    	   if(center == null)
    	   {
    		   center = new Point((int)(origin.x - width/2),(int)( origin.y - height/2));   
    	   } else 
    	   {
    		   return center;
    	   }
    	   
    	   return center;
       }
       
       
       
       
       
       
       
       
       
       
       
       
       
}  