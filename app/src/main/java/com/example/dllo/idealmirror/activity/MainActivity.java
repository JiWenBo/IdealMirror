package com.example.dllo.idealmirror.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;

import android.support.v4.view.DirectionalViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.adapter.VerticalAdapter;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.bean.GoodList;
import com.example.dllo.idealmirror.fragment.MenuFragment;
import com.example.dllo.idealmirror.mirrordao.DaoSingleton;
import com.example.dllo.idealmirror.mirrordao.GoodListCache;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.Url;
import com.example.dllo.idealmirror.tool.VerticalViewPager;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Scroller;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements View.OnClickListener, VolleyListener, Url {
    private ImageView mirror;
    private GoodList datas;
    private List<GoodListCache> goodListCacheList;
    private GoodListCache goodListCache;
    private DirectionalViewPager viewPager;//
    private VerticalAdapter verticalAdapter;
    private TextView login, shopping;
    private LinearLayout menu;
    private FrameLayout frameLayout;
    private DaoSingleton daoSingleton;
    private long firstTime = 0;
    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager = bindView(R.id.viewpager);
        mirror = bindView(R.id.mirror_iv);
        mirror.setOnClickListener(this);
        login = bindView(R.id.login);
        login.setOnClickListener(this);
        shopping = bindView(R.id.main_shopping);
        shopping.setOnClickListener(this);
        menu = bindView(R.id.main_menu_layout);
        menu.setOnClickListener(this);
        frameLayout = bindView(R.id.main_menu_frame);
    }

    @Override
    protected void initData() {
        daoSingleton = DaoSingleton.getInstance();
        // 菜单数据请求
        NetHelper netHelper = new NetHelper();
        netHelper.getInformation(MENU_LIST, this, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在Activity可交互时 读取数据
        // 实例化一个SharedPreferences对象
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        // 获得value 第二个参数的默认值
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        // 如果登陆成功
        if (isLogin) {
            login.setVisibility(View.GONE);
            shopping.setVisibility(View.VISIBLE);
        } else {
            login.setVisibility(View.VISIBLE);
            shopping.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mirror_iv:
                // Mirror按钮动画
                playHeartbeatAnimation(mirror);
                break;
            case R.id.login:
                // 登陆
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
//                frameLayout.setVisibility(View.GONE);
                removeMenuFrame();
                break;
            case R.id.main_shopping:
                // 购物车
                // 按钮动画
                playHeartbeatAnimation(shopping);
                getDataFromFragment(4);
//                frameLayout.setVisibility(View.GONE);
                removeMenuFrame();
                break;
        }
    }

    /**
     * Mirror
     * 图片跳动 按钮模拟心脏跳动
     */
    private void playHeartbeatAnimation(final View view) {
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
                view.startAnimation(animationSet);
            }
        });

        // 实现心跳的View
        view.startAnimation(animationSet);

    }

    /**
     * 菜单请求成功 解析数据
     *
     * @param body 成功获得数据
     */
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

        goodListCacheList = new ArrayList<>();
        for (int i = 0; i < datas.getData().getList().size() - 1; i++) {
            goodListCache = new GoodListCache();
            goodListCache.setType(datas.getData().getList().get(i).getType());
            goodListCache.setButtomColor(datas.getData().getList().get(i).getButtomColor());
            goodListCache.setInfo_data(datas.getData().getList().get(i).getInfo_data());
            goodListCache.setTitle(datas.getData().getList().get(i).getTitle());
            goodListCache.setTopColor(datas.getData().getList().get(i).getTopColor());
            goodListCache.setStore(datas.getData().getList().get(i).getStore());
            goodListCacheList.add(goodListCache);
        }
        // 清空数据库
        daoSingleton.deleteGoodListAll();
        // 将数据加入到数据库中
        daoSingleton.insertGoodList(goodListCacheList);
        verticalAdapter = new VerticalAdapter(getSupportFragmentManager(), goodListCacheList);
        viewPager.setAdapter(verticalAdapter);
        viewPager.setOrientation(DirectionalViewPager.VERTICAL);

    }

    /**
     * 请求失败时 从数据库中获得数据
     */
    @Override
    public void getFail() {
        goodListCacheList = daoSingleton.queryGoodList();
        verticalAdapter = new VerticalAdapter(getSupportFragmentManager(), goodListCacheList);
        viewPager.setAdapter(verticalAdapter);
        viewPager.setOrientation(DirectionalViewPager.VERTICAL);
    }

    /**
     * 暴露一个方法 获得位置
     *
     * @param position fragment的位置
     */
    public void getDataFromFragment(int position) {
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

    /**
     * 设置菜单的帧布局
     *
     * @param context
     * @param store   区分数据的类型
     */
    public void setMenuFrame(Context context, String store) {
        frameLayout.setVisibility(View.VISIBLE);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.main_menu_frame,
                new MenuFragment(store, context, goodListCacheList)).commit();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.menu_fragment);
        frameLayout.setAnimation(animation);
    }

    public void removeMenuFrame() {
        frameLayout.setVisibility(View.GONE);
    }

    /**
     * 系统返回键的监听事件
     * 如果超过两秒则返回true表示表示拦截事件,小于2秒的时候回执行
     * @param keyCode The value in event.getKeyCode(),什么按键
     * @param event   点击事件
     * @return
     */

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime =System.currentTimeMillis();
                Log.e("x", secondTime + "S");
                if (secondTime-firstTime>2000){
                    Toast.makeText(MainActivity.this, "确定真的要离开我了吗~", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                }
                else {
                    System.exit(0);//退出程序
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }


}
