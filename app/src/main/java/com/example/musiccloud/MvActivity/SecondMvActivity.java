package com.example.musiccloud.MvActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musiccloud.R;

public class SecondMvActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //设置界面
        this.setContentView(R.layout.activity_mv);

        //获取VideoView组件
        VideoView videoView = (VideoView)this.findViewById(R.id.mv_video);
        //网络视频直链，这里是MV夜曲的直链，直链需要在有效期内使用，否则会失效
        String uri2 = "https://pro-xhb-video.oss-cn-hangzhou.aliyuncs.com/6b245ac7-47c9-4130-87c2-f488c370416d.mp4";
        //MediaController可以用于配合VideoView播放一段视频，它为VideoView提供一个悬浮的操作栏,创建MediaController对象
        MediaController mediaController = new MediaController(this);
        //解析对象,uri1为本地播放,uri2为网络播放
        Uri uri = Uri.parse(uri2);
        //VideoView与MediaController建立关联
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        //播放URI中的文件
        videoView.setVideoURI(uri);
        //让VideoView获得焦点
        videoView.requestFocus();
        //开始播放视频
        videoView.start();
    }
}
