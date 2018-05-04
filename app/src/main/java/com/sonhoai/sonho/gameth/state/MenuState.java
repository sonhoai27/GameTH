package com.sonhoai.sonho.gameth.state;

import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.sonhoai.sonho.gameth.main.Assets;
import com.sonhoai.sonho.gameth.main.GameMainActivity;
import com.sonhoai.sonho.gameth.main.GameView;
import com.sonhoai.sonho.gameth.util.Painter;
import com.sonhoai.sonho.gameth.util.UIButton;

public class MenuState extends State {
    private UIButton playButton;
    private UIButton scoreButton;
    @Override
    public void init() {
        showMenu();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.menuBackground, 0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        playButton.render(g, false);
        scoreButton.render(g, false);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            playButton.onTouchDown(scaledX, scaledY);
            scoreButton.onTouchDown(scaledX, scaledY);
        }
        if(e.getAction() == MotionEvent.ACTION_UP){
            Log.i("AAA", "AAA");
            if(playButton.isPressed(scaledX, scaledY)){
                setCurrentState(new PlayState());
            }
        }
        return true;
    }

    @Override
    public void load() {
        super.load();
    }

    @Override
    public void unload() {
        super.unload();
    }

    private void showMenu(){
        playButton = new UIButton(250, 500, 450, 580, Assets.newButton);
        scoreButton = new UIButton(250, 600, 450, 680, Assets.scoreButton);
    }
}
