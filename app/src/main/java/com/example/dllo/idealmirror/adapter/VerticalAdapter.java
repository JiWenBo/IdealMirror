package com.example.dllo.idealmirror.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dllo.idealmirror.fragment.GoodsListFragment;
import com.example.dllo.idealmirror.fragment.MrtjFragment;
import com.example.dllo.idealmirror.fragment.ShoppingFragment;
import com.example.dllo.idealmirror.fragment.StoryListFragment;
import com.example.dllo.idealmirror.mirrordao.GoodListCache;
import com.example.dllo.idealmirror.tool.Url;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by dllo on 16/3/29.
 */
public class VerticalAdapter extends FragmentPagerAdapter implements Url {

    private ShoppingFragment fragmenta;
    String store;
    private List<GoodListCache> data;

    public VerticalAdapter(FragmentManager fm, List<GoodListCache> datas) {
        super(fm);
        this.data = datas;
        fragmenta = new ShoppingFragment();
    }

    @Override
    public Fragment getItem(int position) {

        store = data.get(position).getStore();

        if (data.get(position).getType().equals("6")){
            return MrtjFragment.setUrl(INDEX_MRTJ, data.get(position).getTitle(), store);
        }
        else if (data.get(position).getType().equals("3")){
            return GoodsListFragment.setUrl(++position, data.get(--position).getTitle(), store);
        }
        else if (position==3){
            return StoryListFragment.setUrl("专题分享", "XXX");
        }
        else if (position==4){
            return fragmenta;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

}
