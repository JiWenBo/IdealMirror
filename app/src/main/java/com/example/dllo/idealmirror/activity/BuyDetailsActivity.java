package com.example.dllo.idealmirror.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
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

import de.greenrobot.event.EventBus;

/**
 * Created by dllo on 16/4/11.
 */
public class BuyDetailsActivity extends BaseActivity implements View.OnClickListener, Url, VolleyListener {
    private AutoRelativeLayout setaddress;
    private ImageView close;
    private SimpleDraweeView mirimg;
    private TextView title, price, name, nameid, address, addressid, phone, changeadd;
    private Address data;
    private int requsetcode = 1;



    @Override
    protected int setContent() {

        return R.layout.activity_buydetails;
    }

    @Override
    protected void initView() {
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
        changeadd = bindView(R.id.changeaddres);
    }

    @Override
    protected void initData() {
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


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setaddress:
                Intent intent = new Intent(BuyDetailsActivity.this, AllAddressActivity.class);

                startActivityForResult(intent, requsetcode);
                break;
            case R.id.close:
                finish();
                break;
        }
    }


    @Override
    public void getSuccess(String body) {
        try {
            JSONObject jsonObject = new JSONObject(body);
            Gson gson = new Gson();
            data = new Address();
            data = gson.fromJson(jsonObject.toString(), Address.class);
            for (int i = 0; i < data.getData().getList().size(); i++) {
                if (!data.getData().getList().get(i).getIf_moren().equals("2")) {
                    name.setText("收件人: ");
                    nameid.setText(data.getData().getList().get(i).getUsername());
                    address.setText("地址: ");
                    addressid.setText(data.getData().getList().get(i).getAddr_info());
                    phone.setText(data.getData().getList().get(i).getCellphone());
                    changeadd.setText("更改地址");
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getFail() {
        name.setText("请填写收件人信息");
        changeadd.setText("添加地址");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.e("one", "tone");
        if (requestCode == 1 && resultCode == 1) {
            LogUtils.e("one", "one");
            name.setText("收件人: ");
            nameid.setText(data.getStringExtra("nameid"));
            address.setText("地址: ");
            addressid.setText(data.getStringExtra("addressid"));
            phone.setText(data.getStringExtra("phone"));
            changeadd.setText("更改地址");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }


}
