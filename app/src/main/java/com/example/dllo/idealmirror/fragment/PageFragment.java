package com.example.dllo.idealmirror.fragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.adapter.RecyclerAdapter;
import com.example.dllo.idealmirror.base.BaseFragment;

/**
 * Created by dllo on 16/3/29.
 */
public class PageFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

    }
}
