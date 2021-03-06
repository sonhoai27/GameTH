package com.sonhoai.sonho.gameth.state;

import android.graphics.Paint;
import android.view.MotionEvent;
import com.sonhoai.sonho.gameth.main.GameMainActivity;
import com.sonhoai.sonho.gameth.util.Painter;

public abstract class State {

    public void setCurrentState(State newState) {
        GameMainActivity.sGame.setCurrentState(newState);
    }

    public void setPauseGame() {
        GameMainActivity.sGame.setPause();
    }

    public void setResumeGame(){
        GameMainActivity.sGame.setResume();
    }

    public abstract void init();

    public abstract void update(float delta);

    public abstract void render(Painter g);

    public abstract boolean onTouch(MotionEvent e, int scaledX, int scaledY);

    public void onPause(){}

    public void onResume() {}

    public void load() {}

    public void unload() {}

}

