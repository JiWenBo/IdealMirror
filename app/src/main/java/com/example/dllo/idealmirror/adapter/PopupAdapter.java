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
import com.example.dllo.idealmirror.mirrordao.GoodListCache;

import java.util.List;


/**
 * Created by nan on 16/3/31.
 */
public class PopupAdapter extends BaseAdapter {
    private Context context;
    private List<GoodListCache> data;
    String store;

    public PopupAdapter(List<GoodListCache> datas, Context context, String store) {
        this.data = datas;
        this.context = context;
        this.store = store;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
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
        holder.titleTv.setText(data.get(position).getTitle());

        return convertView;
    }

    public class PopupHolder {
        private TextView titleTv;
        private ImageView underLine;

        public PopupHolder(View itemView, int position) {
            titleTv = (TextView) itemView.findViewById(R.id.popup_item_tv);
            underLine = (ImageView) itemView.findViewById(R.id.popup_item_line);
            if (data.get(position).getStore().equals(store)) {
                titleTv.setSelected(true);
                underLine.setVisibility(View.VISIBLE);
            } else {
                titleTv.setSelected(false);
                underLine.setVisibility(View.INVISIBLE);
            }
        }
    }
}
