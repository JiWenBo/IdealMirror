package com.example.dllo.idealmirror.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.idealmirror.activity.CommodityDetailsActivity;
import com.example.dllo.idealmirror.base.BaseApplication;
import com.example.dllo.idealmirror.bean.CommodityDetailsData;
import com.example.dllo.idealmirror.bean.GoodsListBean;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.example.dllo.idealmirror.tool.Url;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by LYH on 16/4/1.
 * 二级界面
 */
public class CommodityDetailsAdapter extends RecyclerView.Adapter {

    private CommodityDetailsData goodsListData;
    private Context context;
    private RecyclerView recyclerView;
    private int layoutScrollValue;
    private int dy;
    final int TYPE_HEAD = 0;
    final int TYPE_TRANSPARENT = 1;
    final int TYPE_GOODS_TITLE = 2;
    final int TYPE_GOODS_DETAILS = 3;


    public CommodityDetailsAdapter(CommodityDetailsData goodsListData, Context context) {
        this.goodsListData = goodsListData;
        this.context = context;
    }


    /**
     * 该方法为接收activity传来的监听recycleview滑动距离的value
     */
    public void setScrollValue(int scrollValue, int dy) {
        this.layoutScrollValue = scrollValue;
        this.dy = dy;
        // 必须加上这句话,持续的刷新从Actvity 接收的滑动值.
        try {
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 决定元素的布局使用哪种类型
     * @param
     * @return 一个int型标志，传递给onCreateViewHolder的第二个参数
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else if (position == 1) {
            return TYPE_TRANSPARENT;
        } else if (position == 2) {
            return TYPE_GOODS_TITLE;
        } else {
            return TYPE_GOODS_DETAILS;
        }
    }

    /** 渲染具体的ViewHolder
     * @param parent   ViewHolder的容器
     * @param viewType 一个标志，根据该标志可以实现渲染不同类型的ViewHolder
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            View viewHead = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_commodity_item_head, parent, false);
            return new HeadViewHolder(viewHead);
        } else if (viewType == TYPE_TRANSPARENT) {
            View viewTransparent = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_commodity_item_lucency, parent, false);
            return new TransparentViewHolder(viewTransparent);
        } else if (viewType == TYPE_GOODS_TITLE) {
            View viewGoodsTitle = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_commodity_item_title, parent, false);
            return new GoodsTitleViewHolder(viewGoodsTitle);
        } else {
            View viewGoodsDetails = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_commoditydetails_item, parent, false);
            return new GoodsDetailsViewHolder(viewGoodsDetails);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       LogUtils.d("xxx", "---holder--" + holder.getClass() + position);
        // 头布局
        if (holder instanceof HeadViewHolder) {
            ((HeadViewHolder) holder).commodityHeadEnglishNameTv.setText(goodsListData.getData().getGoods_name());
            ((HeadViewHolder) holder).commodityHeadNameTv.setText(goodsListData.getData().getBrand());
            ((HeadViewHolder) holder).commodityHeadStoryTv.setText(goodsListData.getData().getInfo_des());
            ((HeadViewHolder) holder).commodityHeadMoney.setText(goodsListData.getData().getGoods_price());
            ((HeadViewHolder) holder).shareIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toShare();
                }
            });

        }

        //带标题的布局
        if (holder instanceof GoodsTitleViewHolder) {
            //如果holder的实例是GoodsTitleViewHolder的话
            //滑动的时候使文字布局发生偏移
            int valueTitle = layoutScrollValue;
            int heiTitle = ((GoodsTitleViewHolder) holder).commodityTitleRl.getHeight();
            RelativeLayout.LayoutParams paramsTitle = (RelativeLayout.LayoutParams) ((GoodsTitleViewHolder) holder).commodityTitleLinearLayout.getLayoutParams();

            paramsTitle.setMargins(0, (int) ((valueTitle + heiTitle * (position - 1)) * 0.15), 0, 0);
            ((GoodsTitleViewHolder) holder).commodityTitleLinearLayout.setLayoutParams(paramsTitle);

            NetHelper helper = new NetHelper();
            //加数据
            ((GoodsTitleViewHolder) holder).commodityTitleNameTv.setText(goodsListData.getData().getBrand());
            ((GoodsTitleViewHolder) holder).commodityTitleTv.setText(goodsListData.getData().getGoods_data().get(0).getLocation());
            ((GoodsTitleViewHolder) holder).commodityTitleEngTv.setText(goodsListData.getData().getGoods_data().get(0).getEnglish());
            ((GoodsTitleViewHolder) holder).commodityTitleCity.setText(goodsListData.getData().getGoods_data().get(0).getCountry());
            ((GoodsTitleViewHolder) holder).commodityTitleIntro.setText(goodsListData.getData().getGoods_data().get(0).getIntroContent());

            Uri uri = Uri.parse(goodsListData.getData().getGoods_pic());
            ((GoodsTitleViewHolder) holder).commodityTitleIv.setImageURI(uri);

            ((GoodsTitleViewHolder) holder).position = position;
        }

        //不带标题的布局
        if (holder instanceof GoodsDetailsViewHolder) {
            //滑动的时候使文字布局发生偏移
            int heiDetails = ((GoodsDetailsViewHolder) holder).commodityDetailsRl.getHeight();
            int valueDetails = layoutScrollValue;
            RelativeLayout.LayoutParams paramsDetails = (RelativeLayout.LayoutParams) ((GoodsDetailsViewHolder) holder).commodityDetailsLinearLayout.getLayoutParams();
            paramsDetails.setMargins(0, (int) ((valueDetails + heiDetails * (position - 1)) * 0.15), 0, 0);
            LogUtils.d("xxx", layoutScrollValue + "");
            ((GoodsDetailsViewHolder) holder).commodityDetailsLinearLayout.setLayoutParams(paramsDetails);


            if (goodsListData.getData().getDesign_des().get(position - 2).getType().equals("1")) {
                //加数据
                ((GoodsDetailsViewHolder) holder).commodityDetailsName.setText(goodsListData.getData().getGoods_data().get(position - 2).getName());
                ((GoodsDetailsViewHolder) holder).commodityDetailsIntro.setText(goodsListData.getData().getGoods_data().get(position - 2).getIntroContent());
                ((GoodsDetailsViewHolder) holder).commodityDetailsLinearLayout.setVisibility(View.VISIBLE);

            } else {
                ((GoodsDetailsViewHolder) holder).commodityDetailsLinearLayout.setVisibility(View.GONE);
            }

            Uri uri = Uri.parse(goodsListData.getData().getDesign_des().get(position - 2).getImg());
            ((GoodsDetailsViewHolder) holder).commodityDetailsIv.setImageURI(uri);

        }
    }

    @Override
    public int getItemCount() {
        return goodsListData != null && goodsListData.getData().getDesign_des().size() + 2 > 0 ? goodsListData.getData().getDesign_des().size() + 2 : 0;
    }

    /**
     * 新建缓存类 商品详情二级页面头布局(半透明)缓存类
     */
    public class HeadViewHolder extends RecyclerView.ViewHolder {
        //需要网络解析的数据
        private TextView commodityHeadEnglishNameTv, commodityHeadNameTv, commodityHeadStoryTv, commodityHeadMoney;
        private ImageView shareIv;

        public HeadViewHolder(View itemView) {
            super(itemView);
            commodityHeadEnglishNameTv = (TextView) itemView.findViewById(R.id.commoditydetails_englishTitle);
            commodityHeadNameTv = (TextView) itemView.findViewById(R.id.commoditydetails_name);
            commodityHeadStoryTv = (TextView) itemView.findViewById(R.id.commoditydetails_commodityStory);
            commodityHeadMoney = (TextView) itemView.findViewById(R.id.commoditydetails_commodityMoney);
            shareIv = (ImageView) itemView.findViewById(R.id.commodity_detail_share);
        }
    }

    /**
     * 分享
     */
    private void toShare() {
        ShareSDK.initSDK(BaseApplication.getContext());
        OnekeyShare onekeyShare = new OnekeyShare();
        // 关闭sso授权
        onekeyShare.disableSSOWhenAuthorize();
        // text是分享文本 所有的平台都需要这个字段
        onekeyShare.setText(goodsListData.getData().getBrand());
        // 微博 QQ空间
        onekeyShare.setImageUrl(goodsListData.getData().getGoods_img());
        // 微博
        onekeyShare.setUrl(goodsListData.getData().getGoods_share() + goodsListData.getData().getGoods_id());

        // QQ空间
        onekeyShare.setTitle("美若推荐");
        // QQ空间  titleUrl是标题的网络连接
        onekeyShare.setTitleUrl(goodsListData.getData().getGoods_share() + goodsListData.getData().getGoods_id());
        // QQ空间  site是分享此内容的网站名称
        onekeyShare.setSite(String.valueOf(R.string.app_name));
        // QQ空间  siteUrl是分享此内容的网站地址
        onekeyShare.setSiteUrl(goodsListData.getData().getGoods_share() + goodsListData.getData().getGoods_id());

        // 启动分享GUI
        onekeyShare.show(BaseApplication.getContext());
    }

    /**
     * 商品二级页面第二布局(全透明)
     */
    public class TransparentViewHolder extends RecyclerView.ViewHolder {
        public TransparentViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 商品二级页面商品详情带标题布局缓存类
     */
    public class GoodsTitleViewHolder extends RecyclerView.ViewHolder {
        //需要网络解析的数据
        private TextView commodityTitleNameTv, commodityTitleTv, commodityTitleEngTv, commodityTitleCity, commodityTitleIntro;
        private SimpleDraweeView commodityTitleIv;
        private LinearLayout commodityTitleLinearLayout;
        private RelativeLayout commodityTitleRl;
        private int position;

        public GoodsTitleViewHolder(View itemView) {
            super(itemView);
            commodityTitleNameTv = (TextView) itemView.findViewById(R.id.commoditydetails_title_name);
            commodityTitleLinearLayout = (LinearLayout) itemView.findViewById(R.id.recyclerview_title_storyLinear);
            commodityTitleIv = (SimpleDraweeView) itemView.findViewById(R.id.recyclerview_title_iv);
            commodityTitleTv = (TextView) itemView.findViewById(R.id.recyclerview_title_title);
            commodityTitleEngTv = (TextView) itemView.findViewById(R.id.recyclerview_title_englishTitle);
            commodityTitleCity = (TextView) itemView.findViewById(R.id.recyclerview_title_city);
            commodityTitleIntro = (TextView) itemView.findViewById(R.id.recyclerview_title_intro);
            commodityTitleRl = (RelativeLayout) itemView.findViewById(R.id.commoditydetails_title_rl);
        }
    }

    /**
     * 商品二级页面商品详情不带标题布局缓存类
     */
    public class GoodsDetailsViewHolder extends RecyclerView.ViewHolder {
        //需要网络解析的数据
        private TextView commodityDetailsName, commodityDetailsIntro;
        private SimpleDraweeView commodityDetailsIv;
        private LinearLayout commodityDetailsLinearLayout;
        private RelativeLayout commodityDetailsRl;
        private int position;

        public GoodsDetailsViewHolder(View itemView) {
            super(itemView);
            commodityDetailsLinearLayout = (LinearLayout) itemView.findViewById(R.id.recyclerview_commodityDetails_storyLinear);
            commodityDetailsIv = (SimpleDraweeView) itemView.findViewById(R.id.recyclerview_commodityDetails_iv);
            commodityDetailsName = (TextView) itemView.findViewById(R.id.recyclerview_commodityDetails_title);
            commodityDetailsIntro = (TextView) itemView.findViewById(R.id.recyclerview_commodityDetails_intro);
            commodityDetailsRl = (RelativeLayout) itemView.findViewById(R.id.recyclerview_commodityDetails_rl);
        }
    }

}
