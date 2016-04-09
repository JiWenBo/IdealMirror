package com.example.dllo.idealmirror.tool;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.activity.MainActivity;
import com.example.dllo.idealmirror.adapter.PopupAdapter;
import com.example.dllo.idealmirror.adapter.VerticalAdapter;
import com.example.dllo.idealmirror.bean.GoodList;

import com.example.dllo.idealmirror.mirrordao.GoodListCache;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by LYH on 16/3/30.
 */
public class PopWindow implements Url, View.OnClickListener {
    private ListView listView;
    private Context context;
    private PopupWindow popupWindow;
    private PopupAdapter adapter;
    private LinearLayout homeLayout, returnLayout;
    private TextView homeTv, returnTv;
    private ImageView homeIv, returnIv;
    private MainActivity mainActivity;
    private static List<GoodListCache>  bean;
    String store;

    public PopWindow(Context context) {
        this.context = context;
    }

    public PopWindow(Context context, String store) {
        this.context = context;
        this.store = store;
    }

    public void showPopWindow(View v) {
        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow_item, null);

        // 设置popupWindow的高度 宽度
        popupWindow = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        // 颜色半透明
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        // 设置popupWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        // 设置中间显示
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        popupWindow.update();

        // 初始化组件
        initViewPop(view);

    }

    /**
     * 从activity传递实体类
     * @param beans
     */
    public void initDataPop(List<GoodListCache> beans) {
        this.bean = beans;
    }


    private void initViewPop(View view) {
        listView = (ListView) view.findViewById(R.id.popup_list);
        homeTv = (TextView) view.findViewById(R.id.popup_to_home_title);
        homeIv = (ImageView) view.findViewById(R.id.popup_to_home_line);
        homeLayout = (LinearLayout) view.findViewById(R.id.popup_to_home_layout);
        homeLayout.setOnClickListener(this);

        returnTv = (TextView) view.findViewById(R.id.popup_return_title);
        returnIv = (ImageView) view.findViewById(R.id.popup_return_line);
        returnLayout = (LinearLayout) view.findViewById(R.id.popup_return_layout);
        returnLayout.setOnClickListener(this);
        adapter = new PopupAdapter(bean, context, store);
        listView.setAdapter(adapter);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击界面退出
                popupWindow.dismiss();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainActivity = (MainActivity) context;
                mainActivity.getDatafromFragment(position);
                popupWindow.dismiss();
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
