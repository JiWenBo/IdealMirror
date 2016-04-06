package com.example.dllo.idealmirror.net;

import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.idealmirror.tool.LogUtils;

/**
 * Created by dllo on 16/4/6.
 */
public class DoubleCache implements ImageLoader.ImageCache{

        private MenoryCache memoryCache;
        private DiskCache diskCache;

        public DoubleCache() {
            memoryCache = new MenoryCache();
            //diskCache = new DiskCache();
        }

        @Override
        public Bitmap getBitmap(String url) {
            Bitmap bitmap = memoryCache.getBitmap(url);
            if (bitmap == null) {
                LogUtils.d("diskcache");
                bitmap = diskCache.getBitmap(url);
            }
            LogUtils.d("diskcache2");
            return bitmap;
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            memoryCache.putBitmap(url, bitmap);
            diskCache.putBitmap(url,bitmap);
        }
}


