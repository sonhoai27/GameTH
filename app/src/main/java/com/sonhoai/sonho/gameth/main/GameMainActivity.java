package com.sonhoai.sonho.gameth.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.sonhoai.sonho.gameth.R;

public class GameMainActivity extends AppCompatActivity {

    public  static final int GAME_WIDTH = 720;
    public static final int GAME_HEIGHT = 1280;
    public static GameView sGame;
    public static AssetManager assets;
    private static SharedPreferences prefs;
    private static final String highScoreKey = "HIGHSCORE";
    private static int highScore;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getPreferences(Activity.MODE_PRIVATE);
        highScore = retrieveHighScore();
        assets = getAssets();
        sGame = new GameView(this, GAME_WIDTH, GAME_HEIGHT);
        setContentView(sGame);
        context = getApplicationContext();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sGame.onResume();
    }

    @Override //onPause() is also run when the game is quit
    protected  void onPause() {
        super.onPause();
        sGame.onPause();
    }

    public static void setHighScore(int highScore) {
        SharedPreferences.Editor editor = prefs.edit();
        if(retrieveHighScoreState() < highScore){
            GameMainActivity.highScore = highScore;
            editor.putInt(highScoreKey, highScore);
            editor.commit();
        }
    }

    private int retrieveHighScore() {
        return prefs.getInt(highScoreKey, 0);
    }

    private static int retrieveHighScoreState() {
        return prefs.getInt(highScoreKey, 0);
    }

    public static int getHighScore() {
        return highScore;
    }
}
