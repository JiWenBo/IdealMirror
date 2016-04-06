package com.example.dllo.idealmirror.fragment;

import android.widget.ImageView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.base.BaseFragment;

/**
 * Created by dllo on 16/4/5.
 */
public class ShoppingFragment extends BaseFragment{
    private ImageView cart;
    @Override
    public int getLayout() {
        return R.layout.activity_shop;
    }

    @Override
    protected void initView() {
     cart = bindView(R.id.shopcart);
    }

    @Override
    protected void initData() {
     cart.setImageResource(R.drawable.shopping_cart_none);
    }
}
