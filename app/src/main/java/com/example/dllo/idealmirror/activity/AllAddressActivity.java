package com.example.dllo.idealmirror.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.dllo.idealmirror.tool.ToastUtils;
import com.example.dllo.idealmirror.tool.Url;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by LYH on 16/3/31.
 * 所有地址的界面
 */
public class AllAddressActivity extends BaseActivity implements Url, VolleyListener, AllAddressRcAdapter.AddressRcListener,
        AllAddressRcAdapter.RecyclerItemListener {

    private RecyclerView recyclerView;
    private AllAddressRcAdapter allAddressRcAdapter;
    private static Address data;
    private Button addBtn;
    private ImageView close;
    private int request = 1;
    HashMap<String, String> paramAll;
    HashMap<String, String> paramDel;
    private int result = 1;
    private GetNewUI getNewUI;
    private static String userToken;


    @Override
    protected int setContent() {
        return R.layout.activity_all_address;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        userToken = intent.getStringExtra("token");
        recyclerView = (RecyclerView) findViewById(R.id.all_address_recycler);
        NetHelper helper = new NetHelper();

        // 获取我的收货地址列表
        paramAll = new HashMap<>();
        paramAll.put("token", userToken);
        paramAll.put("device_type", "3");
        helper.getInformation(USER_ADDRESS_LIST, this, paramAll);
        addBtn = bindView(R.id.add_address_btn);
        close = bindView(R.id.add_close);

    }

    @Override
    protected void initData() {
        //内部广播注册
        getNewUI = new GetNewUI();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROIDCAST);
        registerReceiver(getNewUI, intentFilter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllAddressActivity.this, AddAddressActivity.class);
                intent.putExtra("name", "");
                intent.putExtra("number", "");
                intent.putExtra("address", "");
                intent.putExtra("nameTitle", getString(R.string.java_alladdress_receiver_name));
                intent.putExtra("numTitle", getString(R.string.java_alladdress_contact_phone));
                intent.putExtra("addTitle", getString(R.string.java_alladdress_receiver_address));
                intent.putExtra("title", getString(R.string.java_alladdress_add_address));
                intent.putExtra("btnText", getString(R.string.java_alladdress_commit_address));
                intent.putExtra("token", userToken);
                startActivityForResult(intent, request);

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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 成功获取数据后 解析数据
     *
     * @param body 获取的数据
     * @throws JSONException 抛出异常
     */
    private void SuccessData(String body) throws JSONException {
        data = new Address();
        JSONObject jsonObject = new JSONObject(body);
        Gson gson = new Gson();
        data = gson.fromJson(jsonObject.toString(), Address.class);
        allAddressRcAdapter = new AllAddressRcAdapter(data, AllAddressActivity.this, userToken);
        allAddressRcAdapter.getListener(AllAddressActivity.this);
        allAddressRcAdapter.RecyclerItem(AllAddressActivity.this);
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
        paramDel = new HashMap<>();
        paramDel.put("addr_id", addr_id);
        paramDel.put("token", userToken);

        helper.getInformation(USER_DEL_ADDRESS, new VolleyListener() {
            @Override
            public void getSuccess(String body) {
                // 获取我的收货地址列表
                NetHelper helper = new NetHelper();
                paramAll = new HashMap<>();
                paramAll.put("token", userToken);
                paramAll.put("device_type", "3");
                helper.getInformation(USER_ADDRESS_LIST, new VolleyListener() {
                    @Override
                    public void getSuccess(String body) {
                        try {
                            SuccessData(body);
                            LogUtils.d(body);
                            /*数据删除成功之后也需要刷新BuyActivity的UI*/
                            Intent intent = new Intent(SETNEWBUY);
                            sendBroadcast(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void getFail() {

                    }
                }, paramAll);

            }

            @Override
            public void getFail() {

            }
        }, paramDel);
    }

    @Override

    public void getItemData(String addr, final int position) {
        NetHelper helper = new NetHelper();
        final HashMap<String, String> datas;
        datas = new HashMap<>();
        datas.put("token", userToken);
        datas.put("addr_id", addr);

        helper.getInformation(USER_MR_ADDRESS, new VolleyListener() {
            @Override
            public void getSuccess(String body) {
                ToastUtils.showToast(AllAddressActivity.this, getString(R.string.moren));
                Intent intents = new Intent(AllAddressActivity.this, BuyDetailsActivity.class);
                intents.putExtra("nameId", data.getData().getList().get(position).getUsername());
                intents.putExtra("addressId", data.getData().getList().get(position).getAddr_info());
                intents.putExtra("phone", data.getData().getList().get(position).getCellphone());
                setResult(result, intents);
                finish();

            }

            @Override
            public void getFail() {

            }
        }, datas);
    }

    /**
     * 动态广播用于刷新UI
     */
    public class GetNewUI extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            /*重新拉取数据刷新*/
            NetHelper helper = new NetHelper();
            paramAll = new HashMap<>();
            paramAll.put("token", userToken);
            paramAll.put("device_type", "3");
            helper.getInformation(USER_ADDRESS_LIST, new VolleyListener() {
                @Override
                public void getSuccess(String body) {
                    try {
                        SuccessData(body);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void getFail() {

                }
            }, paramAll);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(getNewUI);
    }
}
