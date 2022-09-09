package com.example.musiccloud.Room.tools;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musiccloud.R;
import com.example.musiccloud.Room.BasicApp;
import com.example.musiccloud.Room.db.AppDatabase;
import com.example.musiccloud.Room.db.dao.MusicDao;
import com.example.musiccloud.Room.db.entity.Music;

public class AddSong extends Dialog {
    // 声明各个变量类型
    private String dialogName;
    private TextView tvMsg;
    private Button btnOK;
    private Button btnCancel;

    public AddSong(Context context, String dialogName) {
        super(context);
        this.dialogName = dialogName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题
        setContentView(R.layout.my_dialog);
        // 根据组件id绑定声明变量
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        btnOK = (Button) findViewById(R.id.btn_ok);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        tvMsg.setText(dialogName);  //设置自定义对话显示内容
        //为确定按钮设置点击事件
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 弹出提示，由于放在子线程会报错，这里放在主线程这里
                Toast.makeText(getContext(), "增加音乐到列表成功", Toast.LENGTH_LONG).show();
                //从线程池获取线程
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        BasicApp basicApp = (BasicApp)v.getContext().getApplicationContext();
                        MusicDao musicDao = AppDatabase.getInstance(basicApp).musicDao();
                        // 增加music
                        // 调用dao中的insert方法
                        Music music = new Music();
                        // 设置music的歌名，歌名通过全局变量basicApp获取Songs的songsName得到
                        music.setMusicName(basicApp.getSong().getSongsName());
                        // 设置music的歌手，歌手通过全局变量basicApp获取Songs的songsName得到
                        music.setSinger(basicApp.getSong().getSongsSinger());
                        // 设置music的歌曲id（这里的id是用于调用接口获取url的参数id），歌曲id通过全局变量basicApp获取Songs的songsName得到
                        music.setSong_id(basicApp.getSong().getSongsID());
                        musicDao.insert(music);
                    }
                });
                dismiss();//关闭当前对话框
            }
        });
        //为取消按钮设置点击事件
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();//关闭当前对话框
            }
        });
    }
}
