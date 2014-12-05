package org.hexlet.testproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
 public class Sprite {
    /**Объект класса GameView*/
	 protected GameView gameView;
    
	 protected Bitmap bmp;
    
	 protected Point center;
	 protected Point origin;
	 
	 protected float xSpeed;
	 protected float ySpeed;

	 protected float width;
	 protected float height;
      
	 protected Line line1;
	 protected Line line2;
	 protected Line line3;
	 protected Line line4;
	 protected Line arrayOfLines[];
	 private boolean initLines = false;
	
		public Sprite(GameView gameView, Bitmap bmp, float x, float y, float xSpeed, float ySpeed) 
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
       
       protected float upperSpeed(float speed, float maxSpeed){
    	   
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
    		   center = new Point((origin.x - width/2),( origin.y - height/2));   
    	   } else 
    	   {
    		   return center;
    	   }
    	   
    	   return center;
       }
       
       public Point isCollision(Ball ball)
   	{
   		if(!initLines)initLines();
   	
   		return null;
   	}
       
       public void initLines()
   	{
   		
   	}
       public boolean isPointOfVerticalLine(Point point)
   	{
    	   return false;
   	}
       
       
       
       
       
}  