package com.example.musiccloud.Room.RecyclerView.MusicList;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;


import com.example.musiccloud.Room.db.AppDatabase;
import com.example.musiccloud.Room.db.dao.MusicDao;
import com.example.musiccloud.Room.db.entity.Music;

import kotlinx.coroutines.CoroutineScope;

//model一般用于表示数据
public class MusicViewModel extends AndroidViewModel {
    //dao类一般封装了对某个表（或试图）增删改查操作的方法
    private MusicDao musicDao;
    //LiveData数据能在数据发生变化时，自动通知，从而自动修改UI界面，刷新界面
    //PagedList<Music>支持分页显示多个Music
    private LiveData<PagingData<Music>>  musics;
    //PAGE_SIZE表示一页中数据记录的条数，这里设置为10条
    private static final int PAGE_SIZE = 10;
    private static final boolean ENABLE_PLACEHOLDERS = true;
    Application app;

    //构造方法
    public MusicViewModel(Application app) {
        super(app);
        this.app = app;
        //AppDatabase类封装了获取dao类对象的各种方法
        this.musicDao = AppDatabase.getInstance((Context)app).musicDao();
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);

        //setPageSize表示配置每页有多少条数据记录
        //enabledPlaceholders表示占位符，如果设置为true，每页都是得到固定数量（PAGE_SIZE）的数据，
        // 但最后一页可能得到为null的数据，所以要在MusicViewHolder的bindTo方法中单独处理为null的情况。
        PagingConfig pagingConfig=new PagingConfig(10,10,false,10);//初始化配置,可以定义最大加载的数据量
        Pager<Integer, Music> pager = new Pager<Integer, Music>(pagingConfig, ()->this.musicDao.loadAllForPaging());//构造函数根据自己的需要来调整
        this.musics= PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager),viewModelScope);
    }

    // 按音乐名进行模糊查询
    public void setMusics(String musicName) {
        musicName="%"+musicName+"%";//模糊查询
        //重新查询获取新的数据
        this.musicDao = AppDatabase.getInstance((Context)app).musicDao();
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);

        // 同上
        PagingConfig pagingConfig=new PagingConfig(10,10,false,10);//初始化配置,可以定义最大加载的数据量
        // 音乐名
        String finalMusicName = musicName;
        Pager<Integer, Music> pager = new Pager<Integer, Music>(pagingConfig, ()-> {
            return this.musicDao.loadAllForPagingByName(finalMusicName);
        });
        //构造函数根据自己的需要来调整
        this.musics= PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager),viewModelScope);

    }

    public void insert( Music music) {  }

    public void remove( Music music) {  }

    public MusicDao getMusicDao() {
        return musicDao;
    }

    public void setMusicDao(MusicDao musicDao) {
        this.musicDao = musicDao;
    }

    public LiveData getMusics() {
        return musics;
    }

    public void setMusics(LiveData musics) {
        this.musics = musics;
    }
}
