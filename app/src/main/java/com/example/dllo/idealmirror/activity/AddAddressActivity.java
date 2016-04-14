package com.example.dllo.idealmirror.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    TextView nametitle, numtitle, addtitle, title;
    Button sub_btn;
    ImageView close;
    String addr;
    HashMap<String, String> parm;


    @Override
    protected int setContent() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initView() {

        name = bindView(R.id.add_name);
        number = bindView(R.id.add_number);
        address = bindView(R.id.add_address);
        sub_btn = bindView(R.id.submit);
        nametitle = bindView(R.id.add_nametitle);
        numtitle = bindView(R.id.add_numtitel);
        addtitle = bindView(R.id.add_addtitle);
        title = bindView(R.id.add_title);
        close = bindView(R.id.set_close);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        addr = intent.getStringExtra("addr_id");
        name.setText(intent.getStringExtra("name"));
        number.setText(intent.getStringExtra("number"));
        address.setText(intent.getStringExtra("address"));
        nametitle.setText(intent.getStringExtra("nametitie"));
        numtitle.setText(intent.getStringExtra("numtitle"));
        addtitle.setText(intent.getStringExtra("addtitle"));
        title.setText(intent.getStringExtra("title"));
        sub_btn.setText(intent.getStringExtra("btntext"));
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText().equals(getString(R.string.tianjiadizhi)))
                {
                    NetHelper helper = new NetHelper();
                    parm = new HashMap<>();
                    parm.put("cellphone", number.getText().toString());
                    parm.put("addr_info", address.getText().toString());
                    parm.put("username", name.getText().toString());
                    parm.put("token", TOKEN);
                    helper.getInformation(USER_ADD_ADDRESS, AddAddressActivity.this, parm);
                }
                else {

                    NetHelper helper = new NetHelper();
                    parm = new HashMap<>();
                    parm.put("addr_id",addr);
                    parm.put("cellphone", number.getText().toString());
                    parm.put("addr_info", address.getText().toString());
                    parm.put("username", name.getText().toString());
                    parm.put("token", TOKEN);
                    helper.getInformation(USER_EDIT_ADDRESS, AddAddressActivity.this, parm);
                }

            }
        });
    }

    @Override
    public void getSuccess(String body) {

        finish();
    }

    @Override
    public void getFail() {

    }
}
