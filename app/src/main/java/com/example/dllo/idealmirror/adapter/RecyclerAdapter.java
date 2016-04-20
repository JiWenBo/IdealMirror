package com.example.dllo.idealmirror.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.activity.CommodityDetailsActivity;
import com.example.dllo.idealmirror.mirrordao.PlainMirror;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.SingleUniverImage;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by dllo on 16/3/30.
 * 主界面的适配器
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private Context context;
    private PlainMirror data;

    public RecyclerAdapter(Context context, PlainMirror datas) {
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

        String goodsUrl = data.getGoodsimg();
        SingleUniverImage singleUniverImage = SingleUniverImage.getSingleUniverImage();
        singleUniverImage.setImageRes(goodsUrl,holder.img,holder.bar);

        LogUtils.e(position+"S");
        holder.brand.setText(data.getBrand());
        holder.area.setText(data.getProduct());
        holder.price.setText("¥" + data.getGoodsprice());
        holder.name.setText(data.getGoodsname());
        if (!data.getDiscount().equals("")) {
            holder.line.setVisibility(View.VISIBLE);
            holder.disprice.setText("¥" + data.getDiscount());
        }

        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommodityDetailsActivity.class);
                intent.putExtra("id", data.getGoodsid());
                intent.putExtra("backgroundUrl", data.getGoodsimg());
                LogUtils.d(data.getGoodsid());
                ((Activity) context).startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, price, area, disprice, brand;
        View line;
        AutoRelativeLayout pic;
        ProgressBar bar;

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
            pic = (AutoRelativeLayout) itemView.findViewById(R.id.picture_rl);
            bar = (ProgressBar) itemView.findViewById(R.id.parogressbar);
        }
    }
}
