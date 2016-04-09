package com.example.dllo.idealmirror.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dllo.idealmirror.activity.ShareActivity;
import com.example.dllo.idealmirror.bean.StoryListBean;
import com.example.dllo.idealmirror.fragment.ShareFragmnet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/4/5.
 */
public class ShareAdapter extends FragmentPagerAdapter{
    private List<Fragment> data;


    public ShareAdapter(FragmentManager fm, List<Fragment> data){
        super(fm);
        this.data = data;

    }
    @Override
    public Fragment getItem(int position) {

        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }




}
