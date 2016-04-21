package com.example.dllo.idealmirror.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.DirectionalViewPager;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.adapter.ShareAdapter;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.bean.StoryListBean;
import com.example.dllo.idealmirror.fragment.ShareFragment;
import com.example.dllo.idealmirror.fragment.StoryListFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

/**
 * Created by JWB on 16/4/5.
 * 专题分享界面
 */
public class ShareActivity extends BaseActivity {
    private DirectionalViewPager viewPagers;
    private ShareAdapter adapter;
    private StoryListBean beans;
    private static int currentPosition;
    private SimpleDraweeView draweeView;

    @Override
    protected int setContent() {
        return R.layout.activity_share;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        currentPosition = Integer.parseInt(intent.getStringExtra("posi"));
        viewPagers = bindView(R.id.viewpager);
        draweeView = (SimpleDraweeView) findViewById(R.id.img_back);
    }

    @Override
    protected void initData() {
        beans = new StoryListBean();
        beans = StoryListFragment.bean();
        List<Fragment> data = new ArrayList<>();
        for (int i = 0; i < beans.getData().getList().get(1).getStory_data().getImg_array().size(); i++) {
            ShareFragment shareFragment = new ShareFragment();
            StoryListBean.DataEntity.ListEntity.StoryDataEntity.TextArrayEntity listBean = beans.getData().getList().get(currentPosition).getStory_data().getText_array().get(i);
            Bundle bundle = new Bundle();
            bundle.putParcelable("shareBean", listBean);
            shareFragment.setArguments(bundle);
            data.add(shareFragment);
        }
        adapter = new ShareAdapter(getSupportFragmentManager(), data);
        viewPagers.setAdapter(adapter);
        viewPagers.setOrientation(DirectionalViewPager.VERTICAL);

        /* 给一个初始的值,以免第一次进入时没有数据 */
        setImgAndText(0);


        viewPagers.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setImgAndText(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setImgAndText(int position) {
        String url = beans.getData().getList().get(currentPosition).getStory_data().getImg_array().get(position);
        draweeView.setImageURI(Uri.parse(url));
    }
}
