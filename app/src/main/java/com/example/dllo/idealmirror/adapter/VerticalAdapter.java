package com.example.dllo.idealmirror.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.dllo.idealmirror.bean.GoodList;
import com.example.dllo.idealmirror.fragment.GoodsListFragment;
import com.example.dllo.idealmirror.fragment.MrtjFragment;
import com.example.dllo.idealmirror.fragment.StoryListFragment;
import com.example.dllo.idealmirror.tool.PopWindow;
import com.example.dllo.idealmirror.tool.Url;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by dllo on 16/3/29.
 */
public class VerticalAdapter extends FragmentPagerAdapter implements Url{
    private GoodList datas;

    public VerticalAdapter(FragmentManager fm, GoodList datas) {
        super(fm);
        this.datas = datas;
    }

    @Override
    public Fragment getItem(int position) {
        String store = datas.getData().getList().get(position).getStore();
        String title = datas.getData().getList().get(position).getTitle();
        switch (position){
            case 0:
                return MrtjFragment.setUrl("http://api101.test.mirroreye.cn/index.php/index/mrtj", title, store);
            case 1:
                return GoodsListFragment.setUrl("http://api101.test.mirroreye.cn/index.php/products/goods_list","269", title, store);
            case 2:
                return GoodsListFragment.setUrl("http://api101.test.mirroreye.cn/index.php/products/goods_list","268", title, store);
            case 3:
                return GoodsListFragment.setUrl("http://api101.test.mirroreye.cn/index.php/products/goods_list","268", title, store);
            case 4:
                return StoryListFragment.setUrl("http://api101.test.mirroreye.cn/index.php/story/story_list", title, store);

        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

}
