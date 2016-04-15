package com.example.dllo.idealmirror.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by nan on 16/3/29.
 * Activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绑定布局
        setContentView(setContent());
        // 初始化组件
        initView();
        // 添加数据
        initData();
    }

    protected abstract int setContent();

    protected abstract void initView();

    protected abstract void initData();

    // 初始化组件
    protected <T extends View> T bindView(int id) {
        return (T) findViewById(id);
    }
}
