package com.sonhoai.sonho.gameth.state;

import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.sonhoai.sonho.gameth.main.Assets;
import com.sonhoai.sonho.gameth.main.GameMainActivity;
import com.sonhoai.sonho.gameth.main.GameView;
import com.sonhoai.sonho.gameth.model.Boom;
import com.sonhoai.sonho.gameth.model.FruitA;
import com.sonhoai.sonho.gameth.model.Player;
import com.sonhoai.sonho.gameth.util.Painter;
import com.sonhoai.sonho.gameth.util.UIButton;
import com.sonhoai.sonho.gameth.util.UILabel;

public class PlayState extends State {
    private Player player;
    private FruitA fruitA, fruitB;
    private Boom boom, boomB, boomC;
    private static int score;
    private UIButton quitButton, boxScore;
    private UILabel txtScore;
    private static boolean isOver;

    @Override
    public void init() {
        score = 0;
        isOver = false;
        txtScore = new UILabel("0", GameMainActivity.GAME_WIDTH / 2, 70);
        player = new Player(0, GameMainActivity.GAME_HEIGHT - 100, 150, 150);
        fruitA = new FruitA(GameMainActivity.GAME_WIDTH+100, 0, 80, 80, Assets.fruit, (int) (GameMainActivity.GAME_HEIGHT*0.5/ GameView.FPS));
        fruitB = new FruitA(GameMainActivity.GAME_WIDTH+100, 0, 100, 100, Assets.tomato, (int) (GameMainActivity.GAME_HEIGHT*0.8 / GameView.FPS));
        boom = new Boom(GameMainActivity.GAME_WIDTH+100, 0, 200, 200, (int) (GameMainActivity.GAME_HEIGHT / 60F));
        boomB = new Boom(GameMainActivity.GAME_WIDTH+100, 0, 200, 200, (int) (GameMainActivity.GAME_HEIGHT*0.2 / 60F));
        boomC = new Boom(GameMainActivity.GAME_WIDTH+100, 0, 200, 200, (int) (int) (GameMainActivity.GAME_HEIGHT*0.5/ GameView.FPS));
        quitButton = new UIButton(GameMainActivity.GAME_WIDTH - 100, 10, GameMainActivity.GAME_WIDTH - 100+88,10+88, Assets.quitButton);
        boxScore = new UIButton(GameMainActivity.GAME_WIDTH/2 - 100, 10, GameMainActivity.GAME_WIDTH/2 -100 + 228,10+80, Assets.boxScore);
    }

    @Override
    public void update(float delta) {
        Log.i("UPDATE", "AA");
        player.update();
        fruitA.update();
        fruitB.update();
        boom.update();
        boomB.update();
        boomC.update();
        if(!isOver){
            updateScore();
        }
        checkGameOver();
    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.gameBackground, 0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        player.render(g);
        fruitA.render(g);
        fruitB.render(g);
        boom.render(g);
        boomB.render(g);
        boomC.render(g);
        quitButton.render(g, false);
        boxScore.render(g, false);
        txtScore.render(g);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            player.onTouch(e, scaledX,scaledY);
            quitButton.onTouchDown(scaledX, scaledY);
        } else if (e.getAction() == MotionEvent.ACTION_UP) {
            player.onTouch(e, scaledX,scaledY);
            if(quitButton.isPressed(scaledX,scaledY)){
                handleQuitButton();
            }
        }
        return true;
    }

    @Override
    public void load() {
        super.load();
    }

    private void checkGameOver(){
        if (checkBoom(boom) || checkBoom(boomB) || checkBoom(boomC)) {
            Log.i("TOAST", "Dung ne");
            isOver = true;
            setCurrentState(new GameOverState(score));
        }
    }

    private void updateScore(){
        score(fruitA, 5);
        score(fruitB, 15);
    }
    private Boolean checkBoom(Boom  boom){
        int centerX = (int) (boom.getX() + boom.getWidth()/2);
        int centerY = (int) (boom.getY() + boom.getHeight()/2);
        if(0 <= centerX && centerX <= player.getWidth() &&
                player.getY() <= centerY && centerY <= player.getY()+player.getWidth()
                ){
            Assets.playSound(Assets.overSoundId);
            return true;
        }

        return false;
    }
    private void score(FruitA  fruitA, int s){
        int centerX = (int) (fruitA.getX() + fruitA.getWidth()/2);
        int centerY = (int) (fruitA.getY() + fruitA.getHeight()/2);

        if(0 <= centerX && centerX <= player.getWidth() &&
                player.getY() <= centerY && centerY <= player.getY()+player.getWidth()
                ){
            score +=s;
            fruitA.setX(-player.getWidth());
            txtScore.setText(score+"");
            Assets.playSound(Assets.hitSoundId);
        }
    }

    private void handleQuitButton(){
        setPauseGame();
    }

    public static int getScore() {
        return score;
    }
}