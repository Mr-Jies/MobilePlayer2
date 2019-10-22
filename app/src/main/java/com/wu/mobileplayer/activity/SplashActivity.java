package com.wu.mobileplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

import com.wu.mobileplayer.R;

public class SplashActivity extends Activity {

    /**
     * 此处的引导页如果不在manifest的该activity中使用单例--launchMode="singleTask"
     * 还可以全局定义一个starSplash变量 如果调用了startMainActivity,就改变他的值,使之不再调用
     */



    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        },1000);
    }

    //另一种
//    boolean starSplash = true;
    private void startMainActivity() {
//        if (starSplash){
//            starSplash = false;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //关闭当前页面
        finish();
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        startMainActivity();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        //所有的消息都会移除
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
