package com.example.dllo.idealmirror.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;

import com.example.dllo.idealmirror.activity.PicDetailsActivity;
import com.example.dllo.idealmirror.bean.AdornPhotosData;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

import com.example.dllo.idealmirror.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LYH on 16/4/8.
 * 佩戴图集的适配器
 */
public class AdornPhotosRcAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<AdornPhotosData.DataEntity.WearVideoEntity> list;
    final int TYPE_HEAD = 0;
    final int TYPE_PHOTOS = 1;

    public AdornPhotosRcAdapter(List<AdornPhotosData.DataEntity.WearVideoEntity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    /**
     * 返回数据类型
     * @param position 位置
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_PHOTOS;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            View viewHead = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adornphotos_head, parent, false);
            return new HeadViewHolder(viewHead);
        } else {
            View viewPhotos = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adornphotos_item, null);
            return new PhotosViewHolder(viewPhotos);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        List<String> videoList = new ArrayList<>();
        String videoUrl = null;
        String videoImg = null;
        final List<String> imageList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String type = list.get(i).getType();
            if (type.equals("8")) {
                videoList.add(list.get(i).getData());//将所有的视屏都放入videoList这个集合中
                videoUrl = list.get(i).getData();
                LogUtils.d("视频", videoUrl);
            } else if (type.equals("9")) {
                videoImg = list.get(i).getData();//将视屏图片放入videoImg这个集合中
                LogUtils.d("图片", videoImg);
            } else {
                imageList.add(list.get(i).getData());
                LogUtils.d("图集", "-------" + imageList.size());
            }
        }

        if (holder instanceof HeadViewHolder) {
            JCVideoPlayer.setThumbImageViewScalType(ImageView.ScaleType.FIT_XY);
            ((HeadViewHolder) holder).jCVideoPlayer.ivStart.performClick();
            ((HeadViewHolder) holder).jCVideoPlayer.setUp(videoUrl, "", false);
            LogUtils.d("视频", "--------------" + videoList.size());
            NetHelper helper = new NetHelper();
            helper.getImage(videoImg, ImageLoader.getImageListener(((HeadViewHolder) holder).jCVideoPlayer.ivThumb, R.mipmap.icon_progress_bar, R.drawable.fail));
        }
        /**
         * java 中的instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例。
         * instanceof通过返回一个布尔值来指出，这个对象是否是这个特定类或者是它的子类的一个实例。
         */
        if (holder instanceof PhotosViewHolder) {
            LogUtils.d("图片", "图片list 大小:  " + imageList.size());
            Uri uri = Uri.parse(imageList.get(position - 1));//???
            ((PhotosViewHolder) holder).iv.setImageURI(uri);
            ((PhotosViewHolder) holder).iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PicDetailsActivity.class);
                    //intent.putExtra("images", (Parcelable) image_url_list);//非必须
                    intent.putStringArrayListExtra("images", (ArrayList<String>) imageList);
                    intent.putExtra("position", position);
                    int[] location = new int[2];

                    /**
                     * location 里有iv的横纵坐标,是绝对坐标,location[0] X坐标
                     * location[1]    Y坐标
                     */
                    ((PhotosViewHolder) holder).iv.getLocationOnScreen(location);
                    intent.putExtra("locationX", location[0]);//必须
                    intent.putExtra("locationY", location[1]);//必须

                    /**
                     * 或得到视图的宽高
                     */
                    intent.putExtra("width", ((PhotosViewHolder) holder).iv.getWidth());//必须
                    intent.putExtra("height", ((PhotosViewHolder) holder).iv.getHeight());//必须
                    context.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list != null && list.size() - 1 > 0 ? list.size() - 1 : 0;
    }

    public class HeadViewHolder extends RecyclerView.ViewHolder {
        private JCVideoPlayer jCVideoPlayer;

        public HeadViewHolder(View itemView) {
            super(itemView);
            jCVideoPlayer = (JCVideoPlayer) itemView.findViewById(R.id.video_controller);
        }
    }

    public class PhotosViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView iv;
        private int position;

        public PhotosViewHolder(View itemView) {
            super(itemView);
            iv = (SimpleDraweeView) itemView.findViewById(R.id.recycler_adorn_photos_iv);
        }
    }
}
