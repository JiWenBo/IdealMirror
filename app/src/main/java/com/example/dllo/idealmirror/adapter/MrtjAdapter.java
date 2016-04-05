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
import com.example.dllo.idealmirror.bean.MrtjListBean;
import com.example.dllo.idealmirror.net.NetHelper;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by dllo on 16/4/1.
 */
public class MrtjAdapter extends RecyclerView.Adapter<MrtjAdapter.MyViewHolder>{
    private MrtjListBean mrtjListBean;
    private Context context;

    public MrtjAdapter(Context context,MrtjListBean mrtjListBeans) {
        this.context = context;
        this.mrtjListBean = mrtjListBeans;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mrtjitem, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NetHelper netHelper = new NetHelper();

        ImageLoader loader = netHelper.getImageLoader();
        String goodsurl = mrtjListBean.getData().getList().get(0).getData_info().getGoods_img();
        loader.get(goodsurl, ImageLoader.getImageListener(holder.img, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
    }

    @Override
    public int getItemCount() {
        return  mrtjListBean.getData().getList().size();

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
