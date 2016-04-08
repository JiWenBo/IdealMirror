package com.example.dllo.idealmirror.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.bean.UserRegBean;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.example.dllo.idealmirror.tool.Url;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by LYH on 16/3/30.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, Url, VolleyListener {
    Button createBtn, loginBtn;
    ImageView close;
    EditText phoneEt, passwordEt;

    @Override
    protected int setContent() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        createBtn = bindView(R.id.create_number);
        close = bindView(R.id.close);
        phoneEt = bindView(R.id.login_phone_et);
        passwordEt = bindView(R.id.login_password_et);
        loginBtn = bindView(R.id.login_btn);
    }

    @Override
    protected void initData() {
        createBtn.setOnClickListener(this);
        close.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

        // 监听电话号码的EditText
        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (phoneEt.length() != 0 && passwordEt.length() != 0) {
                    // 当手机号和密码都输入时 loginBtn可点击状态
                    loginBtn.setBackgroundResource(R.drawable.login_selector);
                } else {
                    // 否则 不可点击
                    loginBtn.setBackgroundResource(R.drawable.button_use_false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 密码EditText的监听
        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (phoneEt.length() != 0 && passwordEt.length() != 0) {
                    // 电话号码和密码都输入时 可点击状态
                    loginBtn.setBackgroundResource(R.drawable.login_selector);
                } else if (phoneEt.length() == 0 || passwordEt.length() == 0) {
                    // 电话号码和密码只要有一个为空 就是不可点击的状态
                    loginBtn.setBackgroundResource(R.drawable.button_use_false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
            case R.id.login_btn:
                toLogin();
                break;
        }
    }

    private void toLogin() {
        NetHelper netHelper = new NetHelper();
        HashMap<String, String> param = new HashMap<>();
        param.put("phone_number", phoneEt.getText().toString());
        param.put("password", passwordEt.getText().toString());
        netHelper.getInformation(USER_LOGIN, this, param);
    }

    @Override
    public void getSuccess(String body) {
        LogUtils.d(body);
        try {
            JSONObject object = new JSONObject(body);
            if (object.has("result")) {
                String result = object.getString("result");
                switch (result) {
                    case "":
                        String msg = object.getString("msg");
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        break;
                    case "1":
                        Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                        // 登陆成功时  实例化一个Share的Preferences对象
                        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                        // 实例化SharedPreferences.Editor对象
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        // 保存数据
                        editor.putBoolean("isLogin", true);
                        // 提交当前数据
                        editor.commit();
                        finish();
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getFail() {
        Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
    }

}
