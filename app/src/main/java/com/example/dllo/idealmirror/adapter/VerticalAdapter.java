package com.example.dllo.idealmirror.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by dllo on 16/3/29.
 */
public class VerticalAdapter extends FragmentPagerAdapter{
    List<Fragment> data;
    public VerticalAdapter(FragmentManager fm,List<Fragment> datafrag) {
        super(fm);
        this.data = datafrag;
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
