package com.example.dllo.idealmirror.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.bean.UserRegBean;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.example.dllo.idealmirror.tool.ToastUtils;
import com.example.dllo.idealmirror.tool.Url;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nan on 16/3/30.
 */

public class CreateActivity extends BaseActivity implements View.OnClickListener, Url {
    ImageView closeIv;
    EditText phoneEt, codeEt, passwordEt;
    Button codeBtn, accountBtn;
    private UserRegBean bean;


    @Override
    protected int setContent() {
        return R.layout.activity_create_account;
    }

    @Override
    protected void initView() {
        closeIv = bindView(R.id.create_close);
        phoneEt = bindView(R.id.create_phone_et);
        codeEt = bindView(R.id.create_verifi_et);
        passwordEt = bindView(R.id.create_password_et);
        codeBtn = bindView(R.id.create_verifi_btn);
        accountBtn = bindView(R.id.create_account_btn);
    }

    @Override
    protected void initData() {
        closeIv.setOnClickListener(this);
        codeBtn.setOnClickListener(this);
        accountBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_close:
                finish();
                break;
            case R.id.create_verifi_btn:
                // 发送验证码
                sendVerification();
                break;
            case R.id.create_account_btn:
                // 创建账号
                createAccount();
                break;
        }
    }

    private void createAccount() {
        bean = new UserRegBean();
        NetHelper helper = new NetHelper();
        HashMap<String, String> paramCreate = new HashMap<>();
        paramCreate.put("phone_number", phoneEt.getText().toString());
        paramCreate.put("number", codeEt.getText().toString());
        paramCreate.put("password", passwordEt.getText().toString());
        helper.getInformation(USER_REG, new VolleyListener() {
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
                                Toast.makeText(CreateActivity.this, msg, Toast.LENGTH_SHORT).show();
                                break;
                            case "0":
                                Toast.makeText(CreateActivity.this, "请确认填写信息", Toast.LENGTH_SHORT).show();
                                break;
                            case "1":
                                Gson gson = new Gson();
                                bean = gson.fromJson(object.toString(), UserRegBean.class);
                                Toast.makeText(CreateActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
                                Bundle bundle = new Bundle();
                                bundle.putString("phone", phoneEt.getText().toString());
                                Intent intent = new Intent(CreateActivity.this, LoginActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
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
                Toast.makeText(CreateActivity.this, "创建失败", Toast.LENGTH_SHORT).show();
            }
        }, paramCreate);
    }

    private void sendVerification() {
        NetHelper netHelper = new NetHelper();
        HashMap<String, String> paramVer = new HashMap<>();
        paramVer.put("phone number", phoneEt.getText().toString());
        netHelper.getInformation(USER_SEND_CODE, new VolleyListener() {
            @Override
            public void getSuccess(String body) {
                LogUtils.d(body);
            }

            @Override
            public void getFail() {

            }
        }, paramVer);
    }

}
