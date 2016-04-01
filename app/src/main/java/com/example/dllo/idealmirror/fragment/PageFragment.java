package com.example.dllo.idealmirror.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.adapter.RecyclerAdapter;
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
public class PageFragment extends BaseFragment implements VolleyListener, Url {
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private GoodsListBean goodsListBean;
    private HashMap<String, String> parm;
    private LinearLayout layout;
    private PopWindow popWindow;

    @Override
    public int getLayout() {
        return R.layout.fragment_page;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.recycle);
        layout = bindView(R.id.page_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow = new PopWindow(getContext());
                popWindow.showPopWindow(v);
            }
        });
    }

    @Override
    protected void initData() {
        recyclerAdapter = new RecyclerAdapter(getContext(), goodsListBean);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
        parm = new HashMap<>();
        NetHelper netHelper = new NetHelper();
        parm.put("token", "");
        parm.put("device_type", "3");
        parm.put("page", "");
        parm.put("last_time", "271");
        parm.put("category_id", "");
        netHelper.getInformation(GOODS_LIST, this, parm);
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


    }

    @Override
    public void getFail() {
        LogUtils.d("请求失败");
    }
}
