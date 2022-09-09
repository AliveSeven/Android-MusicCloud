package com.example.musiccloud.Room.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.musiccloud.Room.db.dao.MusicDao;
import com.example.musiccloud.Room.db.entity.Music;

//Room是对原生的SQLite API进行了一层封装

@Database(entities = {Music.class}, version = 3,exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sInstance;

    @SuppressWarnings("WeakerAccess")
    public abstract MusicDao musicDao();

    //实例化AppDatabase对象时，应遵循 单例设计 模式
    //以后通过sInstance（AppDatabase对象）来操作数据库
    //数据库操作都是耗时操作，必须放在子线程中进行，否则会阻塞主线程（Activity）
    //这里使用同步类的静态方法，防止多个线程同时访问数据库，必须依次访问，一个访问完，其他才能访问数据库
    public static synchronized AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "my-database2")
                    .fallbackToDestructiveMigration().build();
        }
        return sInstance;
    }
}
