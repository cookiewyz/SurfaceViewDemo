package com.qianfeng.zz1503.surfaceviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by wyz on 15/12/10.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private final LoopThread loopThread;
    SurfaceHolder holder;
    boolean isruning = false;
    public MySurfaceView(Context context) {
        super(context);

        holder = getHolder();
        holder.addCallback(this);
        loopThread = new LoopThread(holder);
    }

    /**
     * surface的创建
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isruning = true;
        loopThread.start();
    }

    /**
     * surface的改变
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * 销毁
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isruning = false;
    }

    class LoopThread extends Thread {
        Paint paint;
        int radius = 10;
        SurfaceHolder holder;

        public LoopThread(SurfaceHolder holder) {
            this.holder = holder;

            paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(10);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
        }

        @Override
        public void run() {
            super.run();
            /**
             * 注意：获取画布对象，上锁，使用结束必须解锁
             */
            while (isruning) {
                Canvas canvas = holder.lockCanvas();
                draw(canvas);
                try {
                    loopThread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
        private void draw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);//清除画布
            canvas.drawCircle(300,300,radius++,paint);
            if (radius > 100) {
                radius = 10;
            }
        }
    }
}
