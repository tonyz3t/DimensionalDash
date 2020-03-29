package com.tonyz3t.android.dimensionaldash;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread2 extends Thread {

    private SurfaceHolder mSurfaceHolder;
    private GamePanel mGamePanel;

    private boolean mRunning;
    public static Canvas mCanvas;
    //private Input input;

    public MainThread2(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        mSurfaceHolder = surfaceHolder;
        mGamePanel = gamePanel;
    }

    public void setRunning(boolean running) {
        mRunning = running;
    }

    public void run() {
        super.run();
        //init input
        //input = new Input();

        //game loop
        final int TICKS_PER_SECOND = 30; //60fps
        final int TIME_PER_TICK = 1000 / TICKS_PER_SECOND; //amount of time to get 60fps
        final int MAX_FRAMESKIPS = 5;

        long nextGameTick = System.currentTimeMillis();
        int loops;
        float interpolation;

        long timeAtLastFPSCheck = 0;
        int ticks = 0;

        boolean running = true;

        while(mRunning) {
            //updating
            loops = 0;
            mCanvas = null;

            try {
                mCanvas = mSurfaceHolder.lockCanvas();
                synchronized (mSurfaceHolder) {
                    mGamePanel.update();;
                    mGamePanel.draw(mCanvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mCanvas != null) {
                    try {
                        mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            while(System.currentTimeMillis() > nextGameTick && loops < MAX_FRAMESKIPS) {
                //update();
                ticks++;

                nextGameTick += TIME_PER_TICK;
                loops++;
            }


            //FPS Check
            if(System.currentTimeMillis() - timeAtLastFPSCheck >= 1000) {
                System.out.println("FPS: " + ticks);
                ticks = 0;
                timeAtLastFPSCheck = System.currentTimeMillis();
            }

        }
    }



}
