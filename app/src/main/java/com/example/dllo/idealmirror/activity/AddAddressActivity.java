package com.example.dllo.idealmirror.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.base.BaseActivity;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.ToastUtils;
import com.example.dllo.idealmirror.tool.Url;

import java.util.HashMap;

/**
 * Created by LYH on 16/3/31.
 * 添加地址界面
 */
public class AddAddressActivity extends BaseActivity implements VolleyListener, Url {
    EditText name, number, address;
    TextView nameTitle, numTitle, addTitle, title;
    Button subBtn;
    ImageView close;
    String addr;
    HashMap<String, String> param;
    private static String userToken;

    @Override
    protected int setContent() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initView() {
        name = bindView(R.id.add_name);
        number = bindView(R.id.add_number);
        address = bindView(R.id.add_address);
        subBtn = bindView(R.id.submit_btn);
        nameTitle = bindView(R.id.add_name_title);
        numTitle = bindView(R.id.add_num_title);
        addTitle = bindView(R.id.add_address_title);
        title = bindView(R.id.add_title);
        close = bindView(R.id.set_close);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        userToken  = intent.getStringExtra("token");
        addr = intent.getStringExtra("addr_id");
        name.setText(intent.getStringExtra("name"));
        number.setText(intent.getStringExtra("number"));
        address.setText(intent.getStringExtra("address"));
        nameTitle.setText(intent.getStringExtra("nameTitle"));
        numTitle.setText(intent.getStringExtra("numTitle"));
        addTitle.setText(intent.getStringExtra("addTitle"));
        title.setText(intent.getStringExtra("title"));
        subBtn.setText(intent.getStringExtra("btnText"));
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().length() == 0 || number.getText().length() == 0 || address.getText().length() == 0) {
                    ToastUtils.showToast(AddAddressActivity.this, getString(R.string.rightmessage));

                } else {
                    if (title.getText().equals(getString(R.string.tianjiadizhi))) {
                        NetHelper helper = new NetHelper();
                        param = new HashMap<>();
                        param.put("cellphone", number.getText().toString());
                        param.put("addr_info", address.getText().toString());
                        param.put("username", name.getText().toString());
                        param.put("token", userToken);
                        helper.getInformation(USER_ADD_ADDRESS, AddAddressActivity.this, param);
                    } else {

                        NetHelper helper = new NetHelper();
                        param = new HashMap<>();
                        param.put("addr_id", addr);
                        param.put("cellphone", number.getText().toString());
                        param.put("addr_info", address.getText().toString());
                        param.put("username", name.getText().toString());
                        param.put("token",userToken);
                        helper.getInformation(USER_EDIT_ADDRESS,AddAddressActivity.this, param);

                    }
                }

            }
        });
    }


    @Override
    public void getSuccess(String body) {
        /*点击之后刷新UI*/
        Intent setNewUi = new Intent(BROIDCAST);
        sendBroadcast(setNewUi);
        finish();
    }

    @Override
    public void getFail() {

    }

}
