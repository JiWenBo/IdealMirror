package com.example.dllo.idealmirror.activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.DirectionalViewPager;
import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.adapter.ShareAdapter;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.base.BaseFragment;
import com.example.dllo.idealmirror.bean.StoryListBean;
import com.example.dllo.idealmirror.fragment.ShareFragmnet;
import com.example.dllo.idealmirror.fragment.StoryListFragment;
import com.example.dllo.idealmirror.net.ImageLoaderCache;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.facebook.drawee.view.SimpleDraweeView;

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
    private StoryListBean beans;
    private static  int nowposition;
    private SimpleDraweeView draweeView;
    @Override
    protected int setContent() {
        return R.layout.activity_share;

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        nowposition = Integer.parseInt(intent.getStringExtra("posi"));
        viewPagers = bindView(R.id.viewpager);
       draweeView = (SimpleDraweeView) findViewById(R.id.img_back);
    }
    @Override
    protected void initData() {
        /**
         * 获得实体类
         */
        beans = new StoryListBean();
        beans =  StoryListFragment.bean();
        /*******从activity向fragment传值的时候*最别用eventbus,使用bundle*******/
        List<Fragment> data = new ArrayList<>();
        for (int i=0;i<beans.getData().getList().get(1).getStory_data().getImg_array().size();i++){
            ShareFragmnet shareFragmnet = new ShareFragmnet();
            LogUtils.d("3333",beans.getData().getList().get(1).getStory_data().getImg_array().size()+"x");
            StoryListBean.DataEntity.ListEntity.StoryDataEntity.TextArrayEntity listBean = beans.getData().getList().get(nowposition).getStory_data().getText_array().get(i);
            Bundle bundle = new Bundle();
            /******/
            bundle.putParcelable("shareBean", listBean);
            shareFragmnet.setArguments(bundle);
            data.add(shareFragmnet);
        }
        adapter = new ShareAdapter(getSupportFragmentManager(), data);
        /***************/

        viewPagers.setAdapter(adapter);
        viewPagers.setOrientation(DirectionalViewPager.VERTICAL);

        /* 给一个初始的值,以免第一次进入时没有数据 */
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
        LogUtils.d("ssss",nowposition+"s");
        String url = beans.getData().getList().get(nowposition).getStory_data().getImg_array().get(position);
        draweeView.setImageURI(Uri.parse(url));
    }
}
