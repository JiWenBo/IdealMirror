package com.example.dllo.idealmirror.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by nan on 16/3/29.
 */
public class BaseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
