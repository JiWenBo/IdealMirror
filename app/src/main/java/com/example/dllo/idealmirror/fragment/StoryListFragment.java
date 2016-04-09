package com.example.dllo.idealmirror.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.adapter.StoryAdapter;
import com.example.dllo.idealmirror.base.BaseFragment;
import com.example.dllo.idealmirror.bean.StoryListBean;
import com.example.dllo.idealmirror.mirrordao.DaoSingleton;
import com.example.dllo.idealmirror.mirrordao.StoryMirror;
import com.example.dllo.idealmirror.mirrordao.StoryMirrorDao;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.example.dllo.idealmirror.tool.PopWindow;
import com.example.dllo.idealmirror.tool.Url;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dllo on 16/4/1.
 */
public class StoryListFragment extends BaseFragment implements VolleyListener,Url{
    private StoryAdapter storyAdapter;
    private static StoryListBean storyListBean;
    private HashMap<String,String> data;
    private RecyclerView recyclerView;
    private LinearLayout layout;
    private PopWindow popWindow;
    private StoryMirrorDao storyMirrorDao;
    private StoryMirror storyMirror;
    private List<StoryMirror> storyMirrors;
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
        title = bundle.getString("title");
        store = bundle.getString("store");
        data = new HashMap<>();
        NetHelper netHelper = new NetHelper();
        data.put("token", "");
        data.put("device_type", "3");
        netHelper.getInformation(TEST_STORY_LIST, this, data);
        titleTv.setText(title);
        storyMirrorDao = DaoSingleton.getInstance().getStoryMirrorDao();

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
        storyMirrorDao.deleteAll();
        storyMirrors = new ArrayList<>();
        for (int i=0;i<storyListBean.getData().getList().size();i++){
            storyMirror = new StoryMirror();
            storyMirror.setPicimg(storyListBean.getData().getList().get(i).getStory_img());
            storyMirror.setTitle(storyListBean.getData().getList().get(i).getStory_title());
            storyMirrorDao.insert(storyMirror);
            storyMirrors.add(storyMirror);
        }
        storyMirrors = storyMirrorDao.queryBuilder().list();
        storyAdapter = new StoryAdapter(getContext(),storyMirrors);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(storyAdapter);


    }

    @Override
    public void getFail() {
        LogUtils.d("请求失败");
        storyMirrors = storyMirrorDao.queryBuilder().list();
        storyAdapter = new StoryAdapter(getContext(),storyMirrors);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(storyAdapter);
    }
    /**
     * 通过静态方法将参数传过来
     * @param body
     * @return
     */
    public static StoryListFragment setUrl( String title, String store){
        StoryListFragment storyListFragment = new StoryListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("store", store);
        storyListFragment.setArguments(bundle);
        return storyListFragment;
    }

    public static StoryListBean bean(){
        return storyListBean;
    }
}
