package com.example.dllo.idealmirror.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.bean.SinaUserBean;
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

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * Created by LYH on 16/3/30.
 * 登陆界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, Url, VolleyListener {
    Button createBtn, loginBtn;
    ImageView close, sina, weChat;
    EditText phoneEt, passwordEt;
    private Platform sinaPlatform;
    private SinaUserBean userBean;     // 微博用户的信息

    @Override
    protected int setContent() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        // 创建账号
        createBtn = bindView(R.id.create_number);
        createBtn.setOnClickListener(this);
        // 关闭页面
        close = bindView(R.id.close);
        close.setOnClickListener(this);
        // 电话号码
        phoneEt = bindView(R.id.login_phone_et);
        // 密码
        passwordEt = bindView(R.id.login_password_et);
        // 登陆
        loginBtn = bindView(R.id.login_btn);
        loginBtn.setOnClickListener(this);
        // 新浪登陆
        sina = bindView(R.id.login_sina);
        sina.setOnClickListener(this);
        // 微信登录
        weChat = bindView(R.id.login_wechat);
    }

    @Override
    protected void initData() {
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
            case R.id.login_sina:
                loginSina();
                break;
        }
    }

    /**
     * 新浪微博登陆
     */
    public void loginSina() {
        userBean = new SinaUserBean();
        ShareSDK.initSDK(this);
        sinaPlatform = ShareSDK.getPlatform(SinaWeibo.NAME);
        if (sinaPlatform.isAuthValid()) {
            sinaPlatform.removeAccount();
        }
        sinaPlatform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                userBean.setName(sinaPlatform.getDb().getUserName());
                userBean.setId(sinaPlatform.getDb().getUserId());
                userBean.setImg(sinaPlatform.getDb().getUserIcon());
                toBundling();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        sinaPlatform.SSOSetting(false);
        sinaPlatform.showUser(null);
    }

    /**
     * 绑定第三方账号
     */
    private void toBundling() {
        Log.d("LoginActivity", "bundling");
        NetHelper bundlingHelper = new NetHelper();
        HashMap<String, String> paramBund = new HashMap<>();
        paramBund.put("iswb_orwx", "1");
        paramBund.put("wb_name", userBean.getName());
        paramBund.put("wb_img", userBean.getImg());
        paramBund.put("wb_id", userBean.getId());
        Log.d("LoginActivity", userBean.getName());
        bundlingHelper.getInformation(USER_BUNDLING, new VolleyListener() {
            @Override
            public void getSuccess(String body) {
                // 请求成功
                LogUtils.d("sinaBody" + body);
                try {
                    JSONObject object = new JSONObject(body);
                    if (object.has("result")) {
                        String result = object.getString("result");
                        // 通过result 判断绑定的结果
                        switch (result) {
                            case "":
                                String msg = object.getString("msg");
                                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                                break;
                            case "1":
                                Toast.makeText(getBaseContext(), R.string.java_login_success, Toast.LENGTH_SHORT).show();
                                Gson gson = new Gson();
                                UserRegBean bean = new UserRegBean();
                                bean = gson.fromJson(object.toString(), UserRegBean.class);
                                // 确认已经登陆成功 返回到main
                                makeSureLogin();
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
                ToastUtils.showToast(getBaseContext(), String.valueOf(R.string.java_login_fail));

            }
        }, paramBund);
    }

    /**
     * 实现登陆
     */
    private void toLogin() {
        NetHelper netHelper = new NetHelper();
        HashMap<String, String> param = new HashMap<>();
        param.put("phone_number", phoneEt.getText().toString());
        param.put("password", passwordEt.getText().toString());
        netHelper.getInformation(USER_LOGIN, this, param);
    }

    /**
     * 登陆成功 解析获得的数据
     * @param body 网上拉取的数据
     */
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
                        ToastUtils.showToast(this, msg);
                        break;
                    case "1":
                        ToastUtils.showToast(this, String.valueOf(R.string.java_login_success));
                        /**
                         * 确认登陆
                         */
                        makeSureLogin();
                        finish();
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 确认已经登陆 存到数据库
     */
    private void makeSureLogin() {
        // 登陆成功时  实例化一个Share的Preferences对象
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        // 实例化SharedPreferences.Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // 保存数据
        editor.putBoolean("isLogin", true);
        // 提交当前数据
        editor.commit();
    }

    /**
     * 登陆失败
     */
    @Override
    public void getFail() {
        ToastUtils.showToast(this, String.valueOf(R.string.java_login_fail));
    }

}
