package com.wu.mobileplayer.Adapter;

import android.content.Context;
import android.os.Handler;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wu.mobileplayer.R;
import com.wu.mobileplayer.domain.MediaItem;
import com.wu.mobileplayer.utils.Utils;

import java.util.ArrayList;

public class VideoAdapter extends BaseAdapter {

    private ViewHolder viewHolder;
    private Context context;
    private final ArrayList<MediaItem> mediaItems;
    private Utils utils;

    public VideoAdapter(Context context, ArrayList<MediaItem> mediaItems){
        this.context = context;
        this.mediaItems = mediaItems;
        utils = new Utils();
    }

    @Override
    public int getCount() {
        return mediaItems.size();
    }

    @Override
    public MediaItem getItem(int i) {
        return mediaItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null){
            convertView = View.inflate(context, R.layout.item_video_pager,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_icon = convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
            viewHolder.tv_time = convertView.findViewById(R.id.tv_time);
            viewHolder.tv_size = convertView.findViewById(R.id.tv_size);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //根据i得到列表中对应位置的数据
        MediaItem mediaItem = mediaItems.get(i);
        viewHolder.tv_name.setText(mediaItem.getName());
        viewHolder.tv_size.setText(Formatter.formatFileSize(context,mediaItem.getSize()));
        viewHolder.tv_time.setText(utils.stringForTime((int) mediaItem.getDuration()));

        return convertView;
    }

    /**
     * 内容持有者
     */
    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_time;
        TextView tv_size;
    }

}



