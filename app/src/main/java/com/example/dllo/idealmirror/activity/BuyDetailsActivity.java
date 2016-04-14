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
    private AutoRelativeLayout setaddress;
    private ImageView close;
    private SimpleDraweeView mirimg;
    private TextView title, price, name, nameid, address, addressid, phone, changeAdd;
    private Button buyNow;
    private Address data;
    private int requsetcode = 1;
    private SetNewBuyUI setNewBuyUI;



    @Override
    protected int setContent() {
        return R.layout.activity_buydetails;
    }

    @Override
    protected void initView() {
        setNewBuyUI = new SetNewBuyUI();
        buyNow = bindView(R.id.buynow);
        setaddress = bindView(R.id.setaddress);
        close = bindView(R.id.addressclose);
        mirimg = bindView(R.id.mirrimg);
        title = bindView(R.id.mirrtitle);
        price = bindView(R.id.mirrprice);
        name = bindView(R.id.name);
        nameid = bindView(R.id.nameID);
        address = bindView(R.id.address);
        addressid = bindView(R.id.addressID);
        phone = bindView(R.id.phone);
        changeAdd = bindView(R.id.changeaddres);
    }

    @Override
    protected void initData() {
       // getOrder();//订单拉取
        Intent intent = getIntent();
        setaddress.setOnClickListener(this);
        close.setOnClickListener(this);
        Uri uri = Uri.parse(intent.getStringExtra("good_pic"));
        mirimg.setImageURI(uri);
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
        buyNow.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setaddress:
                Intent intent = new Intent(BuyDetailsActivity.this, AllAddressActivity.class);
                startActivityForResult(intent, requsetcode);
                break;
            case R.id.addressclose:
                finish();
                break;
            case R.id.buynow:
                //TODO

                break;
        }
    }

    /**
     * 获取默认地址
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
        LogUtils.d("00", body);
        if (data.getData().getPagination().getFirst_time()==""){
            name.setText(R.string.java_addpeoplemessage);
            nameid.setText("");
            address.setText("");
            addressid.setText("");
            phone.setText("");
            changeAdd.setText(R.string.java_addaddress);
        }
        else {
            for (int i = 0; i < data.getData().getList().size(); i++) {

                if (!data.getData().getList().get(i).getIf_moren().equals("2")) {
                    name.setText(R.string.receiver);
                    nameid.setText(data.getData().getList().get(i).getUsername());
                    address.setText(R.string.java_address);
                    addressid.setText(data.getData().getList().get(i).getAddr_info());
                    phone.setText(data.getData().getList().get(i).getCellphone());
                    changeAdd.setText(R.string.jave_changeaddress);
                }
                else if (data.getData().getList().get(i).getIf_moren().equals("2")||data.getData().getList().get(i).getIf_moren().equals("")){
                    name.setText(R.string.java_setpeoplemessage);
                    nameid.setText("");
                    address.setText("");
                    addressid.setText("");
                    phone.setText("");
                    changeAdd.setText(R.string.java_setaddress);
                }
            }
        }
    }

    @Override
    public void getFail() {
        name.setText(R.string.java_setpeoplemessage);
        nameid.setText("");
        address.setText("");
        addressid.setText("");
        phone.setText("");
        changeAdd.setText(R.string.java_setaddress);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {

            name.setText(R.string.receiver);
            nameid.setText(data.getStringExtra("nameid"));
            address.setText(R.string.java_address);
            addressid.setText(data.getStringExtra("addressid"));
            phone.setText(data.getStringExtra("phone"));
            changeAdd.setText(R.string.jave_changeaddress);
        }
    }
    class SetNewBuyUI extends BroadcastReceiver{

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
    private void getOrder(){

    }
}
