package com.example.dllo.idealmirror.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.base.BaseFragment;
import com.example.dllo.idealmirror.bean.StoryListBean;

/**
 * Created by dllo on 16/4/5.
 */
public class ShareFragment extends BaseFragment {
    private TextView titles, subTitle, smallTitle;

    @Override
    public int getLayout() {
        return R.layout.fragment_share;
    }

    @Override
    protected void initView() {
        subTitle = bindView(R.id.sub_title);
        titles = bindView(R.id.titles);
        smallTitle = bindView(R.id.small_title);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        StoryListBean.DataEntity.ListEntity.StoryDataEntity.TextArrayEntity listBean = bundle.getParcelable("shareBean");
        smallTitle.setText(listBean.getSmallTitle());
        subTitle.setText(listBean.getSubTitle());
        titles.setText(listBean.getTitle());
    }

}
