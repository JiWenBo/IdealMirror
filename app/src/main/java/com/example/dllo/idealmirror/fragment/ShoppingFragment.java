package com.example.dllo.idealmirror.fragment;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.activity.MainActivity;
import com.example.dllo.idealmirror.base.BaseFragment;

/**
 * Created by dllo on 16/4/5.
 */
public class ShoppingFragment extends BaseFragment implements View.OnClickListener {
    private ImageView cart;
    private LinearLayout layout;
    private MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_shop;
    }

    @Override
    protected void initView() {
        cart = bindView(R.id.shopping_cart);
        layout = bindView(R.id.shopping_layout);
        layout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        cart.setImageResource(R.drawable.shopping_cart_none);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shopping_layout:
                mainActivity.setMenuFrame(getActivity(), "shopping");
                break;
        }
    }
}
