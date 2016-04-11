package com.example.dllo.idealmirror.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;

import com.example.dllo.idealmirror.bean.AdornPhotosData;
import com.example.dllo.idealmirror.net.NetHelper;
import com.facebook.drawee.view.SimpleDraweeView;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import com.example.dllo.idealmirror.R;
/**
 * Created by LYH on 16/4/8.
 */
public class AdornPhotosRcAdapter extends RecyclerView.Adapter {

    private AdornPhotosData adornPhotosData;
    private Context context;
    final int TYPE_HEAD = 0;
    final int TYPE_PHOTOS = 1;


    public AdornPhotosRcAdapter(AdornPhotosData adornPhotosData, Context context) {
        this.adornPhotosData = adornPhotosData;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return TYPE_HEAD;
        }else {
            return TYPE_PHOTOS;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD){
            View viewHead = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adornphotos_head,parent,false);
            return new HeadViewHolder(viewHead);
        }else {
            View viewPhotos = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adornphotos_item, null);
            return new PhotosViewHolder(viewPhotos);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder){
            if (adornPhotosData.getData().getWear_video().get(position).getType().equals("8")){
                JCVideoPlayer.setThumbImageViewScalType(ImageView.ScaleType.FIT_XY);
                ((HeadViewHolder) holder).jCVideoPlayer.ivStart.performClick();
                ((HeadViewHolder) holder).jCVideoPlayer.setUp(adornPhotosData.getData().getWear_video().get(position).getData(), "");
            }
            NetHelper helper = new NetHelper();
            helper.getImage(adornPhotosData.getData().getWear_video().get(0).getData(), ImageLoader.getImageListener(((HeadViewHolder) holder).jCVideoPlayer.ivThumb, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
//            Uri uri = Uri.parse(adornPhotosData.getData().getWear_video().get(0).getData());
//            ((HeadViewHolder) holder).jCVideoPlayer.ivThumb.setImageURI(uri);
        }
        if (holder instanceof PhotosViewHolder && position > 1){
            Uri uri = Uri.parse(adornPhotosData.getData().getWear_video().get(position).getData());
            ((PhotosViewHolder) holder).iv.setImageURI(uri);
        }
    }


    @Override
    public int getItemCount() {
        return adornPhotosData != null && adornPhotosData.getData().getWear_video().size() > 0 ? adornPhotosData.getData().getWear_video().size() : 0;

    }

    public class HeadViewHolder extends RecyclerView.ViewHolder{

        private JCVideoPlayer jCVideoPlayer;

        public HeadViewHolder(View itemView) {
            super(itemView);
            jCVideoPlayer = (JCVideoPlayer) itemView.findViewById(R.id.videocontroller);
        }
    }

   public class PhotosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SimpleDraweeView iv;
        private LinearLayout line;
        private int position;

        public PhotosViewHolder(View itemView) {
            super(itemView);
            iv = (SimpleDraweeView) itemView.findViewById(R.id.recyclerview_adornPhotos_iv);
            line = (LinearLayout) itemView.findViewById(R.id.recyclerview_adornPhotos_line);
            line.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
