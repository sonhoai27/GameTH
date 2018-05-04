package com.sonhoai.sonho.gameth.state;

import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import com.sonhoai.sonho.gameth.main.Assets;
import com.sonhoai.sonho.gameth.main.GameMainActivity;
import com.sonhoai.sonho.gameth.model.Boom;
import com.sonhoai.sonho.gameth.model.FruitA;
import com.sonhoai.sonho.gameth.model.Player;
import com.sonhoai.sonho.gameth.util.Painter;

public class PlayState extends State {
    private Player player;
    private FruitA fruitA;
    private Boom boom, boomB;

    @Override
    public void init() {
        player = new Player(0, GameMainActivity.GAME_HEIGHT - 100, 100, 100);
        fruitA = new FruitA(GameMainActivity.GAME_WIDTH+100, 0, 80, 80);
        boom = new Boom(GameMainActivity.GAME_WIDTH+100, 0, 200, 200, (int) (GameMainActivity.GAME_HEIGHT / 60F));
        boomB = new Boom(GameMainActivity.GAME_WIDTH+100, 0, 200, 200, (int) (GameMainActivity.GAME_HEIGHT*0.2 / 60F));
    }

    @Override
    public void update(float delta) {
        Log.i("UPDATE", "AA");
        player.update();
        fruitA.update();
        boom.update();
        boomB.update();
    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.gameBackground, 0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        player.render(g);
        fruitA.render(g);
        boom.render(g);
        boomB.render(g);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            player.onTouch(e, scaledX,scaledY);
        } else if (e.getAction() == MotionEvent.ACTION_UP) {
            player.onTouch(e, scaledX,scaledY);
        }
        return true;
    }

    @Override
    public void load() {
        super.load();
    }
}