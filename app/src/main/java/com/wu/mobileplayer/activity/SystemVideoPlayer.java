package com.wu.mobileplayer.activity;


import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.wu.mobileplayer.R;

/**
 * 系统播放器
 */
public class SystemVideoPlayer extends Activity {

    private VideoView vv_video;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ystemvideo);

        vv_video = findViewById(R.id.vv_video);

        //准备好的监听
        vv_video.setOnPreparedListener(new MyOnPreparedListener());

        //播放出错了的监听
        vv_video.setOnErrorListener(new MyOnErrorListener());

        //播放完全的监听
        vv_video.setOnCompletionListener(new MyCompletionListener());

        // 得到播放地址
        uri = getIntent().getData();
        if (uri !=null){
            vv_video.setVideoURI(uri);
        }

        //设置控制面板
//        vv_video.setMediaController(new MediaController(this));
    }

    class MyOnPreparedListener implements MediaPlayer.OnPreparedListener{
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            vv_video.start();//开始播放
        }
    }

    class MyOnErrorListener implements MediaPlayer.OnErrorListener{

        @Override
        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
            Toast.makeText(SystemVideoPlayer.this,"播放出错",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    class MyCompletionListener implements MediaPlayer.OnCompletionListener{

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            Toast.makeText(SystemVideoPlayer.this,"播放完成"+uri,Toast.LENGTH_SHORT).show();
        }
    }
}
