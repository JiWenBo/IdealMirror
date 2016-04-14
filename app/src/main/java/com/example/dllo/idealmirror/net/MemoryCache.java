package com.example.dllo.idealmirror.net;

import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by dllo on 16/3/29.
 */
public class MemoryCache implements ImageLoader.ImageCache{
    //LruCache形式和Map很像，用法和Map类型，key网上的url，第二就是图片
    private LruCache<String,Bitmap> cache;

    public MemoryCache() {
        /*初始化之前要确定占用没存的大小*/
        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);/*最大内存空间*/
        int cacheSize = maxMemory/4;/*占1/4的内存空间*/
        cache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight()/1024;
                /*返回每一张图片的内存大小,按照像素存储，单位kb*/
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {

        return cache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        cache.put(url,bitmap);

    }
}


