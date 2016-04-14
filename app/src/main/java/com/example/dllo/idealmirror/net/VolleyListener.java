package com.example.dllo.idealmirror.net;

/**
 * Created by dllo on 16/3/30.
 * 网络请求的接口
 */
public interface VolleyListener {
    void getSuccess(String body);
    void getFail();
}
