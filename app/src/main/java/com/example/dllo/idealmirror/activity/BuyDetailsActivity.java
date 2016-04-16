package com.example.dllo.idealmirror.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.alipay.H5PayDemoActivity;
import com.example.dllo.idealmirror.alipay.PayResult;
import com.example.dllo.idealmirror.alipay.SignUtils;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.bean.Address;
import com.example.dllo.idealmirror.bean.OrderSub;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.example.dllo.idealmirror.tool.ToastUtils;
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
    private TextView title, price, name, nameId, address, addressId, phone, changeAdd,bodycontent;
    private Button buyBtn;
    private Address data;
    private int requsetCode = 1;
    private SetNewBuyUI setNewBuyUI;
    private OrderSub orderSub;//订单实体类

    String str = "service=\"mobile.securitypay.pay\"&partner=\"2088021758262531\"&_input_charset=\"utf-8\"&notify_url=\"http%3A%2F%2Fapi.mirroreye.cn%2Findex.php%2Fali_notify\"&out_trade_no=\"14606860192nw\"&subject=\"GENTLE MONSTER\"&payment_type=\"1\"&seller_id=\"2088021758262531\"&total_fee=\"1300.00\"&body=\"GENTLE MONSTER\"&it_b_pay =\"30m\"&sign=\"aOApPfnRYJfkDc1MfdsheG3Zvy217Ri1JkG76ZtyHyztgQfTf796TTOZTp0udugU%2BJr6RztYRAtdyfcDbzHXgfdLXeNTl8kdisZRejZBYrrk9WqbEnpS2yFUEaN5tU8q2QL%2FKAU%2FUolyVP9MYI1NLr%2FdGnW%2FTRBSKPixXuOMRxI%3D\"&sign_type=\"RSA\"";
    private final static int SDK_PAY_FLAG = 1;
    public static String RSA_PRIVATE = "";// 商户私钥，pkcs8格式

    //支付宝之后的回调 判断是否支付成功

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(BuyDetailsActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(BuyDetailsActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(BuyDetailsActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };


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
        bodycontent = bindView(R.id.content);
    }

    @Override
    protected void initData() {
         getOrder();//订单拉取
        setAddress.setOnClickListener(this);
        close.setOnClickListener(this);

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
                //显示选择支付方式的对话框
                showDialog();
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

    /**
     * 确认下单，弹出支付方式选择框
     */
    public void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择支付方式");
        View v = getLayoutInflater().inflate(R.layout.buydetails_dialog_item,null);
        v.findViewById(R.id.wechat_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BuyDetailsActivity.this, "选择微信支付", Toast.LENGTH_SHORT).show();
            }
        });
        v.findViewById(R.id.ali_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BuyDetailsActivity.this, "选择支付宝支付", Toast.LENGTH_SHORT).show();
                //TODO 支付宝支付
                String sign = sign(str);

                /**
                 * 完整的符合支付宝参数规范的订单信息
                 */
                final String payInfo = str + "&sign=\"" + sign + "\"&" + getSignType();

                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(BuyDetailsActivity.this);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(payInfo, true);

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }

            /**
             * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
             *
             * @param v
             */
            public void h5Pay(View v) {
                Intent intent = new Intent(BuyDetailsActivity.this, H5PayDemoActivity.class);
                Bundle extras = new Bundle();
                /**
                 * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
                 * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
                 * 商户可以根据自己的需求来实现
                 */
                String url = "http://m.meituan.com";
                // url可以是一号店或者美团等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
                extras.putString("url", url);
                intent.putExtras(extras);
                startActivity(intent);

            }

            /**
             * sign the order info. 对订单信息进行签名
             *
             * @param content 待签名订单信息
             */
            private String sign(String content) {
                return SignUtils.sign(content, RSA_PRIVATE);
            }

            /**
             * get the sign type we use. 获取签名方式
             */
            private String getSignType() {
                return "sign_type=\"RSA\"";
            }
        });
        builder.setView(v);
        builder.show();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(setNewBuyUI);
    }

    /**
     * 将订单数据获得
     */
    private void getOrder() {
        Intent  intent = getIntent();
        NetHelper netHelper = new NetHelper();
        HashMap<String,String> data;
        data = new HashMap<>();
        data.put("token",TOKEN);
        data.put("goods_id",intent.getStringExtra("good_id"));
        LogUtils.d(intent.getStringExtra("good_id"));
        data.put("goods_num", "1");
        data.put("price",intent.getStringExtra("good_price"));
        LogUtils.d(intent.getStringExtra("good_price"));
        data.put("device_type","3");
        netHelper.getInformation(ORDER_SUB, new VolleyListener() {
            @Override
            public void getSuccess(String body) {
                try {
                    LogUtils.d(body);
                    JSONObject jsonObject = new JSONObject(body);
                    Gson gson = new Gson();
                    orderSub = new OrderSub();
                    orderSub =  gson.fromJson(jsonObject.toString(),OrderSub.class);

                    Uri uri = Uri.parse(orderSub.getData().getGoods().getPic());
                    mirrorImg.setImageURI(uri);
                    title.setText(orderSub.getData().getGoods().getGoods_name());
                    price.setText("¥" + orderSub.getData().getGoods().getPrice());
                    bodycontent.setText(orderSub.getData().getGoods().getBook_copy());
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
