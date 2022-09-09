package com.example.musiccloud.Room.db.dao;

import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.musiccloud.Room.db.entity.Music;

import java.util.List;


@Dao
public interface MusicDao {

    //获取笔记最大的id（用于插入笔记记录后，再将笔记的详细信息插入到笔记详情表MusicDetail时，需要刚插入笔记的id）
    @Query("SELECT max(id) FROM Music")
    Long getMaxId();

    // 插入复数的Music
    @Insert
    void insert(Music... music);

    @Insert
    void insert(List<Music> music);

    //删除music表中所有记录
    @Query("DELETE FROM Music ")
    void deleteAll();

    //删除music表中所有主键在Music中的记录
    @Delete
    void delete(List<Music> music);

    //删除一条记录
    @Delete
    void delete(Music music);

    // 更新一条记录
    @Update
    void update(Music music);

    @Query("SELECT * FROM Music")
    List<Music> getAll();

    @Query("SELECT * FROM Music")
    public PagingSource<Integer, Music> loadAllForPaging();

    //带条件查询，用于recyclerView组件
    @Query("SELECT * FROM Music where music_name LIKE :musicName")
    public PagingSource<Integer, Music> loadAllForPagingByName(String musicName);

    @Query("SELECT * FROM Music WHERE music_name=:musicName")
    Music findByName(String musicName);

    @Query("SELECT count(*) FROM Music")
    Long getAllSize();

}
