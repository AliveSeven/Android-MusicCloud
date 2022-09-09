package com.example.musiccloud.Room.tools;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musiccloud.R;
import com.example.musiccloud.Room.BasicApp;
import com.example.musiccloud.Room.db.AppDatabase;
import com.example.musiccloud.Room.db.dao.MusicDao;

//自定义对话框
public class MyDialog extends Dialog {
    // 声明各个变量类型
    private String dialogName;
    private TextView tvMsg;
    private Button btnOK;
    private Button btnCancel;

    public MyDialog(Context context, String dialogName) {
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
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_LONG).show();
                //从线程池获取线程
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        // 设置一个布尔值作为中间项
                        boolean delete=false;
                        // 通关BasicApp获取全局数据music
                        BasicApp basicApp=(BasicApp)v.getContext().getApplicationContext();
                        try {
                            // 设置dao
                            MusicDao musicDao = AppDatabase.getInstance(basicApp).musicDao();
                            //删除music
                            //basicApp.getMusic()获取全局数据music，
                            // 该music是在MusicAdapter的onCreateViewHolder的setOnLongClickListener中保存的
                            // 调用dao中的delete方法
                            musicDao.delete(basicApp.getMusic());
                            delete=true;
                        } catch (Exception e) {
                            delete=false;
                            e.printStackTrace();
                        }
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

