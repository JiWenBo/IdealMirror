package com.example.dllo.idealmirror.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.base.BaseActivity;

/**
 * Created by LYH on 16/3/30.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button createBtn;
    private ImageView close;
    EditText phoneEt;

    @Override
    protected int setContent() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        createBtn = bindView(R.id.create_number);
        close = bindView(R.id.close);

    }

    @Override
    protected void initData() {
        createBtn.setOnClickListener(this);
        close.setOnClickListener(this);
        // 注册成功后, 获得手机号
        Intent intentPhone = getIntent();
        Bundle bundle = intentPhone.getExtras();
        if (bundle != null) {
            phoneEt.setText(bundle.getString("phone"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_number:
                Intent intent = new Intent(this, CreateActivity.class);
                startActivity(intent);
                break;
            case R.id.close:
                finish();
                break;
        }
    }
}
