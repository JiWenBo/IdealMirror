package com.example.dllo.idealmirror.net;

/**
 * Created by dllo on 16/3/30.
 */
public interface OkHttpListener {
    void getSuccess(String body);
    void getFail();
}
