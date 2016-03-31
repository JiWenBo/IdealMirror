package com.example.dllo.idealmirror.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.adapter.AllAddressRcAdapter;
import com.example.dllo.idealmirror.bean.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LYH on 16/3/31.
 */
public class AllAddressActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AllAddressRcAdapter allAddressRcAdapter;
    private List<Address> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_address);
        recyclerView = (RecyclerView) findViewById(R.id.allAddress_recyclerview);
        allAddressRcAdapter = new AllAddressRcAdapter();
        recyclerView.setAdapter(allAddressRcAdapter);
        GridLayoutManager gm = new GridLayoutManager(this,1);
        gm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gm);
        data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new Address("李亚辉","辽宁省大连市沙河口区数码广场蓝鸥科技啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊","18042692006"));
        }
        allAddressRcAdapter.addData(data);

    }
}
