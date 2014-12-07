package org.hexlet.testproject;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
@SuppressLint("WrongCall")
public class DrawThread extends Thread
{

       static final long FPS = 50;
       private GameView view; 
       private boolean running = false;

       public DrawThread(GameView view) 
       {
             this.view = view;
       }
 
       public void setRunning(boolean run) 
       {
             running = run;
       }
 
       @Override
       public void run() 
       {
             long ticksPS = 1000 / FPS;
             long startTime;
             long sleepTime;
             while (running) {
                    Canvas canvas = null;
                    startTime = System.currentTimeMillis();
                    try {
                           canvas = view.getHolder().lockCanvas();
                           synchronized (view.getHolder()) {
                                  view.onDraw(canvas);
                           }
                    } finally {
                           if (canvas != null) {
                                  view.getHolder().unlockCanvasAndPost(canvas);
                           }
                    }
                    sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
                    try {
                           if (sleepTime > 0)
                                  sleep(sleepTime);
                           else
                                  sleep(10);
                    } catch (Exception e) {}
             }
       }
}