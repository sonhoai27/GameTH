package com.sonhoai.sonho.gameth.model;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.sonhoai.sonho.gameth.main.Assets;
import com.sonhoai.sonho.gameth.main.GameMainActivity;
import com.sonhoai.sonho.gameth.main.GameView;
import com.sonhoai.sonho.gameth.util.Painter;

public class FruitA {
    private float x, y;
    private int width, height;
    private Rect rect;
    private Bitmap fruit;
    private int speed;
    private static Boolean isPress = false;

    public FruitA(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        fruit = Assets.fruit;
        speed = (int) (GameMainActivity.GAME_HEIGHT*0.5/GameView.FPS);

        rect = new Rect((int) x, (int) y, (int) (x + width), (int) (y + height));
    }

    public void update() {
        fly();
        updateRect();
    }

    public void updateRect() {
        rect.set((int) x, (int) y, (int) x + width, (int) y + height);
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
}
