package com.example.dllo.idealmirror.fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.base.BaseFragment;
import com.example.dllo.idealmirror.bean.StoryListBean;

/**
 * Created by dllo on 16/4/5.
 */
public class ShareFragmnet extends BaseFragment {
    private TextView titles, subtitle, smalltitle;
    @Override
    public int getLayout() {
        return R.layout.fragment_share;
    }

    @Override
    protected void initView() {
        subtitle = bindView(R.id.subtitle);
        titles = bindView(R.id.titles);
        smalltitle = bindView(R.id.smalltitle);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        StoryListBean.DataEntity.ListEntity.StoryDataEntity.TextArrayEntity listBean = bundle.getParcelable("shareBean");
        smalltitle.setText(listBean.getSmallTitle());
        subtitle.setText(listBean.getSubTitle());
        titles.setText(listBean.getTitle());
    }

}
