package com.wu.mobileplayer.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wu.mobileplayer.base.BasePager;
import com.wu.mobileplayer.utils.LogUtil;

public class NetAudioPager extends BasePager {

    private TextView textView;

    public NetAudioPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        LogUtil.e("在线音乐的页面被初始化了...");
        textView = new TextView(context);
        textView.setTextSize(30);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void InitData() {
        super.InitData();
        LogUtil.e("在线音乐的数据被初始化了...");

    }
}
