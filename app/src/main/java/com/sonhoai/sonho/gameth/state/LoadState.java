package com.sonhoai.sonho.gameth.state;

import android.view.MotionEvent;

import com.sonhoai.sonho.gameth.main.Assets;
import com.sonhoai.sonho.gameth.main.GameMainActivity;
import com.sonhoai.sonho.gameth.util.Painter;

public class LoadState extends State {
    @Override
    public void init() {
        load();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.gamebackground, 0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);

    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false;
    }

    @Override
    public void load() {
        super.load();
        Assets.load();
    }

    @Override
    public void unload() {
        super.unload();
    }
}
