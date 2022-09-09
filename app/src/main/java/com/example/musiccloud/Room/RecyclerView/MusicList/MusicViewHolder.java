package com.example.musiccloud.Room.RecyclerView.MusicList;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.musiccloud.R;
import com.example.musiccloud.Room.db.entity.Music;


//RecyclerView.ViewHolder用于将数据库中一条记录 绑定 到activity_music_recycler_view_item.xml中的TextView组件
public class MusicViewHolder extends RecyclerView.ViewHolder {
    // 声明变量
    private View itemView;
    private Music music;
    private TextView nameTextView;
    private TextView singerTextView;
    private TextView albumTextView;
    private TextView numTextView;

    // MusicViewHodler方法
    public MusicViewHolder(View itemView) {
        super(itemView);
        // 赋值itemView
        this.itemView = itemView;
        // 通关组件id绑定相对于的组件TextView
        this.nameTextView = (TextView)itemView.findViewById(R.id.item_music_name);
        // 创建歌手
        this.singerTextView = (TextView) itemView.findViewById(R.id.item_music_singer);
        // 顺序
        this.numTextView = (TextView) itemView.findViewById(R.id.item_music_num);
    }

    //将数据库中一条记录的数据赋值给TextView组件
    //music会被RecyclerView自动赋值
    public void bindTo(Music music , int position){
        this.music = music;
        if(music ==null)
            return;
        // 设置item音乐标题
        nameTextView.setText(music.getMusicName());
        // 设置item歌手的创建时间
        singerTextView.setText(music.getSinger());
        // 设置item专辑的创建时间
//        albumTextView.setText(music.getAlbum());
        // 设置item的顺序num
        numTextView.setText(String.valueOf(position+1));
    }

    //自动生成get和set方法
    public TextView getNameTextView() {
        return nameTextView;
    }

    public void setNameTextView(TextView nameTextView) {
        this.nameTextView = nameTextView;
    }

    public View getItemView() {
        return itemView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public TextView getSingerTextView() {
        return singerTextView;
    }

    public void setSingerTextView(TextView singerTextView) {
        this.singerTextView = singerTextView;
    }

    public TextView getAlbumTextView() {
        return albumTextView;
    }

    public void setAlbumTextView(TextView albumTextView) {
        this.albumTextView = albumTextView;
    }
}
