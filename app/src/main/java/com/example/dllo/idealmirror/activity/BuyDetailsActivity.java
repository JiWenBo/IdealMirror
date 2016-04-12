package com.example.dllo.idealmirror.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * Created by dllo on 16/4/11.
 */
public class BuyDetailsActivity extends BaseActivity implements View.OnClickListener{
    private AutoRelativeLayout setaddress;
    private ImageView close;
    @Override
    protected int setContent() {
        return R.layout.activity_buydetails;
    }

    @Override
    protected void initView() {
      setaddress = bindView(R.id.setaddress);
        close = bindView(R.id.addressclose);
    }

    @Override
    protected void initData() {
      setaddress.setOnClickListener(this);
        close.setOnClickListener(this);
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
