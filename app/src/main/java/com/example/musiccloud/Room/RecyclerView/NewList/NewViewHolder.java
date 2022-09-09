package com.example.musiccloud.Room.RecyclerView.NewList;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.musiccloud.R;
import com.example.musiccloud.Room.db.entity.Songs;

public class NewViewHolder extends RecyclerView.ViewHolder{
    Songs songs;
    LinearLayout jishuLayout;
    LinearLayout oushuLayout;
    TextView jishu_name;
    TextView oushu_name;
    TextView jishu_singer;
    TextView oushu_singer;
    TextView jishu_num;
    TextView oushu_num;

    //  构造方法：将组件（ui）与组件对象（java类）关联起来
    public NewViewHolder(View view){
        super(view);
        //  根据页面的id关联组件对象 ↓
        jishuLayout = itemView.findViewById(R.id.jishu_liner);
        oushuLayout = itemView.findViewById(R.id.oushu_liner);
        jishu_name = itemView.findViewById(R.id.jishu_name);
        oushu_name = itemView.findViewById(R.id.oushu_name);
        jishu_singer = itemView.findViewById(R.id.jishu_singer);
        oushu_singer = itemView.findViewById(R.id.oushu_singer);
        jishu_num = itemView.findViewById(R.id.jishu_num);
        oushu_num = itemView.findViewById(R.id.oushu_num);
    }

    //将数据库中一条记录的数据赋值给TextView组件
    //sons会被RecyclerView自动赋值
    public void bindTo(Songs songs){
        this.songs = songs;
    }

    public View getItemView() {
        return itemView;
    }

    public Songs getSongs() {
        return songs;
    }

    public void setSongs(Songs songs) {
        this.songs = songs;
    }

}
