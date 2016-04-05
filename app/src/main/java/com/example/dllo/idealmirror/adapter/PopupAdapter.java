package com.example.dllo.idealmirror.adapter;

import android.content.Context;
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
    private int position;

    public PopupAdapter(GoodList beans, Context context, int position) {
        this.bean = beans;
        this.context = context;
        this.position = position;
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
            convertView.setTag(holder);
        } else {
            holder = (PopupHolder) convertView.getTag();
        }
        holder.title.setText(bean.getData().getList().get(position).getTitle());
        holder.title.setSelected(false);
        holder.underLine.setVisibility(View.INVISIBLE);

        return convertView;
    }

    public class PopupHolder {
        private TextView title;
        private ImageView underLine;
    }
}
