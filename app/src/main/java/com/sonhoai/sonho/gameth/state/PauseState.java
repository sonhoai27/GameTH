package com.sonhoai.sonho.gameth.state;

import android.view.MotionEvent;

import com.sonhoai.sonho.gameth.main.Assets;
import com.sonhoai.sonho.gameth.main.GameMainActivity;
import com.sonhoai.sonho.gameth.util.Painter;
import com.sonhoai.sonho.gameth.util.UIButton;

public class PauseState extends State {
    private UIButton btnResume, btnQuit;
    @Override
    public void init() {
        btnResume = new UIButton(250, 600, 250+90, 600+90, Assets.resumeButton);
        btnQuit = new UIButton(400, 600, 400+90, 600+90, Assets.quitButton);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.pauseMenu, (int) (GameMainActivity.GAME_WIDTH*0.12), GameMainActivity.GAME_HEIGHT/3+30, 580, 342);
        btnResume.render(g, false);
        btnQuit.render(g, false);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            btnResume.onTouchDown(scaledX, scaledY);
            btnQuit.onTouchDown(scaledX, scaledY);
        } else if (e.getAction() == MotionEvent.ACTION_UP) {
            if(btnResume.isPressed(scaledX,scaledY)){
                setResumeGame();
            }
            if(btnQuit.isPressed(scaledX,scaledY)){
                GameMainActivity.setHighScore(PlayState.getScore());
                setCurrentState(new GameCompletionState());
            }
        }
        return true;
    }

    @Override
    public void load() {
        super.load();
    }
}
