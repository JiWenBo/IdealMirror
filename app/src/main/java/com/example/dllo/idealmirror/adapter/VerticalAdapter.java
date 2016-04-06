package com.example.dllo.idealmirror.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dllo.idealmirror.fragment.GoodsListFragment;
import com.example.dllo.idealmirror.fragment.MrtjFragment;
import com.example.dllo.idealmirror.fragment.ShoppingFragment;
import com.example.dllo.idealmirror.fragment.StoryListFragment;
import com.example.dllo.idealmirror.tool.Url;

import java.util.List;

/**
 * Created by dllo on 16/3/29.
 */
public class VerticalAdapter extends FragmentPagerAdapter implements Url{
   // List<Fragment> data;
    private ShoppingFragment fragmenta;
    public VerticalAdapter(FragmentManager fm) {
        super(fm);
       // this.data = datafrag;
        fragmenta = new ShoppingFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return MrtjFragment.setUrl(INDEX_MRTJ);
            case 1:
                return GoodsListFragment.setUrl(GOODS_LIST,"269");
            case 2:
                return GoodsListFragment.setUrl(GOODS_LIST,"268");
            case 3:
                return GoodsListFragment.setUrl(GOODS_LIST,"268");
            case 4:
                return StoryListFragment.setUrl(STORY_LIST);
            case 5:
                return fragmenta;

        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return 6;
    }


}
