package com.example.musiccloud.Nav.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.musiccloud.MvActivity.FirstMvActivity;
import com.example.musiccloud.MvActivity.SecondMvActivity;
import com.example.musiccloud.MvActivity.ThirdMvActivity;
import com.example.musiccloud.R;
import com.example.musiccloud.Room.RecyclerView.HotList.HotListActivity;
import com.example.musiccloud.Room.RecyclerView.NewList.NewListActivity;
import com.example.musiccloud.Room.RecyclerView.VipList.VipListActivity;
import com.example.musiccloud.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements View.OnClickListener {

    //    通过Binding绑定视图，可以更轻松地编写可与视图交互
    private FragmentHomeBinding binding;
    private Button HotSongsBt;
    private Button NewSongsBt;
    private Button VipSongsBt;
    private Button MvBt1;
    private Button MvBt2;
    private Button MvBt3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 组件绑定
        HotSongsBt = root.findViewById(R.id.hotsongs);
        HotSongsBt.setOnClickListener(this);
        NewSongsBt = root.findViewById(R.id.newsongs);
        NewSongsBt.setOnClickListener(this);
        VipSongsBt = root.findViewById(R.id.vipsongs);
        VipSongsBt.setOnClickListener(this);
        MvBt1 = root.findViewById(R.id.mv1);
        MvBt1.setOnClickListener(this);
        MvBt2 = root.findViewById(R.id.mv2);
        MvBt2.setOnClickListener(this);
        MvBt3 = root.findViewById(R.id.mv3);
        MvBt3.setOnClickListener(this);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hotsongs:
                // 设置跳转Intent对象来协助Fragment和Activity的交互与通讯
                Intent intent=new Intent();
                // 设置跳转的类
                intent.setClass(getActivity(), HotListActivity.class);
                // 开始跳转
                startActivity(intent);
                break;

            case R.id.newsongs:
                // 设置跳转Intent对象来协助Fragment和Activity的交互与通讯
                Intent intent2 = new Intent();
                // 设置跳转的类
                intent2.setClass(getActivity(), NewListActivity.class);
                // 开始跳转
                startActivity(intent2);
                break;

            case R.id.vipsongs:
                // 设置跳转Intent对象来协助Fragment和Activity的交互与通讯
                Intent intent3 = new Intent();
                // 设置跳转的类
                intent3.setClass(getActivity(), VipListActivity.class);
                // 开始跳转
                startActivity(intent3);
                break;

            case R.id.mv1:
                // 设置跳转Intent对象来协助Fragment和Activity的交互与通讯
                Intent intent4 = new Intent();
                // 设置跳转的类
                intent4.setClass(getActivity(), FirstMvActivity.class);
                // 开始跳转
                startActivity(intent4);
                break;
            case R.id.mv2:
                // 设置跳转Intent对象来协助Fragment和Activity的交互与通讯
                Intent intent5 = new Intent();
                // 设置跳转的类
                intent5.setClass(getActivity(), SecondMvActivity.class);
                // 开始跳转
                startActivity(intent5);
                break;
            case R.id.mv3:
                // 设置跳转Intent对象来协助Fragment和Activity的交互与通讯
                Intent intent6 = new Intent();
                // 设置跳转的类
                intent6.setClass(getActivity(), ThirdMvActivity.class);
                // 开始跳转
                startActivity(intent6);
                break;
        }

    }
}
