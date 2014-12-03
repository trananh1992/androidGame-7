package org.hexlet.testproject;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
@SuppressLint("WrongCall")
public class DrawThread extends Thread
{
       /**Наша скорость в мс = 10*/
       static final long FPS = 50;
      
       /**Объект класса GameView*/
       private GameView view; 

       /**Задаем состояние потока*/
       private boolean running = false;
      
       /**Конструктор класса*/
       public DrawThread(GameView view) 
       {
             this.view = view;
       }
 
        /**Задание состояния потока*/
       public void setRunning(boolean run) 
       {
             running = run;
       }
 
       /** Действия, выполняемые в потоке */
 
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