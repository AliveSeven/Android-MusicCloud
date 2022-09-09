package com.example.musiccloud.Room.RecyclerView.VipList;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musiccloud.R;
import com.example.musiccloud.Room.db.entity.Songs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VipListActivity extends AppCompatActivity {

    //  vip歌列表用RecyclerView展示
    private RecyclerView VipRecyclerView;

    //  vip歌列表
    private List<Songs> songsList = new ArrayList<>();

    //  适配器
    private VipViewAdapter vipViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_list);
//        初始化控件
//        通过id绑定热歌的RecyclerView组件
        VipRecyclerView = (RecyclerView) findViewById(R.id.vip_recycler);
//        调用API，获取数据
        sendRequestWithOkHttp();

    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    OkHttpClient client = new OkHttpClient();
                    /**
                     * 调用网易云NodeJS的接口，获取歌单id为453912201的详情信息,vip榜
                     * 下面是调用的接口链接，这里写了两个，因为多次调用可能会引起安全验证，所以要这里准备了几个接口备用
                     * 如果一个链接调用之后没有接受到数据，将下面的request的url更换另一个api链接
                     * https://music-cloud-fpgfem8aa-aliveleqi.vercel.app/playlist/detail?id=5453912201
                     * 或者是
                     * http://duoduozuikeail.top:3000/playlist/detail?id=5453912201
                     * https://lianghj.top:3000/playlist/detail?id=5453912201
                     * https://yxcr-music-api.vercel.app/playlist/detail?id=5453912201
                     * */
                    Request request = new Request.Builder()
                            .url("http://duoduozuikeail.top:3000/playlist/detail?id=5453912201")
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

    private void showResponse(){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                //  设置布局管理
                //  设置recyclerview中展现多条数据的方式
                VipRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                //  创建适配器对象
                vipViewAdapter = new VipViewAdapter(songsList);
                //  Recyclerview组件对象设置适配器对象，显示Recyclerview组件
                VipRecyclerView.setAdapter(vipViewAdapter);
            }
        });
    }


}
