package com.example.musiccloud.Room;
import android.app.Application;

import com.example.musiccloud.Room.db.entity.Music;
import com.example.musiccloud.Room.db.entity.Songs;


//可以用于存放全局数据  要在AndroidManifest.xml的application标签中配置BasicApp
public class BasicApp extends Application {
    Music music;//全局数据
    Songs songs;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public Songs getSong(){ return songs; }

    public void setSongs(Songs songs) { this.songs = songs; }
}
