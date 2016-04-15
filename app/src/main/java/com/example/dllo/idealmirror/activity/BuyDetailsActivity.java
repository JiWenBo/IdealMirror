package com.example.dllo.idealmirror.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.bean.Address;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.example.dllo.idealmirror.tool.Url;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.zhy.autolayout.AutoRelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by dllo on 16/4/11.
 * 订单详情
 */
public class BuyDetailsActivity extends BaseActivity implements View.OnClickListener, Url, VolleyListener {
    private AutoRelativeLayout setAddress;
    private ImageView close;
    private SimpleDraweeView mirrorImg;
    private TextView title, price, name, nameId, address, addressId, phone, changeAdd;
    private Button buyBtn;
    private Address data;
    private int requsetCode = 1;
    private SetNewBuyUI setNewBuyUI;

    @Override
    protected int setContent() {
        return R.layout.activity_buy_detail;
    }

    @Override
    protected void initView() {
        setNewBuyUI = new SetNewBuyUI();
        buyBtn = bindView(R.id.buy_btn);
        setAddress = bindView(R.id.set_address);
        close = bindView(R.id.address_close);
        mirrorImg = bindView(R.id.mirror_sdv);
        title = bindView(R.id.mirror_title);
        price = bindView(R.id.mirror_price);
        name = bindView(R.id.name);
        nameId = bindView(R.id.name_id);
        address = bindView(R.id.address);
        addressId = bindView(R.id.address_id);
        phone = bindView(R.id.phone);
        changeAdd = bindView(R.id.change_address);
    }

    @Override
    protected void initData() {
        // getOrder();//订单拉取
        Intent intent = getIntent();
        setAddress.setOnClickListener(this);
        close.setOnClickListener(this);
        Uri uri = Uri.parse(intent.getStringExtra("good_pic"));
        mirrorImg.setImageURI(uri);
        title.setText(intent.getStringExtra("good_name"));
        price.setText("¥" + intent.getStringExtra("good_price"));
        NetHelper helper = new NetHelper();
        HashMap<String, String> data;
        data = new HashMap<>();
        data.put("token", TOKEN);
        data.put("device_type", "3");
        helper.getInformation(USER_ADDRESS_LIST, this, data);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SETNEWBUY);
        registerReceiver(setNewBuyUI, intentFilter);
        buyBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_address:
                Intent intent = new Intent(BuyDetailsActivity.this, AllAddressActivity.class);
                startActivityForResult(intent, requsetCode);
                break;
            case R.id.address_close:
                finish();
                break;
            case R.id.buy_btn:
                //TODO

                break;
        }
    }

    /**
     * 获取默认地址
     *
     * @param body
     */
    @Override
    public void getSuccess(String body) {
        try {

            SuccessData(body);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void SuccessData(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);
        Gson gson = new Gson();
        data = new Address();
        data = gson.fromJson(jsonObject.toString(), Address.class);

        if (data.getData().getPagination().getFirst_time() == "") {
            name.setText(R.string.java_addpeoplemessage);
            nameId.setText("");
            address.setText("");
            addressId.setText("");
            phone.setText("");
            changeAdd.setText(R.string.java_addaddress);
        } else {
            for (int i = 0; i < data.getData().getList().size(); i++) {

                if (data.getData().getList().get(i).getIf_moren().equals("1")) {
                    name.setText(R.string.receiver);
                    nameId.setText(data.getData().getList().get(i).getUsername());
                    address.setText(R.string.java_address);
                    addressId.setText(data.getData().getList().get(i).getAddr_info());
                    phone.setText(data.getData().getList().get(i).getCellphone());
                    changeAdd.setText(R.string.jave_changeaddress);
                    break;

                } else if (data.getData().getList().get(i).getIf_moren().equals("2") || data.getData().getList().get(i).getIf_moren().equals("")) {
                    name.setText(R.string.java_setpeoplemessage);
                    nameId.setText("");
                    address.setText("");
                    addressId.setText("");
                    phone.setText("");
                    changeAdd.setText(R.string.java_setaddress);

                }
            }
        }
    }

    @Override
    public void getFail() {
        name.setText(R.string.java_setpeoplemessage);
        nameId.setText("");
        address.setText("");
        addressId.setText("");
        phone.setText("");
        changeAdd.setText(R.string.java_setaddress);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {

            name.setText(R.string.receiver);
            nameId.setText(data.getStringExtra("nameId"));
            address.setText(R.string.java_address);
            addressId.setText(data.getStringExtra("addressId"));
            phone.setText(data.getStringExtra("phone"));
            changeAdd.setText(R.string.jave_changeaddress);
        }
    }

    class SetNewBuyUI extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            NetHelper helper = new NetHelper();
            HashMap<String, String> data;
            data = new HashMap<>();
            data.put("token", TOKEN);
            data.put("device_type", "3");
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
            }, data);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(setNewBuyUI);
    }

    //TODO
    private void getOrder() {

    }
}
