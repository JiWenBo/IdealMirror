package com.example.dllo.idealmirror.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.DirectionalViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.adapter.VerticalAdapter;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.bean.GoodList;
import com.example.dllo.idealmirror.mirrordao.DaoMaster;
import com.example.dllo.idealmirror.mirrordao.DaoSession;
import com.example.dllo.idealmirror.mirrordao.DaoSingleton;
import com.example.dllo.idealmirror.mirrordao.GoodListCache;
import com.example.dllo.idealmirror.mirrordao.GoodListCacheDao;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.PopWindow;
import com.example.dllo.idealmirror.tool.Url;
import com.google.gson.Gson;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Scroller;


public class MainActivity extends BaseActivity implements View.OnClickListener, VolleyListener, Url {

    private DirectionalViewPager viewPager;
    private VerticalAdapter verticalAdapter;
    private ImageView mirror;
    private GoodList datas;
    private TextView longin;
    private DaoSession daoSession;
    private DaoMaster daoMaster;
    private SQLiteDatabase db;
    private GoodListCacheDao goodListCacheDao;
    private List<GoodListCache> goodListCachelist;
    private GoodListCache goodListCache;

    public MainActivity() {
    }


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

        /*第一步清空数据库*/
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "allmirror.db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        goodListCacheDao = daoSession.getGoodListCacheDao();
        goodListCacheDao.deleteAll();
        /*将数据重新添加到数据库*/
        goodListCacheDao = DaoSingleton.getInstance().getGoodListCacheDao();
        goodListCachelist = new ArrayList<>();
        for (int i = 0; i < datas.getData().getList().size(); i++) {
            goodListCache = new GoodListCache();
            goodListCache.setType(datas.getData().getList().get(i).getType());
            goodListCache.setButtomColor(datas.getData().getList().get(i).getButtomColor());
            goodListCache.setInfo_data(datas.getData().getList().get(i).getInfo_data());
            goodListCache.setTitle(datas.getData().getList().get(i).getTitle());
            goodListCache.setTopColor(datas.getData().getList().get(i).getTopColor());
            goodListCache.setStore(datas.getData().getList().get(i).getStore());
            goodListCacheDao.insert(goodListCache);
            goodListCachelist.add(goodListCache);
        }
        PopWindow popWindow = new PopWindow(this);
        popWindow.initDataPop(goodListCachelist);
        goodListCachelist = goodListCacheDao.queryBuilder().list();
        verticalAdapter = new VerticalAdapter(getSupportFragmentManager(), goodListCachelist);
        viewPager.setAdapter(verticalAdapter);
        viewPager.setOrientation(DirectionalViewPager.VERTICAL);

    }

    @Override
    public void getFail() {

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "allmirror.db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        goodListCacheDao = daoSession.getGoodListCacheDao();
        goodListCachelist = goodListCacheDao.queryBuilder().list();
        PopWindow popWindow = new PopWindow(this);
        popWindow.initDataPop(goodListCachelist);
        verticalAdapter = new VerticalAdapter(getSupportFragmentManager(), goodListCachelist);
        viewPager.setAdapter(verticalAdapter);
        viewPager.setOrientation(DirectionalViewPager.VERTICAL);


    }

    //暴露方法 得到position
    public void getDatafromFragment(int position) {
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
