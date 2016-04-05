package com.example.dllo.idealmirror.fragment;

import android.os.Bundle;

import android.support.v4.view.DirectionalViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.adapter.PopupAdapter;
import com.example.dllo.idealmirror.adapter.RecyclerAdapter;
import com.example.dllo.idealmirror.adapter.VerticalAdapter;
import com.example.dllo.idealmirror.base.BaseFragment;
import com.example.dllo.idealmirror.bean.GoodsListBean;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.example.dllo.idealmirror.tool.PopWindow;
import com.example.dllo.idealmirror.tool.Url;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by dllo on 16/3/29.
 */
public class GoodsListFragment extends BaseFragment implements VolleyListener, Url {
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private GoodsListBean goodsListBean;
    private HashMap<String,String> parm;
    private LinearLayout layout;
    private PopWindow popWindow;
    private static String title;
    private String store;
    TextView titleTv;

    @Override
    public int getLayout() {
        return R.layout.fragment_goods;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.recycle);
        layout = bindView(R.id.page_layout);
        titleTv = bindView(R.id.goods_fragment_title);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow = new PopWindow(getContext(), store);
                popWindow.showPopWindow(v);
            }
        });
    }

    /**
     * 商品列表list参数
     */
    @Override
    protected void initData() {

        Bundle bundle = getArguments();
        String sd = bundle.getString("body");
        String parms = bundle.getString("parms");
        title = bundle.getString("title");
        store = bundle.getString("store");
        parm = new HashMap<>();
        NetHelper netHelper = new NetHelper();
        parm.put("token", "");
        parm.put("device_type", "3");
        parm.put("category_id",parms);
        netHelper.getInformation(sd, this, parm);
        titleTv.setText(title);
    }


    @Override
    public void getSuccess(String body) {
        try {
            LogUtils.d("请求成功");
            JSONObject object = new JSONObject(body);
            Gson gson = new Gson();
            goodsListBean = gson.fromJson(object.toString(), GoodsListBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerAdapter = new RecyclerAdapter(getContext(),goodsListBean);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);


    }

    @Override
    public void getFail() {
        LogUtils.d("请求失败");
    }
    public static GoodsListFragment setUrl(String body, String parm, String title, String store){
        GoodsListFragment goodsListFragment = new GoodsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("body",body);//url,接口,
        bundle.putString("parms",parm);
        bundle.putString("title", title);
        bundle.putString("store", store);
        goodsListFragment.setArguments(bundle);
        return goodsListFragment;
    }
}
