package com.example.musiccloud.Room.RecyclerView.HotList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.musiccloud.R;
import com.example.musiccloud.Room.BasicApp;
import com.example.musiccloud.Room.db.entity.Songs;
import com.example.musiccloud.Room.tools.AddSong;
import com.example.musiccloud.Room.tools.MyDialog;

import java.util.List;

/**
 * 描述: 热歌榜布局适配器
 * 热歌榜的RecyclerView的Adapter类
 */

public class HotViewAdapter extends RecyclerView.Adapter<HotViewHolder>{

    //    热歌列表
    private List<Songs> mSongslist;

    public HotViewAdapter(List<Songs> list) {
        this.mSongslist = list;
    }

    @Override
    public HotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //          加载展示单条数据的布局文件
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list_item, parent, false);
        //          初始化一个holder对象
        HotViewHolder holder = new HotViewHolder(view);

        //设置监听事件，当用户长按了某个song，setOnLong 是长按
        holder.getItemView().setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //将当前笔记信息保存到BasicApp中，使之变为全局数据，其他地方也可获得
                BasicApp basicApp=(BasicApp)parent.getContext().getApplicationContext();
                //将basicApp对象设置好当前item的歌曲
                basicApp.setSongs(holder.getSongs());
                //打开自定义对话框，让用户确认是否要增加当前songs
                AddSong addSong = new AddSong(parent.getContext(), "要把这个歌曲增加到播放列表吗？");
                //展示对话框，增加框
                addSong.show();
                return true;
            }

        });
        return holder;
    }

    @Override
    public void onBindViewHolder(HotViewHolder holder, int position) {
//        展示偶数 / 奇数 的布局
        if(position %2 == 0){
//          如果偶数条，就显示偶数的的消息布局，将奇数的消息布局隐藏
            holder.oushuLayout.setVisibility(View.VISIBLE);
//          奇数条隐藏
            holder.jishuLayout.setVisibility(View.GONE);
//          展示歌名
            holder.oushu_name.setText(mSongslist.get(position).getSongsName());
//          展示歌手
            holder.oushu_singer.setText(mSongslist.get(position).getSongsSinger());
//          展示顺序
            holder.oushu_num.setText(String.valueOf(position+1));
        }else if(position %2 != 0 ){
//          同上
            holder.jishuLayout.setVisibility(View.VISIBLE);
            holder.oushuLayout.setVisibility(View.GONE);
            holder.jishu_name.setText(mSongslist.get(position).getSongsName());
            holder.jishu_singer.setText(mSongslist.get(position).getSongsSinger());
            holder.jishu_num.setText(String.valueOf(position+1));
        }
        //          赋值
        holder.bindTo(new Songs(mSongslist.get(position).getSongsName(),mSongslist.get(position).getSongsSinger(),mSongslist.get(position).getSongsID()));
    }

//    返回热歌列表的size
    @Override
    public int getItemCount() {
        return mSongslist.size();
    }
}
