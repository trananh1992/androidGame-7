package org.hexlet.testproject;

import android.graphics.Bitmap;

public class Block extends Sprite {

	public Block(GameView gameView, Bitmap bmp, float x, float y, float xSpeed,
			float ySpeed) {
		super(gameView, bmp, x, y, xSpeed, ySpeed);
	}
}
