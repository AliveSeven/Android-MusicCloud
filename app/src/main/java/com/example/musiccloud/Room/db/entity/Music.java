package com.example.musiccloud.Room.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

@Entity
public class Music {
    //PrimaryKey表示是字段id是主键，autoGenerate表示id的值会自动生成，不用用户指定
    @PrimaryKey(autoGenerate=true)
    private Long id;

    //NonNull表示字段music_name不能为空；ColumnInfo(name = "music_name")表示music表中的music_name字段对应Music类的musicName属性
    @NonNull
    @ColumnInfo(name = "music_name")
    public String musicName;

    //必须有一个无参数构造函数，room要使用它创建对象
    public Music() {}

    //@Ignore注解表示此构造函数room不需要使用，但是程序员可以使用它来创建Music对象
    @Ignore
    public Music(String musicName) {
        this.musicName = musicName;
    }

    // 歌手
    @ColumnInfo(name = "music_singer")
    public String singer;

    // 歌曲id
    @ColumnInfo(name = "song_id")
    public String song_id;


    //重写判断对象内容是否相等的方法
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;//先判断o是否为本对象，如果是就肯定是同一对象了，this 指向当前的对象
        if (o == null || getClass() != o.getClass()) return false;//再判断o是否为null，和o.类对象和本类对象是否一致
        Music music = (Music) o;//再把o对象强制转化为Music类对象

        // 类型相同, 比较内容是否相同
        if(id != music.id)
            return false;

        //查看两个对象的属性值是否相等,返回结果
        return id == music.id &&
                Objects.equals(musicName, music.musicName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, musicName);
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }


    public String getSong_id() {
        return song_id;
    }

    public void setSong_id(String song_id) {
        this.song_id = song_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}