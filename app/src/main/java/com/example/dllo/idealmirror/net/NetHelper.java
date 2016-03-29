package com.example.dllo.idealmirror.net;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;

import java.util.Map;

/**
 * Created by dllo on 16/3/29.
 */
public class NetHelper {
    RequestQueue requestQueue;
    NetListener netListener;//接口对象，请求后回调
    private ImageLoader imageLoader;//用来加载网络图片
    public NetHelper(){
        //请求队列的初始化
        SingleQueue singleQueue = SingleQueue.getInstance();
        requestQueue = singleQueue.getQueue();
        imageLoader = new ImageLoader(requestQueue,new MenoryCache());//初始化ImageLoader.请求队列，重点第二个参数
        //
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    //传入网址
    public void  getInformation(String urlid,NetListener netListener){
        String idUrl = urlid;
        Log.e("sss", idUrl);
        Map<String,String> head = new HashMap<>();/*头信息*/
        head.put("apikey", "null");
        getDataFromNet(idUrl,head,netListener);//调用内部这个方法，获得网络数据信息
    }
    //从网络获取信息
    private void getDataFromNet(String url, final Map head, final NetListener netListener){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //成功回调
                netListener.getSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //错误回调
                netListener.getFailed("失败");
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (head!=null){
                    return head;//如果有头像的信息，则返回头像信息
                }
                return super.getHeaders();
            }

        };
        requestQueue.add(request);
    }

    public void getImageList(String url, ImageLoader.ImageListener listener){
        imageLoader.get(url, listener, 100, 100);
    }
    public void getImage(String url, ImageLoader.ImageListener listener){
        imageLoader.get(url,listener);
    }
}

