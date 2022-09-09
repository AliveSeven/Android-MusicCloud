package com.example.musiccloud.Nav.music;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musiccloud.R;
import com.example.musiccloud.Room.RecyclerView.MusicList.MusicAdapter;
import com.example.musiccloud.Room.RecyclerView.MusicList.MusicViewModel;
import com.example.musiccloud.Room.db.entity.Music;

public class MusicFragment extends Fragment {
    // 声明变量
    private View view;
    private RecyclerView mRecyclerView;
    private MusicAdapter musicAdapter;
    MusicViewModel musicViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        // 设置记事本主页的Fragment绑定的Activity
        view = inflater.inflate(R.layout.fragment_music, container, false);
        setHasOptionsMenu(true);
        // 创建Music的RecyclerView适配器
        musicAdapter = new MusicAdapter();
        // 根据music_recycler_view布局文件获得布局对象，即RecyclerView组件的view
        mRecyclerView = view.findViewById(R.id.music_recycler_view);
        //设置RecyclerView的适配器
        mRecyclerView.setAdapter(musicAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        //创建ViewModel，这里默认的ViewModel工厂会向NoteViewModel提供相应的SavedStateHandle，不需要额外添加代码。
        musicViewModel= new ViewModelProvider(this).get(MusicViewModel.class);
        //观察者模式，观察Note数据是否变化，如果变化，将自动更新RecyclerView的UI界面
        musicViewModel.getMusics().observe(getViewLifecycleOwner(), musics -> musicAdapter.submitData(this.getLifecycle(),(PagingData<Music>)musics));
        // 返回视图view
        return view;
    }

    //加载menu
    @Override
    public void  onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // 加载menu的页面
        inflater.inflate(R.menu.recyclerview_menu, menu);
        //应用栏上的搜索组件
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        //为搜索组件绑定事件处理
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {//提交搜索时被自动调用
                //按音乐名模糊查询
                musicViewModel.setMusics(query);
                //recyclerView观察数据
                //观察者模式，观察Note数据是否变化，如果变化，将自动更新RecyclerView的UI界面
                musicViewModel.getMusics().observe(getViewLifecycleOwner(), musics -> musicAdapter.submitData(MusicFragment.this.getLifecycle(),(PagingData<Music>)musics));
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {//代码变化时被自动调用
                return false;
            }
        });
    }

}
