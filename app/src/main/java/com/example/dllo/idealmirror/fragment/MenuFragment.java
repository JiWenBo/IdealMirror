package com.example.dllo.idealmirror.fragment;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.activity.MainActivity;
import com.example.dllo.idealmirror.adapter.MenuAdapter;
import com.example.dllo.idealmirror.base.BaseFragment;
import com.example.dllo.idealmirror.mirrordao.GoodListCache;

import java.util.List;

/**
 * Created by nan on 16/4/12.
 */
public class MenuFragment extends BaseFragment implements View.OnClickListener {
    private ListView listView;
    private Context context;
    private MenuAdapter adapter;
    private LinearLayout storyLayout, shoppingLayout, homeLayout, returnLayout, backLayout;
    private TextView storyTv, shoppingTv;
    private ImageView storyIv, shoppingIv;
    private MainActivity mainActivity;
    private static List<GoodListCache> bean;
    String store;

    public MenuFragment() {
    }

    public MenuFragment(String store, Context context, List<GoodListCache> bean) {
        this.store = store;
        this.context = context;
        this.bean = bean;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_menu;
    }

    @Override
    protected void initView() {
        storyTv = bindView(R.id.menu_to_story_title);
        storyIv = bindView(R.id.menu_to_story_line);
        storyLayout = bindView(R.id.menu_to_story_layout);
        storyLayout.setOnClickListener(this);

        shoppingTv = bindView(R.id.menu_to_shopping_title);
        shoppingIv = bindView(R.id.menu_to_shopping_line);
        shoppingLayout = bindView(R.id.menu_to_shopping_layout);
        shoppingLayout.setOnClickListener(this);

        homeLayout = bindView(R.id.menu_to_home_layout);
        homeLayout.setOnClickListener(this);

        returnLayout = bindView(R.id.menu_return_layout);
        returnLayout.setOnClickListener(this);

        backLayout = bindView(R.id.fragment_menu_back);
        backLayout.setOnClickListener(this);

        listView = bindView(R.id.menu_listview);
        adapter = new MenuAdapter(bean, context, store);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainActivity.getDataFromFragment(position);
                removeMenu();
            }
        });

    }

    @Override
    protected void initData() {
        if (store.equals("分享")) {
            storyTv.setSelected(true);
            storyIv.setVisibility(View.VISIBLE);
        }
        else if (store.equals("购物")) {
            shoppingTv.setSelected(true);
            shoppingIv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_to_story_layout:
                mainActivity.getDataFromFragment(3);
                removeMenu();
                break;
            case R.id.menu_to_shopping_layout:
                mainActivity.getDataFromFragment(4);
                removeMenu();
                break;
            case R.id.menu_to_home_layout:
                mainActivity.getDataFromFragment(0);
                removeMenu();
                break;
            case R.id.menu_return_layout:
                removeMenu();
                break;
            case R.id.fragment_menu_back:
                removeMenu();
                break;
        }

    }

    public void removeMenu() {
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().remove(this).commit();
    }


}
