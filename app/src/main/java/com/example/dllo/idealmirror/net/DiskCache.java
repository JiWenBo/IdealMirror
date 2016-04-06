package com.example.dllo.idealmirror.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.idealmirror.tool.LogUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dllo on 16/4/6.
 */
public class DiskCache implements ImageLoader.ImageCache {
    private String diskPath;

    public DiskCache(String diskPath) {
        this.diskPath = diskPath;
    }

    @Override
        public Bitmap getBitmap(String url) {
            // 获取url中的图片名称

            String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
           LogUtils.d("存储url",url);
            // 用文件名拼接出实际文件存储路径
            String filePath = diskPath + "/" + fileName;
            LogUtils.d("存储路径",filePath);
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            return bitmap;
        }
        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            // 获取url中的图片名称
            String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
            // 用文件名拼接出实际文件存储路径
            String filePath = diskPath + "/" + fileName;

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(filePath);
                // 将bitmap对象写入文件中
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                // 关闭文件流
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
}

