package com.example.dllo.idealmirror.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.adapter.MrtjAdapter;
import com.example.dllo.idealmirror.adapter.StoryAdapter;
import com.example.dllo.idealmirror.base.BaseFragment;
import com.example.dllo.idealmirror.bean.MrtjListBean;
import com.example.dllo.idealmirror.bean.StoryListBean;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.example.dllo.idealmirror.tool.PopWindow;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by dllo on 16/4/1.
 */
public class StoryListFragment extends BaseFragment implements VolleyListener {
    private StoryAdapter storyAdapter;
    private StoryListBean storyListBean;
    private HashMap<String,String> data;
    private RecyclerView recyclerView;
    private LinearLayout layout;
    private PopWindow popWindow;
    String title;
    String store;
    TextView titleTv;


    @Override
    public int getLayout() {
        return R.layout.fragment_story;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.story_recycle);
        layout = bindView(R.id.story_layout);
        titleTv = bindView(R.id.story_title);
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
        title = bundle.getString("title");
        store = bundle.getString("store");
        data = new HashMap<>();
        NetHelper netHelper = new NetHelper();
        data.put("token", "");
        data.put("device_type", "3");
        netHelper.getInformation(sd, this, data);
        titleTv.setText(title);
    }


    @Override
    public void getSuccess(String body) {
        try {
            LogUtils.d("请求成功");
            JSONObject object = new JSONObject(body);
            Gson gson = new Gson();
            storyListBean = gson.fromJson(object.toString(), StoryListBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        storyAdapter = new StoryAdapter(getContext(),storyListBean);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(storyAdapter);


    }

    @Override
    public void getFail() {
        LogUtils.d("请求失败");
    }
    /**
     * 通过静态方法将参数传过来
     * @param body
     * @return
     */
    public static StoryListFragment setUrl(String body, String title, String store){
        StoryListFragment storyListFragment = new StoryListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("body",body);
        bundle.putString("title", title);
        bundle.putString("store", store);
        storyListFragment.setArguments(bundle);
        return storyListFragment;
    }
}
