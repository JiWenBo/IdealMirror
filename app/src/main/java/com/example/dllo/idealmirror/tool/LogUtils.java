package com.example.dllo.idealmirror.tool;

import android.util.Log;

/**
 * Created by nan on 16/3/30.
 */
public class LogUtils {
    public static final boolean DEBUG = true;
    private static final String TAG = "IdealMirror";

    private LogUtils() {
    }

    /**
     * 使用默认的TAG
     * @param msg 打印的信息
     */
    public static void i(String msg) {
        if (DEBUG)
            Log.d(TAG, msg);
    }

    public static void d(String msg) {
        if (DEBUG)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (DEBUG)
            Log.d(TAG, msg);
    }

    public static void v(String msg) {
        if (DEBUG)
            Log.d(TAG, msg);
    }

    public static void w(String msg) {
        if (DEBUG)
            Log.d(TAG, msg);
    }

    /**
     * 自定义tag
     * @param tag TAG
     * @param msg 打印的数据
     */
    public static void i(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }

}
