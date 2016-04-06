package com.example.dllo.idealmirror.adapter;

import android.content.Context;
import android.support.v4.view.DirectionalViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dllo.idealmirror.R;

import com.example.dllo.idealmirror.bean.GoodList;


/**
 * Created by nan on 16/3/31.
 */
public class PopupAdapter extends BaseAdapter {
    private GoodList bean;
    private Context context;
    String store;

    public PopupAdapter(GoodList beans, Context context, String store) {
        this.bean = beans;
        this.context = context;
        this.store = store;
        notifyDataSetChanged();
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
            holder = new PopupHolder(convertView, position);
            convertView.setTag(holder);
        } else {
            holder = (PopupHolder) convertView.getTag();
        }
        holder.titleTv.setText(bean.getData().getList().get(position).getTitle());

        return convertView;
    }

    public class PopupHolder {
        private TextView titleTv;
        private ImageView underLine;

        public PopupHolder(View itemView, int position) {
            titleTv = (TextView) itemView.findViewById(R.id.popup_item_tv);
            underLine = (ImageView) itemView.findViewById(R.id.popup_item_line);
            if (bean.getData().getList().get(position).getStore().equals(store)) {
                titleTv.setSelected(true);
                underLine.setVisibility(View.VISIBLE);
            } else {
                titleTv.setSelected(false);
                underLine.setVisibility(View.INVISIBLE);
            }
        }
    }
}
