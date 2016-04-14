package com.example.dllo.idealmirror.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.example.dllo.idealmirror.tool.SmoothImageView;

import java.util.List;

/**
 * 三级页面图片放大
 * Created by LYH on 16/4/9.
 */
public class PicDetailsActivity extends BaseActivity {
    List<String> mDatas;
    private SmoothImageView imageView;

    @Override
    protected int setContent() {
        return R.layout.activity_picdetails;
    }

    @Override
    protected void initData() {
        final Intent intent = getIntent();
//        mDatas = (ArrayList<String>) getIntent().getSerializableExtra("images");
        mDatas = intent.getStringArrayListExtra("images");
        final int mPosition = intent.getIntExtra("position", 0);
        LogUtils.d("PicDetailsActivity", "适配器传过来的position:  " + mPosition);
        LogUtils.d("PicDetailsActivity", "图片list 大小:::  " + mDatas.size());
        for (int i = 0; i < mDatas.size(); i++) {
            LogUtils.d("PicDetailsActivity", "图片网址:" + mDatas.get(i));
        }

        int mLocationX = intent.getIntExtra("locationX", 0);
        int mLocationY = intent.getIntExtra("locationY", 0);
        int mWidth = intent.getIntExtra("width", 0);
        int mHeight = intent.getIntExtra("height", 0);

        imageView = new SmoothImageView(this);
        imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        imageView.transformIn();
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

//        setContentView(imageView);
        setContentView(imageView);

        NetHelper helper = new NetHelper();
        helper.getImage(mDatas.get(mPosition - 1), ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 退出时动画
     */
    @Override
    public void onBackPressed() {
        imageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformComplete(int mode) {
                if (mode == 2) {
                    finish();
                }
            }
        });
        imageView.transformOut();
    }


    @Override
    protected void initView() {
        overridePendingTransition(0, 0);

    }
}
