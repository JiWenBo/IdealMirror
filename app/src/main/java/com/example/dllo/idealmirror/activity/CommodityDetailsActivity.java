package com.example.dllo.idealmirror.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.idealmirror.adapter.CommodityDetailsAdapter;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.bean.CommodityDetailsData;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.example.dllo.idealmirror.tool.Url;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by LYH on 16/4/1.
 */
public class CommodityDetailsActivity extends BaseActivity implements VolleyListener, Url, View.OnClickListener {

    private HashMap<String, String> parm;
    private Context context;
    private CommodityDetailsData goodsListData;

    //只是为了区分和监听RecyclerView 滑动的监听.
    private RecyclerView recyclerView;
    private CommodityDetailsAdapter commodityDetailsAdapter;
    private int value;
    private boolean visible = false;
    private RelativeLayout commodityDetailsHeadRl, commodityDetailsMenuRl;
    private ImageView returnIv, buyIv;
    private SimpleDraweeView backgroudIv;
    private String id;
    private String backgroud;
    private TextView adornPhotoTv;


    @Override
    protected int setContent() {
        return R.layout.activity_commoditydetails;

    }

    @Override
    protected void initView() {

        recyclerView = bindView(R.id.recyclerview);
        backgroudIv = bindView(R.id.commoditydetails_backgroud_iv);
        returnIv = bindView(R.id.commoditydetails_return_btn);
        adornPhotoTv = bindView(R.id.commoditydetails_adornPhotos);
        buyIv = bindView(R.id.commodityDetails_menu_buy);
        returnIv.setOnClickListener(this);
        adornPhotoTv.setOnClickListener(this);
        buyIv.setOnClickListener(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        backgroud = intent.getStringExtra("backgroudUrl");
        Log.d("ididid", "------" + id);
        Log.d("backgroudUrl", "-----" + backgroud);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("数据确认", "dx =    " + dx + "     " + "dy     " + dy + "   ");

                commodityDetailsHeadRl = (RelativeLayout) findViewById(R.id.commodityDetails_head_rl);
                commodityDetailsMenuRl = (RelativeLayout) findViewById(R.id.commoditydetails_menu_rl);

                /**
                 * 这里的 value 是获得recyclerview 所有的滑动距离,将每一次的滑动距离叠加形成的结果.
                 * */

                value -= dy;
                Log.d("滑动效果", value + "   " + dy);

                //这是Recyclerview 的方法来获得当前的 value 值.
                commodityDetailsAdapter.setScrollValue(value, dy);


                //头布局透明度渐变
                if (dy > 0 && value >= -1110) {
                    commodityDetailsHeadRl.setAlpha((float) (0.5 - (-value) / 0.5 / 1110 * 0.5 / 2));
                } else if (dy < 0 && value >= -1110) {
                    commodityDetailsHeadRl.setAlpha((float) (0.5 - (-value) / 0.5 / 1110 * 0.5 / 2));
                }


                //按钮弹出动画效果
                if (value <= -2600 && visible == false) {
                    commodityDetailsMenuRl.setVisibility(View.VISIBLE);
                    ObjectAnimator animator = ObjectAnimator.ofFloat(commodityDetailsMenuRl, "translationX", -800, 0);
                    animator.setDuration(500);
                    animator.start();
                    visible = true;

                } else if (value >= -2590 && visible == true) {
//                    commodityDetailsMenuRl.setVisibility(View.INVISIBLE);
                    ObjectAnimator animator = ObjectAnimator.ofFloat(commodityDetailsMenuRl, "translationX", 0, -800);
                    animator.setDuration(500);
                    animator.start();
                    visible = false;
                }

            }
        });
    }

    @Override
    protected void initData() {
        NetHelper netHelper = new NetHelper();
        HashMap<String, String> data;
        data = new HashMap<>();
        data.put("device_type", "3");
        data.put("goods_id", id);
        data.put("version", "1.0.1");
        netHelper.getInformation(GOODS_INFO, this, data);

    }


    @Override
    public void getSuccess(String body) {
        try {
            LogUtils.d("请求成功");
            JSONObject object = new JSONObject(body);
            Gson gson = new Gson();
            goodsListData = gson.fromJson(object.toString(), CommodityDetailsData.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        commodityDetailsAdapter = new CommodityDetailsAdapter(goodsListData, context);
        GridLayoutManager gm = new GridLayoutManager(this, 1);
        gm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gm);
        recyclerView.setAdapter(commodityDetailsAdapter);

        //设置背景图片
        Uri uri = Uri.parse(backgroud);
        backgroudIv.setImageURI(uri);
    }

    @Override
    public void getFail() {
        LogUtils.d("请求失败");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commoditydetails_return_btn:
                finish();
                break;
            case R.id.commoditydetails_adornPhotos:
                Intent intentAdorn = new Intent(this, AdornPhotosActivity.class);
                intentAdorn.putExtra("id", id);
                startActivity(intentAdorn);
                break;
            case R.id.commodityDetails_menu_buy:
                break;
        }
    }

}
