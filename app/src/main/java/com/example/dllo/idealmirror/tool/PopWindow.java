package com.example.dllo.idealmirror.tool;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.dllo.idealmirror.R;

/**
 * Created by LYH on 16/3/30.
 */
public class PopWindow implements View.OnClickListener {

    private TextView watchAllTv,watchOpticalTv,watchSunTv,shareTv,shopCartTv,returnHomeTv,exitTv;
    private ImageView watchAllIv,watchOpticalIv,watchSunIv,shareIv,shopCartIv,returnHomeIv,exitIv;
    private Context context;
    private PopupWindow popupWindow;

    public PopWindow(Context context) {
        this.context = context;
    }

    public void showPopupWindow(View v){
        popupWindow = new PopupWindow(context);
        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow_item, null);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.update();

        //初始化组件
        initView(view);
    }


    public void initView(View view){
        watchAllTv = (TextView) view.findViewById(R.id.popup_watchAll);
        watchOpticalTv = (TextView) view.findViewById(R.id.popup_watchOptical);
        watchSunTv = (TextView) view.findViewById(R.id.popup_watchSun);
        shareTv = (TextView) view.findViewById(R.id.popup_share);
        shopCartTv = (TextView) view.findViewById(R.id.popup_shopCart);
        returnHomeTv = (TextView) view.findViewById(R.id.popup_returnHome);
        exitTv = (TextView) view.findViewById(R.id.popup_exit);
        watchAllIv = (ImageView) view.findViewById(R.id.popup_watchAll_underline);
        watchOpticalIv = (ImageView) view.findViewById(R.id.popup_watchOptical_underline);
        watchSunIv = (ImageView) view.findViewById(R.id.popup_watchSun_underline);
        shareIv = (ImageView) view.findViewById(R.id.popup_share_underline);
        shopCartIv = (ImageView) view.findViewById(R.id.popup_shopCart_underline);
        returnHomeIv = (ImageView) view.findViewById(R.id.popup_returnHome_underline);
        exitIv = (ImageView) view.findViewById(R.id.popup_exit_underline);
        watchAllTv.setOnClickListener(this);
        watchOpticalTv.setOnClickListener(this);
        watchSunTv.setOnClickListener(this);
        shareTv.setOnClickListener(this);
        shopCartTv.setOnClickListener(this);
        returnHomeTv.setOnClickListener(this);
        exitTv.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.popup_watchAll:
                watchAllIv.setVisibility(View.VISIBLE);

                watchOpticalIv.setVisibility(View.INVISIBLE);
                watchSunIv.setVisibility(View.INVISIBLE);
                shareIv.setVisibility(View.INVISIBLE);
                shopCartIv.setVisibility(View.INVISIBLE);
                returnHomeIv.setVisibility(View.INVISIBLE);
                exitIv.setVisibility(View.INVISIBLE);
                break;
            case R.id.popup_watchOptical:
                watchOpticalIv.setVisibility(View.VISIBLE);

                watchAllIv.setVisibility(View.INVISIBLE);
                watchSunIv.setVisibility(View.INVISIBLE);
                shareIv.setVisibility(View.INVISIBLE);
                shopCartIv.setVisibility(View.INVISIBLE);
                returnHomeIv.setVisibility(View.INVISIBLE);
                exitIv.setVisibility(View.INVISIBLE);
                break;
            case R.id.popup_watchSun:
                watchSunIv.setVisibility(View.VISIBLE);

                watchAllIv.setVisibility(View.INVISIBLE);
                watchOpticalIv.setVisibility(View.INVISIBLE);
                shareIv.setVisibility(View.INVISIBLE);
                shopCartIv.setVisibility(View.INVISIBLE);
                returnHomeIv.setVisibility(View.INVISIBLE);
                exitIv.setVisibility(View.INVISIBLE);
                break;
            case R.id.popup_share:
                shareIv.setVisibility(View.VISIBLE);

                watchAllIv.setVisibility(View.INVISIBLE);
                watchOpticalIv.setVisibility(View.INVISIBLE);
                watchSunIv.setVisibility(View.INVISIBLE);
                shopCartIv.setVisibility(View.INVISIBLE);
                returnHomeIv.setVisibility(View.INVISIBLE);
                exitIv.setVisibility(View.INVISIBLE);

                break;
            case R.id.popup_shopCart:
                shopCartIv.setVisibility(View.VISIBLE);

                watchAllIv.setVisibility(View.INVISIBLE);
                watchOpticalIv.setVisibility(View.INVISIBLE);
                watchSunIv.setVisibility(View.INVISIBLE);
                shareIv.setVisibility(View.INVISIBLE);
                returnHomeIv.setVisibility(View.INVISIBLE);
                exitIv.setVisibility(View.INVISIBLE);
                break;
            case R.id.popup_returnHome:
                returnHomeIv.setVisibility(View.VISIBLE);

                watchAllIv.setVisibility(View.INVISIBLE);
                watchOpticalIv.setVisibility(View.INVISIBLE);
                watchSunIv.setVisibility(View.INVISIBLE);
                shareIv.setVisibility(View.INVISIBLE);
                shopCartIv.setVisibility(View.INVISIBLE);
                exitIv.setVisibility(View.INVISIBLE);
                break;
            case R.id.popup_exit:
                exitIv.setVisibility(View.VISIBLE);

                watchAllIv.setVisibility(View.INVISIBLE);
                watchOpticalIv.setVisibility(View.INVISIBLE);
                watchSunIv.setVisibility(View.INVISIBLE);
                shareIv.setVisibility(View.INVISIBLE);
                shopCartIv.setVisibility(View.INVISIBLE);
                returnHomeIv.setVisibility(View.INVISIBLE);
                break;
        }
    }

}
