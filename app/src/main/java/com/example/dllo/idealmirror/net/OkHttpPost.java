package com.example.dllo.idealmirror.net;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by dllo on 16/3/30.
 */
public class OkHttpPost {
    public void requesPost(FormEncodingBuilder builder,String requestType,final OkHttpListener netListener) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request =  new Request.Builder().
                url("http://api101.test.mirroreye.cn/"+requestType).post(builder.build()).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                netListener.getFail();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String body = response.body().string();
                netListener.getSuccess(body);

            }
        });

    }

}
