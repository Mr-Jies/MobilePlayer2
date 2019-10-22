package com.wu.mobileplayer.pager;

import android.content.ContentResolver;
import android.content.Context;

import android.content.Intent;
import android.database.Cursor;

import android.net.Uri;

import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.wu.mobileplayer.Adapter.VideoAdapter;
import com.wu.mobileplayer.R;
import com.wu.mobileplayer.activity.SystemVideoPlayer;
import com.wu.mobileplayer.base.BasePager;
import com.wu.mobileplayer.domain.MediaItem;
import com.wu.mobileplayer.utils.LogUtil;
import com.wu.mobileplayer.utils.Utils;

import java.util.ArrayList;

public class VideoPager extends BasePager {

    private ListView listview;
    private ProgressBar pd_load;
    private TextView tv_nomedia;
    private ArrayList<MediaItem> mediaItems;

    private Handler handler = new Handler(){

        private VideoAdapter videoAdapter;

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (mediaItems!=null&&mediaItems.size()>0){
                //有数据
                //设置适配器
                videoAdapter = new VideoAdapter(context,mediaItems);
                listview.setAdapter(videoAdapter);

                tv_nomedia.setVisibility(View.GONE);
            }else {
                //无数据
                //文本显示
                tv_nomedia.setVisibility(View.VISIBLE);
            }
            pd_load.setVisibility(View.GONE);

            //把文本和progressBar隐藏
        }
    };


    public VideoPager(Context context) {
        super(context);

    }
    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.video_pager, null);
        listview = view.findViewById(R.id.listview);
        pd_load = view.findViewById(R.id.pd_load);
        tv_nomedia = view.findViewById(R.id.tv_nomedia);

        //设置listView的点击事件
        listview.setOnItemClickListener(new MyOnItemClickListener());
        return view;
    }

    class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            MediaItem mediaItem = mediaItems.get(i);
            Toast.makeText(context,"mediaTieme=="+mediaItem.toString(),Toast.LENGTH_SHORT).show();

            //1.调用起系统所有的播放器
//            Intent intent = new Intent();
//            intent.setDataAndType(Uri.parse(mediaItem.getData()),"video/*");
//            context.startActivity(intent);

            //2.调用自己的播放器
            Intent intent = new Intent(context, SystemVideoPlayer.class);
            intent.setDataAndType(Uri.parse(mediaItem.getData()),"video/*");
            context.startActivity(intent);

        }
    }

    @Override
    public void InitData() {
        super.InitData();
        LogUtil.e("本地视频的数据被初始化了...");
        getDataFormLocal();

    }

    //从本地sd卡得到数据
    private void getDataFormLocal() {

        mediaItems = new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                super.run();
                ContentResolver resolver = context.getContentResolver();
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                Uri uri = MediaStore.Video.Media.INTERNAL_CONTENT_URI;
                String[] objs = {
                        MediaStore.Video.Media.DISPLAY_NAME,//视频在sdcard的名称
                        MediaStore.Video.Media.DURATION,//视频总时长
                        MediaStore.Video.Media.SIZE,//视频文件的大小
                        MediaStore.Video.Media.DATA,//视频文件的绝对地址
                        MediaStore.Video.Media.ARTIST//歌曲的演唱者
                };
                Cursor cursor = resolver.query(uri, objs, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()){

                        MediaItem mediaItem = new MediaItem();

                        String name = cursor.getString(0);
                        mediaItem.setName(name);

                        long duration = cursor.getLong(1);
                        mediaItem.setDuration(duration);

                        long size = cursor.getLong(2);
                        mediaItem.setSize(size);

                        String data = cursor.getString(3);
                        mediaItem.setData(data);

                        String artist = cursor.getString(4);
                        mediaItem.setArtist(artist);

                        mediaItems.add(mediaItem);
                    }
                    cursor.close();
                }
                //发消息
                handler.sendEmptyMessage(0);//通知

            }
        }.start();
    }

}
