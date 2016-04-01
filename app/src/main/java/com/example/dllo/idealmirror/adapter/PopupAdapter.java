package com.example.dllo.idealmirror.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.activity.MainActivity;
import com.example.dllo.idealmirror.bean.PopupListBean;
import com.example.dllo.idealmirror.tool.PopWindow;

import java.util.ArrayList;

/**
 * Created by nan on 16/3/31.
 */
public class PopupAdapter extends BaseAdapter {
    private PopupListBean bean;
    private Context context;
//    private PopupWindow popupWindow;
//    private PopWindow popWindow;
//    private MainActivity mainActivity;

    public PopupAdapter(PopupListBean bean, Context context) {
        this.bean = bean;
        this.context = context;
//        mainActivity = (MainActivity) context;
//        popupWindow = new PopupWindow();
//        popWindow = new PopWindow(context);
    }

    @Override
    public int getCount() {
        return bean.getData().getList().size();
    }

    @Override
    public Object getItem(int position) {
        return bean.getData().getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PopupHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popup, null);
            holder = new PopupHolder();
            holder.title = (TextView) convertView.findViewById(R.id.popup_item_tv);
            holder.underLine = (ImageView) convertView.findViewById(R.id.popup_item_line);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.popup_item_layout);
            convertView.setTag(holder);
        } else {
            holder = (PopupHolder) convertView.getTag();
        }
        holder.title.setText(bean.getData().getList().get(position).getTitle());
        holder.title.setSelected(false);

        final PopupHolder finalHolder = holder;
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mainActivity.getDatafromFragment((int) getItemId(position));
                finalHolder.title.setSelected(true);
                finalHolder.underLine.setVisibility(View.VISIBLE);
            }
        });
        return convertView;
    }

    public class PopupHolder {
        private TextView title;
        private ImageView underLine;
        private LinearLayout layout;
    }
}
