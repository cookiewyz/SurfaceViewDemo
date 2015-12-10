package com.qianfeng.zz1503.surfaceviewdemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Calendar;

/**
 * @author wyz
 */

public class TimerActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    //1503
    private SurfaceView surface;
    private SurfaceHolder holder;
    private int width;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        surface = ((SurfaceView) findViewById(R.id.surface_timer));
        holder = surface.getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Canvas canvas = holder.lockCanvas();
                    if (canvas != null) {
                        //清除画布
                        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);

                        float f = (height - width) / 2;
                        canvas.translate(Math.max(0, -f), Math.max(0, f));//找原点
                        float d = Math.min(height, width) / 200f;
                        canvas.scale(d, d);//缩放
                        canvas.save();

                        //创建画笔
                        Paint paint = new Paint();
                        paint.setColor(Color.RED);
                        paint.setStrokeWidth(2);
                        paint.setAntiAlias(true);
                        paint.setStyle(Paint.Style.STROKE);

                        canvas.drawCircle(100, 100, 100, paint);
                        canvas.restore();
                        canvas.save();

                        //绘制小刻度60个
                        for (int i = 0; i < 60; i++) {
                            //paint.setColor(Color.WHITE);
                            canvas.drawLine(100, 0, 100, 6, paint);
                            canvas.rotate(6, 100, 100);
                        }
                        canvas.restore();
                        canvas.save();
                        //绘制大的1-12刻度
                        //
                        for (int i = 0; i < 12; i++) {
                            paint.setStrokeWidth(3);
                            if(i%3 == 0) {
                                canvas.drawLine(100, 0, 100, 15, paint);
                            } else {
                                canvas.drawLine(100, 0, 100, 10, paint);
                            }

                            canvas.rotate(30, 100, 100);
                        }
                        //绘制表盘的中的指针
                        canvas.restore();
                        canvas.save();
                        Calendar calendar = Calendar.getInstance();
                        //秒针
                        paint.setColor(Color.YELLOW);
                        canvas.rotate(calendar.get(Calendar.SECOND) * 6,100,100);
                        canvas.drawLine(100,130,100,20,paint);
                        canvas.restore();
                        canvas.save();
                        //分针
                        paint.setColor(Color.GREEN);
                        canvas.rotate(calendar.get(Calendar.MINUTE) * 6 +calendar.get(Calendar.SECOND) * 0.1f,100,100);
                        canvas.drawLine(100,120,100,30,paint);
                        canvas.restore();
                        canvas.save();
                        //时针
                        paint.setColor(Color.BLUE);
                        canvas.rotate(calendar.get(Calendar.HOUR) * 30 + calendar.get(Calendar.MINUTE) * 0.5f,100,100);
                        canvas.drawLine(100,110,100,40,paint);
                        canvas.restore();

                        holder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,
                               int format, int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
