package com.hn.day1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.time.Instant;

public class SplashActivity extends AppCompatActivity {
    private FullScreenVideoView mSqlash;
    private TextView mJump;
    private CustomsCountDownTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        mSqlash = (FullScreenVideoView) findViewById(R.id.vv_sqlash);
        mJump = (TextView)findViewById(R.id.tv_jump);
        mJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示启动
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        });




        //播放地址
        mSqlash.setVideoURI(Uri.parse("android.resource://" + getPackageName()
                + File.separator + R.raw.splash));
        //监听到mSqlash异步装载完成之后回调（创建匿名类，mp.start后，视频开始播放,观察者模式)
        mSqlash.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        //监听到mSqlash完成后,继续开始播放视频
        mSqlash.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
        time = new CustomsCountDownTime(5,new CustomsCountDownTime.ICountDownHandler(){

            @Override
            public void onTicker(int time) {
                mJump.setText(time + "秒");
            }

            @Override
            public void onFinish() {
                mJump.setText("跳过");
            }
        });
        time.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        time.cancel();
    }
}
