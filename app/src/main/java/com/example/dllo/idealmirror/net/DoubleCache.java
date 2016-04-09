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
      //  private String diskPathurl;//文件路径

        public DoubleCache(String diskPathurl) {
            memoryCache = new MenoryCache();
            diskCache = new DiskCache(diskPathurl);
        }

        @Override
        public Bitmap getBitmap(String url) {
            Bitmap bitmap = memoryCache.getBitmap(url);
            if (bitmap == null) {

                bitmap = diskCache.getBitmap(url);
            }

            return bitmap;
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            memoryCache.putBitmap(url, bitmap);
            diskCache.putBitmap(url,bitmap);
        }
}


