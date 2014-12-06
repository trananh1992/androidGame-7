package org.hexlet.testproject;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.graphics.Canvas;
 public class Sprite {
 
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
 
       public void update() 
       {
    	   
       }
       public void onDraw(Canvas canvas) 
       {
             update();
             canvas.drawBitmap(bmp, origin.x , origin.y, null);
       }
       
       public Point getCenter(){
    	   if(center == null)
    	   {
    		   center = new Point(origin.x + width/2.f,origin.y + height/2.f);
    	  	   return center;   
    	   } else 
    	   {
    		   return center;
    	   }
       }
       
       public Line[] getLines()
   	{
   		if(!initLines)initLines();
   		return arrayOfLines;
   	}
       
       public void initLines()
   	{	   
   	   line1 = new Line(origin.x, origin.y, origin.x + width, origin.y);
   	   line2 = new Line(origin.x + width, origin.y, origin.x + width, origin.y + height);
       line3 = new Line( origin.x + width, origin.y + height, origin.x, origin.y + height);
	   line4 = new Line(origin.x, origin.y, origin.x, origin.y + height);
	   
	   WeakReference<Sprite> weakSelf = new WeakReference<Sprite>(this);
	   line1.sprite = weakSelf;
	   line2.sprite = weakSelf;
	   line3.sprite = weakSelf;
	   line4.sprite = weakSelf;
	   
	   arrayOfLines = new Line[]{line1, line2, line3, line4};
	   initLines = true;
   	}
      
       
       
       
       
       
}  