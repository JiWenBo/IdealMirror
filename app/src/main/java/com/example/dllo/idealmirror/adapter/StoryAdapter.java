package com.example.dllo.idealmirror.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.activity.ShareActivity;
import com.example.dllo.idealmirror.bean.MrtjListBean;
import com.example.dllo.idealmirror.bean.StoryListBean;
import com.example.dllo.idealmirror.net.NetHelper;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by dllo on 16/4/1.
 */
public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.MyViewHolder> {
    private StoryListBean storyListBean;
    private Context context;

    public StoryAdapter(Context context, StoryListBean storyListBeans) {
        this.context = context;
        this.storyListBean = storyListBeans;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_storyitem, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NetHelper netHelper = new NetHelper();
        ImageLoader loader = netHelper.getImageLoader();
        String goodsurl = storyListBean.getData().getList().get(position).getStory_img();
        loader.get(goodsurl, ImageLoader.getImageListener(holder.img, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
    }

    @Override
    public int getItemCount() {
        return storyListBean.getData().getList().size();

    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        AutoRelativeLayout story;


        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            img = (ImageView) itemView.findViewById(R.id.picture);
            story = (AutoRelativeLayout) itemView.findViewById(R.id.storyitem);
            story.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ShareActivity.class);
            context.startActivity(intent);
        }
    }

}
