package com.example.dllo.idealmirror.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by nan on 16/3/29.
 */
public class BaseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        /**
         * UniversalImageLoader
         */
        Fresco.initialize(this);
        File file = getCacheDir();
        if (file==null){
            file.mkdir();
        }
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480,800)
                .diskCache(new UnlimitedDiscCache(file)).build();
        ImageLoader.getInstance().init(configuration);
    }

    public static Context getContext() {
        return context;
    }
}
