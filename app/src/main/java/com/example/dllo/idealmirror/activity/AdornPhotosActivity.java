package com.example.dllo.idealmirror.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.adapter.AdornPhotosRcAdapter;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.bean.AdornPhotosData;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.example.dllo.idealmirror.tool.Url;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by LYH on 16/4/8.
 * 佩戴图集界面
 */
public class AdornPhotosActivity extends BaseActivity implements VolleyListener,Url, View.OnClickListener {

    private AdornPhotosData adornPhotosData;
    private List<AdornPhotosData.DataEntity.WearVideoEntity> list;
    private RecyclerView recyclerView;
    private Context context = this;
    private AdornPhotosRcAdapter adornPhotosRcAdapter;
    private String id;
    private ImageView returnIv, buyIv;

    @Override
    protected int setContent() {
        return R.layout.activity_adornphotos;
    }


    @Override
    protected void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.adornPhotos_recyclerview);
        returnIv = (ImageView) findViewById(R.id.adornPhotos_return_btn);
        buyIv = (ImageView) findViewById(R.id.adornPhotos_buy_btn);
        returnIv.setOnClickListener(this);
        buyIv.setOnClickListener(this);
        // 从二级界面获得id
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }

    @Override
    protected void initData() {
        NetHelper netHelper = new NetHelper();
        HashMap<String,String> data;
        data = new HashMap<>();
        data.put("device_type","3");
        data.put("version","1.0.1");
        data.put("goods_id",id);
        netHelper.getInformation(GOODS_INFO, this, data);


    }

    @Override
    public void getSuccess(String body) {
        try {
            JSONObject object = new JSONObject(body);
            Gson gson = new Gson();
            adornPhotosData = gson.fromJson(object.toString(), AdornPhotosData.class);
            list = adornPhotosData.getData().getWear_video();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        adornPhotosRcAdapter = new AdornPhotosRcAdapter(list, context);

        recyclerView.setAdapter(adornPhotosRcAdapter);
        GridLayoutManager gm = new GridLayoutManager(this, 1);
        gm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gm);

    }

    @Override
    public void getFail() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.adornPhotos_return_btn: //佩戴图集返回
                finish();
                break;
            case R.id.adornPhotos_buy_btn: //购买
                Intent intent = new Intent(this, BuyDetailsActivity.class);
                intent.putExtra("good_pic", adornPhotosData.getData().getGoods_pic());
                intent.putExtra("good_name", adornPhotosData.getData().getGoods_name());
                intent.putExtra("good_price", adornPhotosData.getData().getGoods_price());
                startActivity(intent);
                break;
        }
    }
}
