package com.example.dllo.idealmirror.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.bean.GoodsListBean;
import com.example.dllo.idealmirror.mirrordao.PlainMirror;
import com.example.dllo.idealmirror.net.ImageLoaderCache;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by dllo on 16/3/30.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private Context context;
    private PlainMirror data;

    public RecyclerAdapter(Context context,PlainMirror datas) {
        this.context = context;
        this.data = datas;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_goodsitem, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NetHelper netHelper = new NetHelper();
        ImageLoader loader = netHelper.getImageLoader();
        String goodsurl = data.getGoodsimg();
        loader.get(goodsurl, ImageLoader.getImageListener(holder.img, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
        holder.brand.setText(data.getBrand());
        holder.area.setText(data.getProduct());
        holder.price.setText("¥"+data.getGoodsprice());
        holder.name.setText(data.getGoodsname());
        if (!data.getDiscount().equals("")){
            holder.line.setVisibility(View.VISIBLE);
            holder.disprice.setText("¥"+data.getDiscount());
        }

    }

    @Override
    public int getItemCount() {
            return  1;

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name,price,area,disprice,brand;
        View line;
        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            img = (ImageView) itemView.findViewById(R.id.picture);
            name = (TextView) itemView.findViewById(R.id.good_name);
            price = (TextView) itemView.findViewById(R.id.good_price);
            area = (TextView) itemView.findViewById(R.id.good_area);
            disprice = (TextView) itemView.findViewById(R.id.good_disprice);
            brand = (TextView) itemView.findViewById(R.id.good_brand);
            line = itemView.findViewById(R.id.line);
        }
    }
}
