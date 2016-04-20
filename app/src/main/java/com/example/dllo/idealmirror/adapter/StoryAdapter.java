package com.example.dllo.idealmirror.adapter;

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
import com.example.dllo.idealmirror.activity.ShareActivity;
import com.example.dllo.idealmirror.mirrordao.StoryMirror;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.SingleUniverImage;
import com.example.dllo.idealmirror.tool.ToastUtils;
import com.example.dllo.idealmirror.tool.IsNetWork;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by dllo on 16/4/1.
 * 专题分享一级适配器
 */
public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.MyViewHolder> {
    private List<StoryMirror> storyListBean;
    private Context context;

    public StoryAdapter(Context context, List<StoryMirror> storyListBeans) {
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
        String goodsUrl = storyListBean.get(position).getPicimg();
        holder.position = position;
        SingleUniverImage singleUniverImage = SingleUniverImage.getSingleUniverImage();
        singleUniverImage.setImageRes(goodsUrl,holder.img,holder.bar);
        holder.titleTv.setText(storyListBean.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return storyListBean.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        TextView titleTv;
        AutoRelativeLayout story;
        int position;
        ProgressBar bar;

        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            img = (ImageView) itemView.findViewById(R.id.picture);
            titleTv = (TextView) itemView.findViewById(R.id.story_title);
            story = (AutoRelativeLayout) itemView.findViewById(R.id.item_story);
            bar = (ProgressBar) itemView.findViewById(R.id.Story_progressbar);
            story.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (IsNetWork.isNetWorkAvilable(context)) {
                Intent intent = new Intent(context, ShareActivity.class);
                intent.putExtra("posi", position + "");
                context.startActivity(intent);

            } else {
                ToastUtils.showToast(context, context.getString(R.string.java_story_check_net));
            }

        }
    }

}
