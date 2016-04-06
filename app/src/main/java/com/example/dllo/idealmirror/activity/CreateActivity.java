package com.example.dllo.idealmirror.activity;

import android.view.View;
import android.widget.ImageView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.base.BaseActivity;

/**
 * Created by nan on 16/3/30.
 */
public class CreateActivity extends BaseActivity implements View.OnClickListener{
    private ImageView close;

    @Override
    protected int setContent() {
        return R.layout.activity_create_account;
    }

    @Override
    protected void initView() {
       close = bindView(R.id.create_close);
    }

    @Override
    protected void initData() {
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_close:
                finish();
                break;
        }
    }
}
