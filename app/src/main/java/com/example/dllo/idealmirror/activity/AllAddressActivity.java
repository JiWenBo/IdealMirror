package com.example.dllo.idealmirror.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.adapter.AllAddressRcAdapter;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.bean.Address;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.example.dllo.idealmirror.tool.Url;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LYH on 16/3/31.
 * 所有地址的界面
 */
public class AllAddressActivity extends BaseActivity implements Url, VolleyListener, AllAddressRcAdapter.ReclcleListrner ,AllAddressRcAdapter.RecyclerItemLinstener{

    private RecyclerView recyclerView;
    private AllAddressRcAdapter allAddressRcAdapter;
    private Address data;
    private Button add;
    private ImageView close;
    HashMap<String, String> parma;
    HashMap<String, String> parm;


    @Override
    protected int setContent() {
        return R.layout.activity_all_address;
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.allAddress_recyclerview);
        NetHelper helper = new NetHelper();
        // 获取我的收货地址列表
        parma = new HashMap<>();
        parma.put("token", "1bb26b25b32ccb55890611b8fb9d552f");
        parma.put("device_type", "3");
        helper.getInformation(USER_ADDRESS_LIST, this, parma);
        add = bindView(R.id.addaddress);
        close = bindView(R.id.add_close);

    }

    @Override
    protected void initData() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllAddressActivity.this, AddAddressActivity.class);
                intent.putExtra("name", "");
                intent.putExtra("number", "");
                intent.putExtra("address", "");
                intent.putExtra("nametitie", getString(R.string.java_alladdress_receiver_name));
                intent.putExtra("numtitle", getString(R.string.java_alladdress_contact_phone));
                intent.putExtra("addtitle", getString(R.string.java_alladdress_receiver_address));
                intent.putExtra("title", getString(R.string.java_alladdress_add_address));
                intent.putExtra("btntext", getString(R.string.java_alladdress_commit_address));
                startActivity(intent);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void getSuccess(String body) {
        try {
            SuccessData(body);
            LogUtils.d(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /**
     * 成功获取数据后 解析数据
     * @param body 获取的数据
     * @throws JSONException 抛出异常
     */
    private void SuccessData(String body) throws JSONException {
        data = new Address();
        JSONObject jsonObject = new JSONObject(body);
        Gson gson = new Gson();
        data = gson.fromJson(jsonObject.toString(), Address.class);
        allAddressRcAdapter = new AllAddressRcAdapter(data, AllAddressActivity.this);
        allAddressRcAdapter.getListener(AllAddressActivity.this);
        recyclerView.setAdapter(allAddressRcAdapter);
        GridLayoutManager gm = new GridLayoutManager(this, 1);
        gm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gm);
    }

    @Override
    public void getFail() {

    }

    @Override
    public void getData(String addr_id, int position) {
        // 删除收货地址
        NetHelper helper = new NetHelper();
        parm = new HashMap<>();
        parm.put("addr_id", addr_id);
        LogUtils.d(addr_id);
        parm.put("token", TOKEN);
        helper.getInformation(USER_DEL_ADDRESS, new VolleyListener() {
            @Override
            public void getSuccess(String body) {
                // 获取我的收货地址列表
                NetHelper helper = new NetHelper();
                parma = new HashMap<>();
                parma.put("token", TOKEN);
                parma.put("device_type", "3");
                helper.getInformation(USER_ADDRESS_LIST, new VolleyListener() {
                    @Override
                    public void getSuccess(String body) {
                        try {
                            SuccessData(body);
                            LogUtils.d(body);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void getFail() {

                    }
                }, parma);

            }

            @Override
            public void getFail() {

            }
        }, parm);
    }

    @Override
    public void getItemData(String addr) {
        // 设置默认收货地址
        NetHelper helper = new NetHelper();
        HashMap<String,String> data;
        data = new HashMap<>();
        data.put("token",TOKEN);
        data.put("addr_id",addr);
        helper.getInformation(USER_MR_ADDRESS, new VolleyListener() {
            @Override
            public void getSuccess(String body) {
                LogUtils.e("item",body);
            }

            @Override
            public void getFail() {

            }
        }, data);
    }
}
