package com.example.dllo.idealmirror.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.dllo.idealmirror.bean.GoodList;
import com.example.dllo.idealmirror.fragment.GoodsListFragment;
import com.example.dllo.idealmirror.fragment.MrtjFragment;
import com.example.dllo.idealmirror.fragment.ShoppingFragment;
import com.example.dllo.idealmirror.fragment.StoryListFragment;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.example.dllo.idealmirror.tool.PopWindow;
import com.example.dllo.idealmirror.tool.Url;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by dllo on 16/3/29.
 */
public class VerticalAdapter extends FragmentPagerAdapter implements Url{

    private ShoppingFragment fragmenta;
    private GoodList datas;
    String store,title;

    public VerticalAdapter(FragmentManager fm, GoodList datas) {
        super(fm);
        this.datas = datas;
        fragmenta = new ShoppingFragment();

    }

    @Override
    public Fragment getItem(int position) {
        if (position<3){
             store = datas.getData().getList().get(position).getStore();
             title = datas.getData().getList().get(position).getTitle();
        }

        LogUtils.d(store);
        switch (position){
            case 0:
                return MrtjFragment.setUrl(INDEX_MRTJ, title, store);
            case 1:
                return GoodsListFragment.setUrl(GOODS_LIST,"269", title, store);
            case 2:
                return GoodsListFragment.setUrl(GOODS_LIST,"268", title, store);
            case 3:
                return GoodsListFragment.setUrl(GOODS_LIST,"268", title, store);
            case 4:
                return StoryListFragment.setUrl(STORY_LIST, title, store);
            case 5:
                return fragmenta;


        }
        return null;
    }

    @Override
    public int getCount() {
        return 6;
    }

}
