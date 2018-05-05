package com.sonhoai.sonho.gameth.model;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.sonhoai.sonho.gameth.main.Assets;
import com.sonhoai.sonho.gameth.main.GameMainActivity;
import com.sonhoai.sonho.gameth.main.GameView;
import com.sonhoai.sonho.gameth.util.Painter;

public class Player {
    private float x, y;
    private int width, height;
    private Bitmap player;
    private int speed;
    private static Boolean isPress = false;

    public Player(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        player = Assets.player;
        speed = 1600;
    }

    public void update() {
        fly();
        if (y < 0) {
            y = 0;
        }
        if (y > GameMainActivity.GAME_HEIGHT - height) {
            y = GameMainActivity.GAME_HEIGHT - height;
        }
    }

    public void render(Painter g) {
        g.drawImage(player, (int) x, (int) y, width, height);
    }

    public void onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            isPress = true;
            Assets.playSound(Assets.flySoundId);
        } else if (e.getAction() == MotionEvent.ACTION_UP) {
            isPress = false;
        }
    }

    public void fly() {
        if (isPress) {
            up();
            Log.i("AAA", "up");
        } else {
            down();
            Log.i("AAA", "down");
        }
    }

    private void up() {
        y -= speed/GameView.FPS;
    }

    private void down() {
        y += speed/ GameView.FPS;
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
