package com.example.dllo.idealmirror.net;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.dllo.idealmirror.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * 单例化UniversalImageLoader
 * Created by dllo on 16/4/18.
 */
public class SingleUniverImage {
    private static SingleUniverImage singleUniverImage = null;

    private SingleUniverImage() {
        /*构造方法私有化*/
    }

    public static SingleUniverImage getSingleUniverImage(){
        if (singleUniverImage == null) {
            synchronized (SingleUniverImage.class) {
                if (singleUniverImage == null) {
                    singleUniverImage = new SingleUniverImage();
                }
            }

        }
        return singleUniverImage;

    }

    /**
     * 设置图片
     * @param imgUrl  图片的地址
     * @param img     布局
     * @param bar     progressbar
     */
    public void setImageRes(String imgUrl, final ImageView img, final ProgressBar bar){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)//允许硬盘缓存
        .cacheInMemory(true)
        .build();
        ImageLoader.getInstance().loadImage(imgUrl,options,new SimpleImageLoadingListener(){


            @Override
            public void onLoadingStarted(String imageUri, View view) {
                super.onLoadingStarted(imageUri, view);
                bar.setVisibility(View.VISIBLE);
            }


            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                img.setImageBitmap(loadedImage);
                bar.setVisibility(View.GONE);



            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                super.onLoadingCancelled(imageUri, view);
            }
        });
    }

}
