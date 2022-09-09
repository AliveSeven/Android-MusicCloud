package com.example.musiccloud.Room.db.entity;

//  热歌榜的实体类
public class Songs {

//    歌名
    private String SongsName;

//    歌手
    private String SongsSinger;

//    歌曲播放ID
    private String SongsID;

//    设置一个Songs方法可以通过这个方法给对象赋值，初始化Songs对象
    public Songs(String SongsName,String SongsSinger,String SongsID){
        this.SongsName = SongsName;
        this.SongsSinger = SongsSinger;
        this.SongsID = SongsID;
    }

//   自动生成get和set方法
    public String getSongsSinger() {
        return SongsSinger;
    }

    public void setSongsSinger(String songsSinger) {
        SongsSinger = songsSinger;
    }

    public String getSongsID() {
        return SongsID;
    }

    public void setSongsID(String songsID) {
        SongsID = songsID;
    }

    public String getSongsName() {
        return SongsName;
    }

    public void setSongsName(String SongsName) {
        this.SongsName = SongsName;
    }
}
