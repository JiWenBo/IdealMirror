package com.example.dllo.idealmirror.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;


import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.net.ImageLoaderCache;
import com.example.dllo.idealmirror.net.NetHelper;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageview);
        NetHelper netHelper = new NetHelper();
        ImageLoader imageLoader = netHelper.getImageLoader();
        String url ="http://img4.imgtn.bdimg.com/it/u=1730609487,964171457&fm=21&gp=0.jpg";
        imageLoader.get(url, ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
        new ImageLoaderCache().getImageLoader(url, imageView);
    }
}
