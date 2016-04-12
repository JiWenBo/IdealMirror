package com.example.dllo.idealmirror.fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.adapter.RecyclerAdapter;
import com.example.dllo.idealmirror.base.BaseFragment;
import com.example.dllo.idealmirror.bean.GoodsListBean;
import com.example.dllo.idealmirror.mirrordao.DaoMaster;
import com.example.dllo.idealmirror.mirrordao.DaoSession;
import com.example.dllo.idealmirror.mirrordao.DaoSingleton;
import com.example.dllo.idealmirror.mirrordao.PlainMirror;
import com.example.dllo.idealmirror.mirrordao.PlainMirrorDao;
import com.example.dllo.idealmirror.net.NetHelper;
import com.example.dllo.idealmirror.net.VolleyListener;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.example.dllo.idealmirror.tool.PopWindow;
import com.example.dllo.idealmirror.tool.Url;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dllo on 16/3/29.
 */
public class GoodsListFragment extends BaseFragment implements VolleyListener, Url {
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private GoodsListBean goodsListBean;
    private HashMap<String, String> parm;
    private LinearLayout layout;
    private PopWindow popWindow;
    private static String title,numType;
    private String store;
    private TextView titleTv;
    /*数据库类*/
    private List<PlainMirror> plainMirrors;
    private PlainMirror plainMirror;

    private PlainMirrorDao plainMirrorDao;


    @Override
    public int getLayout() {
        return R.layout.fragment_goods;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.recycle);
        layout = bindView(R.id.page_layout);
        titleTv = bindView(R.id.goods_fragment_title);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow = new PopWindow(getContext(), store);
                popWindow.showPopWindow(v);
            }
        });
    }

    /**
     * 商品列表list参数
     */
    @Override
    protected void initData() {

        Bundle bundle = getArguments();
        title = bundle.getString("title");
        store = bundle.getString("store");
        numType = bundle.getString("type");
        parm = new HashMap<>();
        NetHelper netHelper = new NetHelper();
        parm.put("token", "null");
        parm.put("device_type", "3");
        parm.put("category_id", "");
        netHelper.getInformation(GOODS_LIST, this, parm);
        titleTv.setText(title);
    }

    @Override
    public void getSuccess(String body) {
        try {
            LogUtils.d("请求成功");
            JSONObject object = new JSONObject(body);
            Gson gson = new Gson();
            goodsListBean = gson.fromJson(object.toString(), GoodsListBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*添加数据库*/
        plainMirrorDao = DaoSingleton.getInstance().getPlainMirrorDao();
        plainMirrorDao.deleteAll();
        plainMirrors = new ArrayList<>();
        for (int i=0;i<goodsListBean.getData().getList().size();i++){
            plainMirror = new PlainMirror();
            plainMirror.setGoodsimg(goodsListBean.getData().getList().get(i).getGoods_img());
            plainMirror.setBrand(goodsListBean.getData().getList().get(i).getBrand());
            plainMirror.setDiscount(goodsListBean.getData().getList().get(i).getDiscount_price());
            plainMirror.setGoodsname(goodsListBean.getData().getList().get(i).getGoods_name());
            plainMirror.setGoodsprice(goodsListBean.getData().getList().get(i).getGoods_price());
            plainMirror.setProduct(goodsListBean.getData().getList().get(i).getProduct_area());
            plainMirror.setWholestorge(goodsListBean.getData().getList().get(i).getWhole_storge());
            plainMirror.setGoodsid(goodsListBean.getData().getList().get(i).getGoods_id());
            plainMirrors.add(plainMirror);
            plainMirrorDao.insert(plainMirror);
        }
       plainMirrors = plainMirrorDao.queryBuilder().list();
        for (int j=0;j<plainMirrors.size();j++){
            if (plainMirrors.get(j).getWholestorge().equals(numType)){
                recyclerAdapter = new RecyclerAdapter(getContext(),plainMirrors.get(j));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(recyclerAdapter);
                break;
            }
        }
    }

    @Override
    public void getFail() {
        plainMirrorDao = DaoSingleton.getInstance().getPlainMirrorDao();
        plainMirrors = plainMirrorDao.queryBuilder().list();
        for (int i=0;i<plainMirrors.size();i++){
            if (plainMirrors.get(i).getWholestorge().equals(numType)) {
                recyclerAdapter = new RecyclerAdapter(getContext(), plainMirrors.get(i));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(recyclerAdapter);
                break;

            }
        }
      LogUtils.d("请求失败");
    }

    public static GoodsListFragment setUrl(int type, String title, String store) {
        GoodsListFragment goodsListFragment = new GoodsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("store", store);
        bundle.putString("type",type+"");
        goodsListFragment.setArguments(bundle);
        return goodsListFragment;
    }

}
