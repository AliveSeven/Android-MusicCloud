package com.example.musiccloud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musiccloud.Room.BasicApp;
import com.example.musiccloud.Room.RecyclerView.HotList.HotViewAdapter;
import com.example.musiccloud.Room.RecyclerView.MusicList.MusicAdapter;
import com.example.musiccloud.Room.db.entity.Songs;
import com.example.musiccloud.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MusicAdapter.CallBackValue{

    /** 声明变量
     *  tv_total是歌曲时长
     *  appBarConfiguration是导航栏
     *  通过Binding绑定视图，可以更轻松地编写可与视图交互
     * */
    private static TextView tv_total;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    //  声明按钮
    private Button btnPause, btnPlayUrl, btnStop, btnReplay;
    //  进度条
    private SeekBar skbProgress;
    //  歌曲播放对象player
    private Player player;
    //  文本：当前播放的歌曲
    private TextView tipsView;
    //  音乐播放网络直链
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //  通过binding视图绑定
        setContentView(binding.getRoot());

        //  设置自定义应用栏，这一步要有，不然会报空指针错误
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //  获取布局文件中的BottomNavigationView
        BottomNavigationView navView = findViewById(R.id.nav_view);

        //  导航栏设置跳转
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_music, R.id.navigation_user)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //  歌曲时长
        tv_total = (TextView) findViewById(R.id.tv_total);

        //  播放歌曲按钮
        btnPlayUrl = (Button) this.findViewById(R.id.btnPlayUrl);
        //  按钮监听
        btnPlayUrl.setOnClickListener(new ClickEvent());

        //  暂停歌曲按钮
        btnPause = (Button) this.findViewById(R.id.btnPause);
        //  按钮监听
        btnPause.setOnClickListener(new ClickEvent());

        //  停止歌曲按钮
        btnStop = (Button) this.findViewById(R.id.btnStop);
        //  按钮监听
        btnStop.setOnClickListener(new ClickEvent());

        //  重播歌曲按钮
        btnReplay = (Button) this.findViewById(R.id.btnReplay);
        //  按钮监听
        btnReplay.setOnClickListener(new ClickEvent());

        //  当前播放的歌曲
        tipsView=(TextView) this.findViewById(R.id.tips);

        //  进度条
        skbProgress = (SeekBar) this.findViewById(R.id.skbProgress);
        //  设置进度条监听Event
        skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());

        /** 初始化音乐播放网络直链url
         *  这里放了两条直链接，都是红日.mp3
         *  如果有一个直链失效了，可以用另一条试试。
         * */
//      url = "https://pro-xhb-audio.oss-cn-hangzhou.aliyuncs.com/f4f0de6a-e371-4369-b290-76620eb672eb.mp3" ;
        url = "https://s1.ananas.chaoxing.com/audio/c3/27/9c/70db7fac8387deaee37c4f7d0e2a51af/audio.mp3";

//      初始化 player对象
        player = new Player(url,skbProgress,tv_total);
    }

//      通过接口动态获取播放音乐直链urlValue 和 音乐名称nameValue
    public void SendMessageValue(String urlValue , String nameValue ) {
        url = urlValue;
//          当前播放音乐改变
        tipsView.setText("开始播放  "+ nameValue + "....");
//          player对象重新设置播放链接
        player.setVideoUrl(urlValue);
//          开始播放
        player.play();
    }

//      点击监听事件Event
    class ClickEvent implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            //  下面分别是按下在暂停键、播放键、停止键、重播键的事件
            if (arg0 == btnPause) {
                boolean pause=player.pause();
                if (pause) {
                    btnPause.setText("继续");
                    tipsView.setText("暂停播放...");
                }else{
                    btnPause.setText("暂停");
                    tipsView.setText("继续播放...");
                }
            } else if (arg0 == btnPlayUrl) {
                player.play();
                tipsView.setText("开始播放...");
            } else if (arg0 == btnStop) {
                player.stop();
                tipsView.setText("停止播放...");
            } else if (arg0==btnReplay) {
                player.replay();
                tipsView.setText("重新播放...");
            }
        }
    }

//      进度条监听事件
    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            this.progress = progress * player.mediaPlayer.getDuration()
                    / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与歌曲时间的数字，而不是与seekBar.getMax()相对的数字
            player.mediaPlayer.seekTo(progress);
        }
    }

    //根据mAppBarConfiguration的配置执行导航的 返回（navigate Up） 操作
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment);

        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}