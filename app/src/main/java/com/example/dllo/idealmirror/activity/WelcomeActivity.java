package com.example.dllo.idealmirror.activity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.Url;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by dllo on 16/4/15.
 */
public class WelcomeActivity extends BaseActivity implements Url, VolleyListener {
    private SimpleDraweeView welcome;
    private Handler handler;

    @Override
    protected int setContent() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        welcome = bindView(R.id.welcomeimg);

    }

    @Override
    protected void initData() {
        NetHelper helper = new NetHelper();

        helper.getInformation(INDEX_STARTED_IMG, this, null);
        new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(5000);
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 1) {
                    finish();
                }


                return false;
            }
        });

    }

    @Override
    public void getSuccess(String body) {

        try {
            JSONObject jsonObject = new JSONObject(body);
            SharedPreferences sharedPreferences = this.getSharedPreferences("welimgs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.putString("welimg", jsonObject.getString("img"));//存储路径
            editor.commit();
            if (jsonObject.has("img")) {
                welcome.setImageURI(Uri.parse(jsonObject.getString("img")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getFail() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("welimgs", MODE_PRIVATE);
        welcome.setImageURI(Uri.parse(sharedPreferences.getString("welimg", "").toString()));

    }
}
