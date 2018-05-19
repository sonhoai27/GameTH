package com.sonhoai.sonho.gameth.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.sonhoai.sonho.gameth.state.LoadState;
import com.sonhoai.sonho.gameth.state.PauseState;
import com.sonhoai.sonho.gameth.state.State;
import com.sonhoai.sonho.gameth.util.InputHandler;
import com.sonhoai.sonho.gameth.util.Painter;

public class GameView extends SurfaceView implements Runnable {

    private Bitmap gameImage;
    private Rect gameImageSrc;
    private Rect gameImageDst;
    private Canvas gameCanvas;
    public Painter graphics;

    private final String TAG = "GameView";
    private Thread gameThread;
    private volatile boolean running = false;
    private volatile State currentState;
    private volatile State preState = null;

    private InputHandler inputHandler;

    public static final int FPS = 60;

    public GameView(Context context, int gameWidth, int gameHeight) {
        super(context);
        gameImage = Bitmap.createBitmap(gameWidth, gameHeight, Bitmap.Config.RGB_565);
        gameImageSrc = new Rect(0, 0, gameImage.getWidth(), gameImage.getHeight());
        gameImageDst = new Rect();
        gameCanvas = new Canvas(gameImage);
        graphics = new Painter(gameCanvas);

        SurfaceHolder holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                initInput();
                if(currentState == null) {
                    setCurrentState(new LoadState());
                }
                initGame();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                pauseGame();
            }
        });
    }

    public GameView(Context context) {
        super(context);
    }

    //PAUSE AND RESUME GAME
    public void setPause() {
        PauseState pauseState = new PauseState();
        pauseState.init();
        preState = currentState;//set currentstate
        currentState = pauseState;
        inputHandler.setCurrentState(currentState);
    }

    public void setResume() {
        if(preState!=null){
            currentState = preState;
            inputHandler.setCurrentState(currentState);
        }
    }

    public void setCurrentState(State newState) {
        System.gc();
        newState.init();
        currentState = newState;
        inputHandler.setCurrentState(currentState);
    }

    private void initInput() {
        if(inputHandler == null) {
            inputHandler = new InputHandler();
        }
        setOnTouchListener(inputHandler);
    }

    @Override
    public void run() {
        long updateDurationMillis = 0;
        long sleepDurationMillis = 0;

        while(running) {

            long beforeUpdateRender = System.nanoTime();
            long deltaMillis = sleepDurationMillis + updateDurationMillis;
            updateAndRender(deltaMillis);

            updateDurationMillis = (System.nanoTime() - beforeUpdateRender) / 1000000L;
            sleepDurationMillis = (long) Math.max(2, 1.0/FPS*1000 - updateDurationMillis);

            try {
                Log.i("GIAY", sleepDurationMillis+"");
                Thread.sleep(sleepDurationMillis);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //basically like a constructor for the game
    private void initGame() {
        running = true;
        gameThread = new Thread(this, "Game Thread");
        gameThread.start();
    }

    private void pauseGame() {
        running = false;
        while (gameThread.isAlive()) {
            try {
                gameThread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void onPause() {
        if (currentState != null) {
            currentState.onPause();
        }
    }

    public void onResume() {
        if (currentState != null) {
            currentState.onResume();
        }
    }

    //update vi trí,ve lai
    private void updateAndRender(long delta) {
        currentState.update(delta / 1000f);
        currentState.render(graphics);
        renderGameImage();
    }

    //update xong thì dc ve ngược lại trên màn hình
    private void renderGameImage() {
        Canvas screen = getHolder().lockCanvas();
        if (screen != null) {
            screen.getClipBounds(gameImageDst);
            screen.drawBitmap(gameImage, gameImageSrc, gameImageDst, null);
            getHolder().unlockCanvasAndPost(screen);
        }
    }
}

