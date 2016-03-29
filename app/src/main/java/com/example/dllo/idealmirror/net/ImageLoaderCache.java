package com.example.dllo.idealmirror.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.base.BaseApplication;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.support.v4.util.LruCache;
import android.widget.ImageView;

/**
 * Created by dllo on 16/3/29.
 */
public class ImageLoaderCache {
    private ImageLoader imageLoader;
    // 文件存储路径
    private String diskPath;
    // 请求队列
    RequestQueue requestQueue;

    public ImageLoaderCache() {
        SingleQueue singleQueue = SingleQueue.getInstance();
        requestQueue = singleQueue.getQueue();
    }

    /**
     * 初始化ImageLoader的方法
     * 加载图片缓存，需要缓存是，采用ImageLoader
     */
    public void initImageLoader() {
        // 定义硬盘图片缓存的跟路径
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = Environment.getExternalStorageDirectory();
            diskPath = file.getAbsolutePath();
        } else {
            File file = BaseApplication.getContext().getFilesDir();
            diskPath = file.getAbsolutePath();
        }
        File file = new File(diskPath + "/img");
        if (!file.exists()) {
            file.mkdir();
        }
        diskPath = file.getAbsolutePath();
        imageLoader = new ImageLoader(requestQueue, new DoubleCache());
    }

    /**
     * 双重缓存,三级缓存
     */
    public class DoubleCache implements ImageLoader.ImageCache{
        private MemoryCache memoryCache;
        private DiskCache diskCache;

        public DoubleCache() {
            memoryCache = new MemoryCache();
            diskCache = new DiskCache();
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

    /**
     * 内部类创建，实现硬盘缓存
     */
    public class DiskCache implements ImageLoader.ImageCache {
        @Override
        public Bitmap getBitmap(String url) {
            // 获取url中的图片名称
            String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
            // 用文件名拼接出实际文件存储路径
            String filePath = diskPath + "/" + fileName;
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


    /**
     * 内部类创建的内存缓存对象
     */
    public class MemoryCache implements ImageLoader.ImageCache {
        private LruCache<String, Bitmap> lruCache;
        public MemoryCache() {
            // 获取最大运行内存大小
            int maxSize = (int) (Runtime.getRuntime().maxMemory() / 1024);

            lruCache = new LruCache<String, Bitmap>(maxSize / 4) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight() / 1024;
                }
            };
        }
        @Override
        public Bitmap getBitmap(String url) {
            return lruCache.get(url);
        }
        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            lruCache.put(url, bitmap);
        }
    }

    public ImageLoader getImageLoader(String url,ImageView imageView) {
        initImageLoader();
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get(url, listener);
        return imageLoader;
    }
}
