package com.example.dllo.idealmirror.activity;

import android.content.Intent;
import android.support.v4.view.DirectionalViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;


import android.content.Context;
import android.support.v4.view.DirectionalViewPager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.ImageView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.adapter.VerticalAdapter;
import com.example.dllo.idealmirror.base.BaseActivity;

import com.example.dllo.idealmirror.bean.GoodList;

import com.example.dllo.idealmirror.bean.PopupListBean;

import com.example.dllo.idealmirror.fragment.PageFragment;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.LogUtils;

import com.example.dllo.idealmirror.tool.PopWindow;
import com.example.dllo.idealmirror.tool.Url;
import com.google.gson.Gson;


import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;

import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Scroller;

import de.greenrobot.event.EventBus;


public class MainActivity extends BaseActivity implements View.OnClickListener, VolleyListener, Url {
    private DirectionalViewPager viewPager;
    private VerticalAdapter verticalAdapter;
    private List<Fragment> data;
    private ImageView mirror;
    private Bundle bundle;
    private GoodList datas;
    private TextView longin;
    private PopupListBean bean;
    private static int position;

    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager = bindView(R.id.viewpager);
        mirror = bindView(R.id.mirrorpic);
        mirror.setOnClickListener(this);
        longin = bindView(R.id.login);
        longin.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        data = new ArrayList<>();
        NetHelper netHelper = new NetHelper();
        netHelper.getInformation(MENU_LIST, this, null);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mirrorpic: //Mirror按钮动画
                playHeartbeatAnimation();
                break;

            case R.id.login:     //登陆
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * Mirror
     * 图片跳动
     */
    // 按钮模拟心脏跳动
    private void playHeartbeatAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        // Animation.RELATIVE_TO_SELF 变化中心角
        animationSet.addAnimation(new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f));


        animationSet.setDuration(200);
        animationSet.setInterpolator(new AccelerateInterpolator());
        //结尾停在最后一针
        animationSet.setFillAfter(true);
        //对动画进行监听
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            //开始时候怎么样
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            //结束时候怎么样
            @Override
            public void onAnimationEnd(Animation animation) {
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(new ScaleAnimation(1.2f, 1.0f, 1.2f,
                        1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f));
                animationSet.setDuration(200);
                animationSet.setInterpolator(new DecelerateInterpolator());
                animationSet.setFillAfter(false);
                // 实现心跳的View
                mirror.startAnimation(animationSet);
            }
        });

        // 实现心跳的View
        mirror.startAnimation(animationSet);

    }

    @Override
    public void getSuccess(String body) {
        datas = new GoodList();
        try {
            JSONObject jsonObject = new JSONObject(body);
            Gson gson = new Gson();
            datas = gson.fromJson(jsonObject.toString(), GoodList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PopWindow popWindow = new PopWindow(this);
        popWindow.initDataPop(datas);

        for (int i = 0; i < datas.getData().getList().size(); i++) {
            bundle = new Bundle();
            PageFragment pageFragment = new PageFragment();
            String categorys = datas.getData().getList().get(i).getInfo_data();
            LogUtils.d(categorys);
            bundle.putString("cate", categorys);
            pageFragment.setArguments(bundle);
            data.add(pageFragment);
        }

        verticalAdapter = new VerticalAdapter(getSupportFragmentManager(), data);
        viewPager.setAdapter(verticalAdapter);
        viewPager.setOrientation(DirectionalViewPager.VERTICAL);
    }

    @Override
    public void getFail() {

    }

    //暴露方法 得到position
    public void getDatafromFragment(int position) {
        Log.d("MainActivity", "从fragment历来" + position);
        this.position = position;
        //这个是设置viewPager切换过度时间的类
        ViewPagerScroller scroller = new ViewPagerScroller(this);
        scroller.setScrollDuration(80);
        scroller.initViewPagerScroll(viewPager);
        //这个是设置切换过渡时间为0毫秒
        viewPager.setCurrentItem(position);
    }


    /**
     * 这个类是给ViewPager滚动速度的设置
     * 这个类封装了滚动操作。滚动的持续时间可以通过构造函数传递，并且可以指定滚动动作的持续的最长时间。
     * 经过这段时间，滚动会自动定位到最终位置，
     * 并且通过computeScrollOffset()会得到的返回值为false，表明滚动动作已经结束。
     */
    public class ViewPagerScroller extends Scroller {
        private int mScrollDuration = 2000;             // 滑动速度

        /**
         * http://mengsina.iteye.com/blog/1123339 这个对这个类解释很详细
         * 设置速度速度
         *
         * @param duration
         */
        public void setScrollDuration(int duration) {
            this.mScrollDuration = duration;
        }

        public ViewPagerScroller(Context context) {
            super(context);
        }


        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }


        public void initViewPagerScroll(ViewPager viewPager) {
            try {
                //就是存储一个类的属性值
                //通过这个方法找到private的方法
                Field mScroller = ViewPager.class.getDeclaredField("mScroller");
                //试图设置accessible标志。其设置为true防止IllegalAccessExceptions。
                mScroller.setAccessible(true);
                mScroller.set(viewPager, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
