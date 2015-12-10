package com.qianfeng.zz1503.surfaceviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

/**
 *  SurfaceView 低功能 高性能，复用率低
 *  相对于普通View功能比较少，
 *  GlSurfaceView OpenGL里面的视图 了解
 *  怎么使用SurfaceView?
 *  通过SurfaceHolder 来控制生命周期
 *  使用方法两钟 ：自定义 ，直接在xml文件里面写
 *  VideoView， MediaPlayer＋SurfaceView
 *
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //setContentView(new MyView(this));
        setContentView(new MySurfaceView(this));

        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            //(error,1)
            mediaPlayer.setDataSource("");
            mediaPlayer.prepare();
            mediaPlayer.start();

            mediaPlayer.pause();
            mediaPlayer.stop();
            //
            mediaPlayer.seekTo(100);
            //mediaPlayer.setAudioStreamType();


        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });

    }

    /**
     *画一个圆 半径逐渐增大 增大到一定程度在恢复原来大小，
     * 循环这个过程
     *
     */
    class  MyView extends View {

        Paint paint;
        int radius = 10;

        public MyView(Context context) {
            super(context);
            paint = new Paint();
            paint.setColor(Color.RED);//画笔颜色
            paint.setStrokeWidth(5);//画笔宽度
            paint.setAntiAlias(true);//抗锯齿
            paint.setStyle(Paint.Style.STROKE);//画笔样式，空心（STROKE）和实心（FILL）
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //canvas.translate(300,300);//平移
            /**
             * drawCircle 参数
             * x轴坐标，y坐标，半径，画笔
             */
            canvas.drawCircle(300,300,radius++,paint);
            if (radius > 100) {
                radius = 10;
            }
            invalidate();//通知刷新UI
        }
    }
}
