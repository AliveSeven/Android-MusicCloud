package com.example.musiccloud.Room.RecyclerView.MusicList;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.example.musiccloud.MainActivity;
import com.example.musiccloud.R;
import com.example.musiccloud.Room.BasicApp;
import com.example.musiccloud.Room.db.entity.Music;
import com.example.musiccloud.Room.tools.MyDialog;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


//adapter：适配器，为view提供数据，
//PagingDataAdapter: RecyclerView的适配器，用于为RecyclerView组件提供数据并显示
public class MusicAdapter extends PagingDataAdapter<Music, MusicViewHolder> {
    CallBackValue callBackValue;

    public MusicAdapter() {
        super(DIFF_CALLBACK);
    }

    //重写父类PagedListAdapter的onCreateViewHolder方法，创建代表单条数据记录显示界面的ViewHolder对象
    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化 activity_music_recycler_view_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_music_recycler_view_item, parent, false);

        //根据activity_music_recycler_view_item创建ViewHolder对象
        MusicViewHolder holder = new MusicViewHolder(view);

        //设置监听事件，当用户点击了某个music
        holder.getItemView().setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 接口初始化
                callBackValue = (CallBackValue) parent.getContext();
                /** 调用方法请求网络数据，调用网易云NodeJS的song/url的接口，传入当前item的歌曲id参数获得音乐播放直链
                 * 传入当前item的歌曲name，传到主页面那里，然后主页面可以获取这个值来设置UI组件
                 * */
                getURL(holder.getMusic().getSong_id(),holder.getMusic().getMusicName());
            }
        });

        //设置监听事件，当用户长按了某个music，setOnLong 是长按
        holder.getItemView().setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //将当前笔记信息保存到BasicApp中，使之变为全局数据，其他地方也可获得
                BasicApp basicApp=(BasicApp)parent.getContext().getApplicationContext();
                //设置全局数据basicApp的Music变量
                basicApp.setMusic(holder.getMusic());
                //打开自定义对话框，让用户确认是否要删除当前music
                MyDialog myDialog = new MyDialog(parent.getContext(), "确定要删除吗？");
                //展开删除对话框
                myDialog.show();
                return true;
            }

        });
        return holder;
    }

    //将单条数据记录与显示单条数据的ViewHolder绑定在一块，实现将单条数据显示的功能
    @Override
    public void onBindViewHolder(MusicViewHolder holder, int position) {
        //传入当前子item和其下标，用于赋值
        holder.bindTo(getItem(position) , position);
    }

    public interface CallBackValue {
        public void SendMessageValue(String strValue , String nameValue);
    }

    //DiffUtil是android的一个工具类，是帮助我们在刷新RecyclerView时，计算新老数据集的差异，寻找出旧数据集变化到新数据集的最小变化量。
    // 并自动调用RecyclerView.Adapter的刷新方法，以完成高效刷新并伴有Item动画的效果
    private static DiffUtil.ItemCallback<Music> DIFF_CALLBACK =
        new DiffUtil.ItemCallback<Music>() {
            //判断新旧条数据记录是否相等，用id判断
            @Override
            public boolean areItemsTheSame(Music oldMusic, Music newMusic) {
                return oldMusic.getId()== newMusic.getId();
            }

            //判断新旧条数据记录内容是否相等
            @Override
            public boolean areContentsTheSame(Music oldMusic, Music newMusic) {
                return oldMusic.equals(newMusic);
            }
        };

    private void getURL(String id,String name){
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    OkHttpClient client = new OkHttpClient();
                    /**
                     * 调用网易云NodeJS的接口，获取歌单id为3778678的详情信息，这个是热歌榜
                     * 下面是调用的接口链接，这里写了两个，因为多次调用可能会引起安全验证，所以要这里准备了几个接口备用
                     * 如果一个链接调用之后没有接受到数据，将下面的request的url更换另一个api链接
                     * https://music-cloud-fpgfem8aa-aliveleqi.vercel.app/song/url?id=
                     * 或者是
                     * http://duoduozuikeail.top:3000/song/url?id=
                     * https://lianghj.top:3000/song/url?id=
                     * https://yxcr-music-api.vercel.app/song/url?id=
                     * */
                    Request request = new Request.Builder()
                            .url("http://duoduozuikeail.top:3000/song/url?id="+id)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    //将服务器返回的数据传入到JSONObject对象中
                    JSONObject object = new JSONObject(responseData);
                    //将object中的 playlist{} 作为一个对象传入到 object2 中
                    String songUrl = object.getJSONArray("data").getJSONObject(0).getString("url");
                    callBackValue.SendMessageValue(songUrl,name);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

};

