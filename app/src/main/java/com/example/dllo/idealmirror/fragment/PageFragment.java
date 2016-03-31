package com.example.dllo.idealmirror.fragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.adapter.RecyclerAdapter;
import com.example.dllo.idealmirror.base.BaseFragment;
import com.example.dllo.idealmirror.bean.GoodsListBean;
import com.example.dllo.idealmirror.net.OkHttpListener;
import com.example.dllo.idealmirror.net.OkHttpPost;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dllo on 16/3/29.
 */
public class PageFragment extends BaseFragment implements OkHttpListener{
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private GoodsListBean goodsListBean;



    @Override
    public int getLayout() {
        return R.layout.fragment_page;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.recycle);

    }

    @Override
    protected void initData() {
        recyclerAdapter = new RecyclerAdapter(getContext());
        getPostData();



    }
    private void getPostData(){
        OkHttpPost httppost = new OkHttpPost();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("token","");
        builder.add("last_time","");
        builder.add("device_type","3");
        builder.add("category_id","269");
        builder.add("version","1.0.1");
        builder.add("page","");
        httppost.requesPost(builder, "index.php/products/goods_list",this);
    }

    @Override
    public void getSuccess(String body) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(body);
            Gson gson = new Gson();
            goodsListBean=new GoodsListBean();
            goodsListBean= gson.fromJson(jsonObject.toString(), GoodsListBean.class);
            Log.d("数据", goodsListBean.toString());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(recyclerAdapter);



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getFail() {

    }
}
