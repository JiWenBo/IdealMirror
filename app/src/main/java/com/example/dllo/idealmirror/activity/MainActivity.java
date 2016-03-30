package com.example.dllo.idealmirror.activity;

import android.support.v4.view.DirectionalViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;


import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.adapter.VerticalAdapter;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.fragment.PageFragment;
import com.example.dllo.idealmirror.net.ImageLoaderCache;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.example.dllo.idealmirror.tool.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;

public class MainActivity extends BaseActivity {
    private DirectionalViewPager viewPager;
    private VerticalAdapter verticalAdapter;
    private List<Fragment> data;


    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager = bindView(R.id.viewpager);
    }

    @Override
    protected void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add(new PageFragment());
        }
        verticalAdapter = new VerticalAdapter(getSupportFragmentManager(), data);
        viewPager.setAdapter(verticalAdapter);
        viewPager.setOrientation(DirectionalViewPager.VERTICAL);
    }
}
