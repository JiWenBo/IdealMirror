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
import android.widget.ImageView;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.adapter.VerticalAdapter;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.bean.GoodList;
import com.example.dllo.idealmirror.fragment.PageFragment;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity implements View.OnClickListener,VolleyListener{
    private DirectionalViewPager viewPager;
    private VerticalAdapter verticalAdapter;
    private List<Fragment> data;
    private ImageView mirror;
    private Bundle bundle;
    private GoodList datas;
    private TextView longin;



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
        netHelper.getInformation("http://api101.test.mirroreye.cn/index.php/index/menu_list", this, null);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mirrorpic: //Mirror按钮动画
                playHeartbeatAnimation();
                break;

            case R.id.login:     //登陆
                Intent intent = new Intent(this,LoginActivity.class);
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
          datas =  gson.fromJson(jsonObject.toString(), GoodList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
}
