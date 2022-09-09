package com.example.musiccloud;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Player implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener{

//  声明变量
//  安卓中的MediaPlayer类可用于控制音频/视频文件或流的播放
    public MediaPlayer mediaPlayer;
//  进度条
    private SeekBar skbProgress;
//  定时器
    private Timer mTimer = new Timer();
//  播放链接
    private String videoUrl;
//  暂停布尔值
    private boolean pause;
//  音乐时长
    private TextView tv_total;

//  初始化Player
    public Player(String videoUrl, SeekBar skbProgress,TextView tv_total) {
//       赋值
        this.skbProgress = skbProgress;
        this.videoUrl = videoUrl;
        this.tv_total = tv_total;
        try {
//            初始化mediaPlayer对象
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
        } catch (Exception e) {
//            输出报错信息
            Log.e("mediaPlayer", "error", e);
        }

        mTimer.schedule(mTimerTask, 0, 1000);
    }

    /*******************************************************
     * 通过定时器和Handler来更新进度条
     ******************************************************/
    TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            if (mediaPlayer == null)
                return;
            if (mediaPlayer.isPlaying() && skbProgress.isPressed() == false) {
                handleProgress.sendEmptyMessage(0);
            }
        }
    };

    Handler handleProgress = new Handler() {
        public void handleMessage(Message msg) {
            int position = mediaPlayer.getCurrentPosition();
            //  获取音乐时长,然后进行预处理
            int duration = mediaPlayer.getDuration();
            if (duration > 0) {
                long pos = skbProgress.getMax() * position / duration;
                skbProgress.setProgress((int) pos);
                //歌曲的总时长
                int minute = duration / 1000 / 60;
                int second = duration / 1000 % 60;
                String strMinute = null;
                String strSecond = null;
                if (minute < 10) {              //如果歌曲的时间中的分钟小于10
                    strMinute = "0" + minute; //在分钟的前面加一个0
                } else {
                    strMinute = minute + "";
                }
                if (second < 10) {             //如果歌曲的时间中的秒钟小于10
                    strSecond = "0" + second;//在秒钟前面加一个0
                } else {
                    strSecond = second + "";
                }
                tv_total.setText(strMinute + ":" + strSecond);

            }
        };
    };

    /**
     * 播放
     */
    public void play() {
        playNet(0);
    }

    /**
     * 重播
     */
    public void replay() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);// 从开始位置开始播放音乐
        } else {
            playNet(0);
        }
    }

    /**
     * 暂停
     */
    public boolean pause() {
        if (mediaPlayer.isPlaying()) {// 如果正在播放
            mediaPlayer.pause();// 暂停
            pause = true;
        } else {
            if (pause) {// 如果处于暂停状态
                mediaPlayer.start();// 继续播放
                pause = false;
            }
        }
        return pause;
    }

    /**
     * 停止
     */
    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    @Override
    /**
     * 通过onPrepared播放
     */
    public void onPrepared(MediaPlayer arg0) {
        arg0.start();
        Log.e("mediaPlayer", "onPrepared");
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        Log.e("mediaPlayer", "onCompletion");
    }

    @Override
    public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress) {
        skbProgress.setSecondaryProgress(bufferingProgress);
//        缓冲
        int currentProgress = skbProgress.getMax()
                * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
//        输出歌曲缓冲信息
        Log.e(currentProgress + "% play", bufferingProgress + "% buffer");
    }

    /**
     * 播放音乐
     *
     * @param playPosition
     */
    private void playNet(int playPosition) {
        try {
            mediaPlayer.reset();// 把各项参数恢复到初始状态
            mediaPlayer.setDataSource(videoUrl);
            mediaPlayer.prepare();// 进行缓冲
            mediaPlayer.setOnPreparedListener(new MyPreparedListener(
                    playPosition));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final class MyPreparedListener implements
            android.media.MediaPlayer.OnPreparedListener {
        private int playPosition;

        public MyPreparedListener(int playPosition) {
            this.playPosition = playPosition;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start();// 开始播放
            if (playPosition > 0) {
                mediaPlayer.seekTo(playPosition);
            }
        }
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

}
