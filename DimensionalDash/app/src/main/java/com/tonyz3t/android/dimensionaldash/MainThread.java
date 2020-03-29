/*
package com.tonyz3t.android.dimensionaldash;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {



    public static final int MAX_FPS = 30;
    public static double averageFPS;

    private SurfaceHolder mSurfaceHolder;
    private GamePanel mGamePanel;

    private boolean mRunning;
    public static Canvas mCanvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        mSurfaceHolder = surfaceHolder;
        mGamePanel = gamePanel;
    }

    public void setRunning(boolean running) {
        mRunning = running;
    }

    @Override
    public void run() {
        super.run();

        long startTime;
        long timeMillis = 1000/MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;

        while (mRunning) {
            startTime = System.nanoTime();
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
            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMillis;
            try {
                if (waitTime > 0) {
                    this.sleep(waitTime);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == MAX_FPS) {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }


}
*/
