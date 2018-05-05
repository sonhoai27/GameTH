package com.sonhoai.sonho.gameth.state;

import android.view.MotionEvent;

import com.sonhoai.sonho.gameth.main.Assets;
import com.sonhoai.sonho.gameth.main.GameMainActivity;
import com.sonhoai.sonho.gameth.util.Painter;
import com.sonhoai.sonho.gameth.util.UIButton;
import com.sonhoai.sonho.gameth.util.UILabel;

public class GameCompletionState extends State {
    private UILabel highScore;
    private UIButton exitButton, newButton;
    @Override
    public void init() {
        exitButton = new UIButton(
                (int) (GameMainActivity.GAME_WIDTH*0.1)+50,
                (int) (GameMainActivity.GAME_HEIGHT*0.28)+250,
                (int) (GameMainActivity.GAME_WIDTH*0.1)+50+200,
                (int) (GameMainActivity.GAME_HEIGHT*0.28)+250+200,
                Assets.quitButton);
        newButton = new UIButton(
                (int) (GameMainActivity.GAME_WIDTH*0.1)+350,
                (int) (GameMainActivity.GAME_HEIGHT*0.28)+250,
                (int) (GameMainActivity.GAME_WIDTH*0.1)+350+200,
                (int) (GameMainActivity.GAME_HEIGHT*0.28)+250+200,
                Assets.newButton);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.menuCompletion, (int) (GameMainActivity.GAME_WIDTH*0.1), (int) (GameMainActivity.GAME_HEIGHT*0.28), 600, 678);
        newButton.render(g, false);
        exitButton.render(g, false);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            newButton.onTouchDown(scaledX, scaledY);
            exitButton.onTouchDown(scaledX, scaledY);
        } else if (e.getAction() == MotionEvent.ACTION_UP) {
            if(newButton.isPressed(scaledX,scaledY)){
                setCurrentState(new PlayState());
            }
            if(exitButton.isPressed(scaledX,scaledY)){
                setCurrentState(new MenuState());
            }
        }
        return true;
    }

    @Override
    public void load() {
        super.load();
    }
}
