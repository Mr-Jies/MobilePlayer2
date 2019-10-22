package com.wu.mobileplayer.activity;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.wu.mobileplayer.R;
import com.wu.mobileplayer.base.BasePager;
import com.wu.mobileplayer.pager.AudioPager;
import com.wu.mobileplayer.pager.NetAudioPager;
import com.wu.mobileplayer.pager.NetVideoPager;
import com.wu.mobileplayer.pager.VideoPager;
import com.wu.mobileplayer.utils.ReplaceFragment;

import java.util.ArrayList;


// FragmentActivity
public class MainActivity extends FragmentActivity {

    private FrameLayout fl_main_context;
    private RadioGroup rg_bottom_tag;
    private ArrayList<BasePager> basePagers;

    //选中的位置
    private int position;

    /**
     * 解决安卓6.0以上版本不能读取外部存储权限的问题
     * @param activity
     * @return
     */
    public static boolean isGrantExternalRW(Activity activity) {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) &&
                (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fl_main_context = findViewById(R.id.fl_main_context);
        rg_bottom_tag = findViewById(R.id.rg_bottom_tag);


        basePagers = new ArrayList<>();
        basePagers.add(new VideoPager(this)); //添加本地视频页面
        basePagers.add(new AudioPager(this)); //添加本地音频页面
        basePagers.add(new NetVideoPager(this)); //添加网络视频页面
        basePagers.add(new NetAudioPager(this)); //添加网络音频页面

        //设置RadioGroup的监听
        rg_bottom_tag.setOnCheckedChangeListener(new MyOnCheckedChangerListener());
        //先设置监听---再勾选才有效果,否则第一个页面是空白的
        rg_bottom_tag.check(R.id.rb_video);  //默认选择首页
    }


    class MyOnCheckedChangerListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i) {
                default:
                    isGrantExternalRW(MainActivity.this);
                    position = 0;
                    break;
                case R.id.rb_audio:
                    position = 1;
                    break;
                case R.id.rb_net_video:
                    position = 2;
                    break;
                case R.id.rb_net_audio:
                    position = 3;
                    break;
            }
            setFragment();
        }
    }

    //把页面添加到Fragment中
    private void setFragment() {
        //1.得到FragmentManger
        FragmentManager manager = getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction ft = manager.beginTransaction();
        //3.替换
        ft.replace(R.id.fl_main_context, new ReplaceFragment(getBasePager()));
        //4.提交事务
        ft.commit();

    }

    //根据位置得到某个页面
    private BasePager getBasePager() {
        BasePager basePager = basePagers.get(position);
        if (basePager != null && !basePager.isInitData) {
            basePager.InitData();
            basePager.isInitData = true;
        }
        return basePager;
    }

}
