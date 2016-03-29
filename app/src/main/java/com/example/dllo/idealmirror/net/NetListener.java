package com.example.dllo.idealmirror.net;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by dllo on 16/3/29.
 */
public interface NetListener {
    void getSuccess(JSONObject jsonObject);
    void getFailed(String s);

}


