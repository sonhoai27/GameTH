package com.sonhoai.sonho.gameth.state;

import android.app.AlertDialog;
import android.util.Log;
import android.util.PrintWriterPrinter;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.sonhoai.sonho.gameth.R;
import com.sonhoai.sonho.gameth.main.Assets;
import com.sonhoai.sonho.gameth.main.GameMainActivity;
import com.sonhoai.sonho.gameth.main.GameView;
import com.sonhoai.sonho.gameth.util.Painter;
import com.sonhoai.sonho.gameth.util.UIButton;

import static com.sonhoai.sonho.gameth.main.GameMainActivity.context;

public class MenuState extends State {
    private UIButton playButton;
    private UIButton userButton;
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
        userButton.render(g, false);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            playButton.onTouchDown(scaledX, scaledY);
            userButton.onTouchDown(scaledX, scaledY);
        }
        if(e.getAction() == MotionEvent.ACTION_UP){
            Log.i("AAA", "AAA");
            if(playButton.isPressed(scaledX, scaledY)){
                setCurrentState(new PlayState());
            }
            if(userButton.isPressed(scaledX,scaledY)){
                GameMainActivity.showHighScore();
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
        playButton = new UIButton(250, 500, 250+200, 500+200, Assets.newButton);
        userButton = new UIButton(250, 800, 250+200, 800+200, Assets.scoreButton);
    }
}
