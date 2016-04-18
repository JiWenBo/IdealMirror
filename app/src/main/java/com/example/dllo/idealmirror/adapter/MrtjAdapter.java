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
import com.example.dllo.idealmirror.bean.MrtjListBean;
import com.example.dllo.idealmirror.mirrordao.AllMirrorCache;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.tool.IsNetWork;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/4/1.
 * 每日推荐适配器
 */
public class MrtjAdapter extends RecyclerView.Adapter<MrtjAdapter.MyViewHolder> {
    private MrtjListBean mrtjListBean;
    private Context context;
    private IsNetWork isNetWorks;
    private List<AllMirrorCache> data;

    public MrtjAdapter(Context context) {
        this.context = context;
    }

    public MrtjAdapter(Context context, MrtjListBean mrtjListBeans) {
        this.context = context;
        this.mrtjListBean = mrtjListBeans;
        this.isNetWorks = new IsNetWork();
    }

    public void getData(List<AllMirrorCache> datas) {
        data = new ArrayList<>();
        this.data = datas;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mrtjitem, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        NetHelper netHelper = new NetHelper();
        ImageLoader loader = netHelper.getImageLoader();
        if (isNetWorks.isNetWorkAvilable(context)) {
            final String goodsurl = mrtjListBean.getData().getList().get(0).getData_info().getGoods_img();
            loader.get(goodsurl, ImageLoader.getImageListener(holder.img, R.mipmap.icon_progress_bar, R.drawable.fail));
            holder.price.setText("¥" + mrtjListBean.getData().getList().get(0).getData_info().getGoods_price());
            holder.brand.setText(mrtjListBean.getData().getList().get(0).getData_info().getBrand());
            holder.area.setText(mrtjListBean.getData().getList().get(0).getData_info().getProduct_area());
            holder.name.setText(mrtjListBean.getData().getList().get(0).getData_info().getGoods_name());
            holder.bar.setVisibility(View.GONE);
            holder.pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommodityDetailsActivity.class);
                    intent.putExtra("id", mrtjListBean.getData().getList().get(0).getData_info().getGoods_id());
                    intent.putExtra("backgroundUrl", goodsurl);
                    context.startActivity(intent);
                }
            });
        } else {
            holder.price.setText("¥" + data.get(0).getGoodprice());
            holder.brand.setText(data.get(0).getBrand());
            holder.area.setText(data.get(0).getProductarea());
            holder.name.setText(data.get(0).getGoodname());

            loader.get(data.get(0).getImgurl(), ImageLoader.getImageListener(holder.img, R.mipmap.icon_progress_bar, R.drawable.fail));
            holder.bar.setVisibility(View.GONE);

            holder.pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommodityDetailsActivity.class);
                    intent.putExtra("id", data.get(0).getGoodsid());
                    intent.putExtra("backgroundUrl", data.get(0).getImgurl());
                    ((Activity) context).startActivity(intent);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (isNetWorks.isNetWorkAvilable(context)) {
            return mrtjListBean.getData().getList().size();
        } else {
            return data.size();
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, area, brand, price;
        AutoRelativeLayout pic;
        ProgressBar bar;

        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            img = (ImageView) itemView.findViewById(R.id.picture);
            name = (TextView) itemView.findViewById(R.id.mrtj_name);
            area = (TextView) itemView.findViewById(R.id.mrtj_area);
            brand = (TextView) itemView.findViewById(R.id.mrtj_brand);
            price = (TextView) itemView.findViewById(R.id.mrtj_price);
            pic = (AutoRelativeLayout) itemView.findViewById(R.id.picture_rl);
            bar = (ProgressBar) itemView.findViewById(R.id.progressbar);

        }
    }

}
