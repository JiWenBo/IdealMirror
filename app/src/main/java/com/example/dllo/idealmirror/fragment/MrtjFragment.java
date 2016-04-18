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
import com.example.dllo.idealmirror.adapter.MrtjAdapter;
import com.example.dllo.idealmirror.base.BaseFragment;
import com.example.dllo.idealmirror.bean.MrtjListBean;
import com.example.dllo.idealmirror.mirrordao.AllMirrorCache;
import com.example.dllo.idealmirror.mirrordao.DaoSingleton;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dllo on 16/4/1.
 */
public class MrtjFragment extends BaseFragment implements VolleyListener {
    private MrtjAdapter mrtjAdapter;
    private MrtjListBean mrtjListBean;
    private HashMap<String, String> data;
    private RecyclerView recyclerView;
    private LinearLayout layout;
    private String title;
    private String store;
    TextView titleTv;
    private List<AllMirrorCache> mirrorData;
    private AllMirrorCache allMirrorCache;
    private MainActivity mainActivity;
    private DaoSingleton daoSingleton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_mrtj;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.mrtj_recycle);
        layout = bindView(R.id.mrtj_layout);
        titleTv = bindView(R.id.mrtj_title);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setMenuFrame(getActivity(), store);
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

        daoSingleton = DaoSingleton.getInstance();

    }


    @Override
    public void getSuccess(String body) {
        try {
            LogUtils.d(mainActivity.getString(R.string.Mrtj_requestseccess));
            JSONObject object = new JSONObject(body);
            Gson gson = new Gson();
            mrtjListBean = gson.fromJson(object.toString(), MrtjListBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 第一步删除数据库中的所有数据
        daoSingleton.deleteAllMirrorAll();
        // 第二步:将新数据再加入数据库中
        allMirrorCache = new AllMirrorCache();
        allMirrorCache.setImgurl(mrtjListBean.getData().getList().get(0).getData_info().getGoods_img());
        allMirrorCache.setBrand(mrtjListBean.getData().getList().get(0).getData_info().getBrand());
        allMirrorCache.setGoodname(mrtjListBean.getData().getList().get(0).getData_info().getGoods_name());
        allMirrorCache.setGoodprice(mrtjListBean.getData().getList().get(0).getData_info().getGoods_price());
        allMirrorCache.setProductarea(mrtjListBean.getData().getList().get(0).getData_info().getProduct_area());
        allMirrorCache.setProductarea(mrtjListBean.getData().getList().get(0).getData_info().getGoods_id());
        LogUtils.d(mrtjListBean.getData().getList().get(0).getData_info().getGoods_id());
        daoSingleton.insert(allMirrorCache);

        mrtjAdapter = new MrtjAdapter(getContext(), mrtjListBean);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mrtjAdapter);
    }

    @Override
    public void getFail() {
        // 失败之后调用数据库取出数据
        mirrorData = new ArrayList<>();

        mirrorData = daoSingleton.queryAllMirror();
        mrtjAdapter = new MrtjAdapter(getActivity());
        mrtjAdapter.getData(mirrorData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mrtjAdapter);
    }
    /**
     * 通过静态方法将参数传过来
     *
     * @param body
     * @return
     */
    public static MrtjFragment setUrl(String body, String title, String store) {
        MrtjFragment mrtjFragment = new MrtjFragment();
        Bundle bundle = new Bundle();
        bundle.putString("body", body);
        bundle.putString("title", title);
        bundle.putString("store", store);
        mrtjFragment.setArguments(bundle);
        return mrtjFragment;
    }
}
