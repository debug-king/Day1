package com.hn.day1;

import android.os.Handler;

public class CustomsCountDownTime implements Runnable{
    //总时间
    private int time;
    //当前时间
    private int countDownTime;
    //消息传递对象
    private final Handler handler;

    private final ICountDownHandler countDownHandler;

    private boolean isRun;
    //1、时时去回调 什么时间 倒计时到几秒 观察者模式
    //2、支持动态传入总时间、可变总时间
    //3、没过一秒 总秒数减一
    //4、总时间倒计时为0时，要回调完成的状态
    public CustomsCountDownTime(int time, ICountDownHandler countDownHandler){
        handler = new Handler();
        this.time = time;
        this.countDownTime = time;
        this.countDownHandler = countDownHandler;
    }

    @Override
    public void run() {
        if (isRun){
            if(countDownHandler != null){
                countDownHandler.onTicker(countDownTime);
            }
            if(countDownTime == 0){
                cancel();
                if(countDownHandler != null) {
                    countDownHandler.onFinish();
                }
            } else {
                countDownTime = time--;
                handler.postDelayed(this, 1000);
            }

        }

    }
    //开始倒计时
    public void start(){
        isRun = true;
        handler.post(this);
    }

    public void cancel(){
        isRun = false;
        handler.removeCallbacks(this);
    }



    //观察者回调接口（IOC数据回调）
    public interface ICountDownHandler{
        void onTicker(int time);
        void onFinish();
    }


}
