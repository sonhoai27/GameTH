package com.sonhoai.sonho.gameth.state;

import android.view.MotionEvent;

import com.sonhoai.sonho.gameth.main.Assets;
import com.sonhoai.sonho.gameth.main.GameMainActivity;
import com.sonhoai.sonho.gameth.util.Painter;
import com.sonhoai.sonho.gameth.util.UIButton;
import com.sonhoai.sonho.gameth.util.UILabel;

public class GameOverState extends State {
    private int score;
    private UILabel txtScore;
    private UIButton btnRestart, btnQuit;
    public GameOverState(int score){
        this.score = score;
    }
    @Override
    public void init() {
        txtScore = new UILabel(score+"", GameMainActivity.GAME_WIDTH / 2, GameMainActivity.GAME_HEIGHT/2-10);
        btnRestart = new UIButton(250, 670, 250+90, 670+90, Assets.newButton);
        btnQuit = new UIButton(400, 670, 400+90, 670+90, Assets.quitButton);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.gameOver, (int) (GameMainActivity.GAME_WIDTH*0.12), GameMainActivity.GAME_HEIGHT/3+30, 580, 342);
        txtScore.render(g);
        btnRestart.render(g, false);
        btnQuit.render(g, false);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            btnRestart.onTouchDown(scaledX, scaledY);
            btnQuit.onTouchDown(scaledX, scaledY);
        } else if (e.getAction() == MotionEvent.ACTION_UP) {
            if(btnRestart.isPressed(scaledX,scaledY)){
                GameMainActivity.setHighScore(score);
                setCurrentState(new PlayState());
            }
            if(btnQuit.isPressed(scaledX,scaledY)){
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
