package com.example.dllo.idealmirror.activity;
import android.support.v4.view.DirectionalViewPager;
import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.adapter.ShareAdapter;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.bean.StoryListBean;
import com.example.dllo.idealmirror.fragment.ShareFragmnet;
import com.example.dllo.idealmirror.fragment.StoryListFragment;
import com.example.dllo.idealmirror.net.ImageLoaderCache;
import com.example.dllo.idealmirror.net.NetHelper;
import java.util.ArrayList;
import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;

import de.greenrobot.event.EventBus;

/**
 * Created by dllo on 16/4/5.
 */
public class ShareActivity extends BaseActivity {
    private DirectionalViewPager viewPagers;
    private ShareAdapter adapter;
    private static ImageView imageViewl;
    private StoryListBean beans;

    @Override
    protected int setContent() {
        return R.layout.activity_share;

    }

    @Override
    protected void initView() {
        viewPagers = bindView(R.id.viewpager);
        imageViewl = bindView(R.id.img_back);

    }

    @Override
    protected void initData() {
        /**
         * 获得实体类
         */
        beans = new StoryListBean();
        beans =  StoryListFragment.bean();
        adapter = new ShareAdapter(getSupportFragmentManager(),beans);
        viewPagers.setAdapter(adapter);
        viewPagers.setOrientation(DirectionalViewPager.VERTICAL);
        /*
        * 给一个初始的值,以免第一次进入时没有数据*/
        setImgandtext(0);
        viewPagers.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setImgandtext(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void  setImgandtext(int position){
        NetHelper netHelper = new NetHelper();
        ImageLoader imageLoader = netHelper.getImageLoader();
        imageLoader.get(beans.getData().getList().get(1).getStory_data().getImg_array().get(position),
                ImageLoader.getImageListener(imageViewl, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
        ShareFragmnet shareFragmnet = new ShareFragmnet();
        shareFragmnet.ShareFragmnets(beans.getData().getList().get(1).getStory_data().getText_array().get(position).getSmallTitle(),
                beans.getData().getList().get(1).getStory_data().getText_array().get(position).getTitle(),
                beans.getData().getList().get(1).getStory_data().getText_array().get(position).getSubTitle());

    }
}
