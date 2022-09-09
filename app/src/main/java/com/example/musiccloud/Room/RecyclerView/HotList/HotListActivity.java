package com.example.musiccloud.Room.RecyclerView.HotList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.musiccloud.R;
import com.example.musiccloud.Room.db.entity.Songs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//  热歌榜的Activity
public class HotListActivity extends AppCompatActivity {

//  热歌列表用RecyclerView展示
    private RecyclerView HotRecyclerView;

//  热歌列表
    private List<Songs> songsList = new ArrayList<>();

//  适配器
    private HotViewAdapter hotViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_list);
//        初始化控件
//        通过id绑定热歌的RecyclerView组件
        HotRecyclerView = (RecyclerView) findViewById(R.id.hot_recycler);
//        调用API，获取数据
        sendRequestWithOkHttp();

    }

//    调用网易云NodeJS的接口，获取热歌榜的数据
    private void sendRequestWithOkHttp(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    OkHttpClient client = new OkHttpClient();
                    /**
                     * 调用网易云NodeJS的接口，获取歌单id为3778678的详情信息，这个是热歌榜
                     * 下面是调用的接口链接，这里写了两个，因为多次调用可能会引起安全验证，所以要这里准备了几个接口备用
                     * 如果一个链接调用之后没有接受到数据，将下面的request的url更换另一个api链接
                     * https://music-cloud-fpgfem8aa-aliveleqi.vercel.app/playlist/detail?id=3778678
                     * 或者是
                     * http://duoduozuikeail.top:3000/playlist/detail?id=3778678
                     * https://lianghj.top:3000/playlist/detail?id=3778678
                     * https://yxcr-music-api.vercel.app/playlist/detail?id=3778678
                     * */
                    //发起request请求，请求playlist（歌单）的id为3778678（热歌榜的id）的数据
                    Request request = new Request.Builder()
                            .url("http://duoduozuikeail.top:3000/playlist/detail?id=3778678")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    //将服务器返回的数据传入到JSONObject对象中
                    JSONObject object = new JSONObject(responseData);
                    //将object中的 playlist{} 作为一个对象传入到 object2 中
                    JSONObject object2 = object.getJSONObject("playlist");

                    //由于tracks[]它是一个数组的形式，
                    //我们需要调用getJSONArray()方法并传给 JSONArray
                    JSONArray jsonArray = object2.getJSONArray("tracks");

                    for(int i = 0; i < jsonArray.length(); i++){
                        //通过角标获取"数组"的对象
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //通过调用getString("划红线部分的键名")方法获取想要的数据
                        String songName = jsonObject.getString("name");
                        //歌手在tracks[i].ar[0].name里面
                        String songSinger = jsonObject.getJSONArray("ar").getJSONObject(0).getString("name");
                        //歌曲播放id在tracks[i].id里面
                        String songID = jsonObject.getString("id");
                        //赋值
                        songsList.add(new Songs(songName,songSinger,songID));
                    }
                    showResponse();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

//    调到UI线程，也就是主线程里面展开RecyclerView
    private void showResponse(){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                //  设置布局管理
                //  设置recyclerview中展现多条数据的方式
                HotRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                //  创建适配器对象
                hotViewAdapter = new HotViewAdapter(songsList);
                //  Recyclerview组件对象设置适配器对象，显示Recyclerview组件
                HotRecyclerView.setAdapter(hotViewAdapter);
            }
        });
    }

}