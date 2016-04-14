package com.example.dllo.idealmirror.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.AutoRelativeLayout;

import java.net.URL;

/**
 * Created by dllo on 16/4/11.
 * 订单详情
 */
public class BuyDetailsActivity extends BaseActivity implements View.OnClickListener{
    private AutoRelativeLayout setaddress;
    private ImageView close;
    private SimpleDraweeView mirimg;
    private TextView title,price;
    @Override
    protected int setContent() {
        return R.layout.activity_buydetails;
    }

    @Override
    protected void initView() {
        setaddress = bindView(R.id.setaddress);
        close = bindView(R.id.addressclose);
        mirimg = bindView(R.id.mirrimg);
        title = bindView(R.id.mirrtitle);
        price = bindView(R.id.mirrprice);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        setaddress.setOnClickListener(this);
        close.setOnClickListener(this);
        Uri uri = Uri.parse(intent.getStringExtra("good_pic"));
        mirimg.setImageURI(uri);
        title.setText(intent.getStringExtra("good_name"));
        price.setText("¥" +intent.getStringExtra("good_price"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setaddress:
                Intent intent = new Intent(BuyDetailsActivity.this,AllAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.close:
                finish();
                break;
        }
    }
}
