package com.example.dllo.idealmirror.activity;

import android.view.View;
import android.widget.ImageView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.base.BaseActivity;

/**
 * Created by nan on 16/3/30.
 */
public class CreateActivity extends BaseActivity implements View.OnClickListener{
    private ImageView creatclose;

    @Override
    protected int setContent() {
        return R.layout.activity_create_account;
    }

    @Override
    protected void initView() {
       creatclose = bindView(R.id.creat_close);
    }

    @Override
    protected void initData() {
        creatclose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.creat_close:
                finish();
                break;
        }
    }
}
