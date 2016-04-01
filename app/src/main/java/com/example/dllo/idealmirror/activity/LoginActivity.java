package com.example.dllo.idealmirror.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.base.BaseActivity;

/**
 * Created by LYH on 16/3/30.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private Button creatnum;
    private ImageView close;

    @Override
    protected int setContent() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        creatnum = bindView(R.id.creat_number);
        close = bindView(R.id.close);

    }

    @Override
    protected void initData() {
        creatnum.setOnClickListener(this);
        close.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.creat_number:
                Intent intent = new Intent(this,CreateActivity.class);
                startActivity(intent);
                break;
            case R.id.close:
                finish();
                break;
        }
    }
}
