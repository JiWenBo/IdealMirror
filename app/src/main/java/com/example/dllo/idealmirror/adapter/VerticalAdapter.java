package com.example.dllo.idealmirror.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dllo.idealmirror.fragment.GoodsListFragment;
import com.example.dllo.idealmirror.fragment.MrtjFragment;
import com.example.dllo.idealmirror.fragment.StoryListFragment;
import com.example.dllo.idealmirror.tool.Url;

import java.util.List;

/**
 * Created by dllo on 16/3/29.
 */
public class VerticalAdapter extends FragmentPagerAdapter implements Url{
   // List<Fragment> data;
    public VerticalAdapter(FragmentManager fm) {
        super(fm);
       // this.data = datafrag;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return MrtjFragment.setUrl("http://api101.test.mirroreye.cn/index.php/index/mrtj");
            case 1:
                return GoodsListFragment.setUrl("http://api101.test.mirroreye.cn/index.php/products/goods_list","269");
            case 2:
                return GoodsListFragment.setUrl("http://api101.test.mirroreye.cn/index.php/products/goods_list","268");
            case 3:
                return GoodsListFragment.setUrl("http://api101.test.mirroreye.cn/index.php/products/goods_list","268");
            case 4:
                return StoryListFragment.setUrl("http://api101.test.mirroreye.cn/index.php/story/story_list");

        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return 5;
    }


}
