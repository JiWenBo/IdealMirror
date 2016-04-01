package com.example.dllo.idealmirror.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.bean.GoodsListBean;
import com.example.dllo.idealmirror.net.ImageLoaderCache;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/3/30.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private GoodsListBean goodsListBean;
    private Context context;
    private ImageLoaderCache imageLoaderCache;

    public RecyclerAdapter(Context context,GoodsListBean goodsListBeans) {
        this.context = context;
        this.goodsListBean = goodsListBeans;
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
        String goodsurl = goodsListBean.getData().getList().get(0).getGoods_img();
        LogUtils.d(goodsurl);
        loader.get(goodsurl, ImageLoader.getImageListener(holder.img, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
        //imageLoaderCache.getImageLoader(goodsListBean.getData().getList().get(0).getGoods_img(), holder.img);

    }

    @Override
    public int getItemCount() {
            return  goodsListBean.getData().getList().size();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;


        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            img = (ImageView) itemView.findViewById(R.id.picture);
           // tex = (LinearLayout) itemView.findViewById(R.id.itemx);
        }
    }
}
