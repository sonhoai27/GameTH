package com.sonhoai.sonho.gameth.model;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.sonhoai.sonho.gameth.main.Assets;
import com.sonhoai.sonho.gameth.main.GameMainActivity;
import com.sonhoai.sonho.gameth.main.GameView;
import com.sonhoai.sonho.gameth.util.Painter;

public class Boom {
    private float x, y;
    private int width, height;
    private Bitmap fruit;
    private int speed;

    public Boom(float x, float y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        fruit = Assets.boom;
        this.speed = speed;
    }

    public void update() {
        fly();
    }
    public void render(Painter g) {
        g.drawImage(fruit, (int) x, (int) y, width, height);
    }

    public void onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {

        }
    }

    public void fly() {
        x -= speed;
        if (x < -width) {
            x = GameMainActivity.GAME_WIDTH + 100;
            //random vị trí y, từ trên xuống dưới
            y = (int) Math.floor(Math.random() * (GameMainActivity.GAME_HEIGHT - height));
        }
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
