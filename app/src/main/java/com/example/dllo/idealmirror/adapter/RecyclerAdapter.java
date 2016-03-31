package com.example.dllo.idealmirror.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.bean.GoodsListBean;
import com.example.dllo.idealmirror.net.NetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/3/30.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private GoodsListBean goodsListBean;
    private Context context;

    public RecyclerAdapter(Context context) {
        this.context = context;


    }

    public void getData(GoodsListBean goodsListBeans){
        this.goodsListBean = goodsListBeans;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_pageitem, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NetHelper netHelper = new NetHelper();
        ImageLoader loader = netHelper.getImageLoader();
        loader.get(goodsListBean.getData().getList().get(0).getDesign_des().get(position).getImg(), ImageLoader.getImageListener(holder.img,R.mipmap.ic_launcher,R.mipmap.ic_launcher));

    }

    @Override
    public int getItemCount() {
            return  goodsListBean.getData().getList().get(0).getDesign_des().size();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.picture);
        }
    }
}
