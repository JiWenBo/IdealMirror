package com.example.dllo.idealmirror.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.activity.MainActivity;
import com.example.dllo.idealmirror.adapter.StoryAdapter;
import com.example.dllo.idealmirror.base.BaseFragment;
import com.example.dllo.idealmirror.bean.StoryListBean;
import com.example.dllo.idealmirror.mirrordao.DaoSingleton;
import com.example.dllo.idealmirror.mirrordao.StoryMirror;
import com.example.dllo.idealmirror.mirrordao.StoryMirrorDao;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.example.dllo.idealmirror.tool.Url;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dllo on 16/4/1.
 * 专题分享fragment
 */
public class StoryListFragment extends BaseFragment implements VolleyListener, Url {
    private StoryAdapter storyAdapter;
    private static StoryListBean storyListBean;
    private HashMap<String, String> data;
    private RecyclerView recyclerView;
    private LinearLayout layout;
    private StoryMirror storyMirror;
    private List<StoryMirror> storyMirrors;
    private DaoSingleton daoSingleton;
    String title;
    TextView titleTv;
    private MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

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
                mainActivity.setMenuFrame(getActivity(), "share");
            }
        });
    }

    /**
     * 商品列表list参数
     */
    @Override
    protected void initData() {
        daoSingleton = DaoSingleton.getInstance();
        Bundle bundle = getArguments();
        title = bundle.getString("title");
        data = new HashMap<>();
        NetHelper netHelper = new NetHelper();
        data.put("token", "");
        data.put("device_type", "3");
        netHelper.getInformation(TEST_STORY_LIST, this, data);
        titleTv.setText(title);
    }


    @Override
    public void getSuccess(String body) {
        try {
            JSONObject object = new JSONObject(body);
            Gson gson = new Gson();
            storyListBean = gson.fromJson(object.toString(), StoryListBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        daoSingleton.deleteStoryMirrorAll();
        storyMirrors = new ArrayList<>();
        for (int i = 0; i < storyListBean.getData().getList().size(); i++) {
            storyMirror = new StoryMirror();
            storyMirror.setPicimg(storyListBean.getData().getList().get(i).getStory_img());
            storyMirror.setTitle(storyListBean.getData().getList().get(i).getStory_title());
            storyMirrors.add(storyMirror);
            daoSingleton.insert(storyMirror);
        }
        storyMirrors = daoSingleton.queryStoryMirror();
        storyAdapter = new StoryAdapter(getContext(), storyMirrors);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(storyAdapter);

    }

    @Override
    public void getFail() {
        storyMirrors = daoSingleton.queryStoryMirror();
        storyAdapter = new StoryAdapter(getContext(), storyMirrors);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(storyAdapter);
    }

    /**
     * 通过静态方法将参数传过来
     *
     * @param
     * @return
     */
    public static StoryListFragment setUrl(String title) {
        StoryListFragment storyListFragment = new StoryListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        storyListFragment.setArguments(bundle);
        return storyListFragment;
    }

    public static StoryListBean bean() {
        return storyListBean;
    }
}
