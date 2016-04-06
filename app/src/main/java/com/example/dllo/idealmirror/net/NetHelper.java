package com.example.dllo.idealmirror.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.example.dllo.idealmirror.tool.Url;

import java.util.HashMap;

import java.util.Map;

/**
 * Created by dllo on 16/3/29.
 */
public class NetHelper {
    RequestQueue requestQueue;
    VolleyListener postListenetr;//接口对象，请求后回调
    private ImageLoader imageLoader;//用来加载网络图片


    public NetHelper() {
        //请求队列的初始化
        SingleQueue singleQueue = SingleQueue.getInstance();
        requestQueue = singleQueue.getQueue();
        imageLoader = new ImageLoader(requestQueue,new ImageLoaderCache());//初始化ImageLoader.请求队列，重点第二个参数

    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    //传入网址
    public void getInformation(String url, VolleyListener postListenetr, HashMap<String, String> param) {
        getDataFromPost(url, postListenetr, param);//调用内部这个方法，获得网络数据信息,第二个参数为head
    }

    //从网络获取信息
    private void getDataFromPost(String url, final VolleyListener postListenetr, final Map<String, String> param) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                postListenetr.getSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                postListenetr.getFail();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return param;
            }
        };

        requestQueue.add(request);

    }

    public void getImageList(String url, ImageLoader.ImageListener listener) {
        imageLoader.get(url, listener, 100, 100);
    }

    public void getImage(String url, ImageLoader.ImageListener listener) {
        imageLoader.get(url, listener);
    }
}

