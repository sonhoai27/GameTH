package com.sonhoai.sonho.gameth.util;

import android.view.MotionEvent;
import android.view.View;

import com.sonhoai.sonho.gameth.main.GameMainActivity;
import com.sonhoai.sonho.gameth.state.State;

public class InputHandler implements View.OnTouchListener {

    private State currentState;

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //it uses scaledX and Y instead of event.getX or event.getY
        // so there wont be bugs on different screen sizes
        int scaledX = (int) ((event.getX() / v.getWidth()) * GameMainActivity.GAME_WIDTH);
        int scaledY = (int) ((event.getY() / v.getHeight()) * GameMainActivity.GAME_HEIGHT);
        return currentState.onTouch(event, scaledX, scaledY);
    }
}

