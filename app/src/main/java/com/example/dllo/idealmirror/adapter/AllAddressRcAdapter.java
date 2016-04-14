package com.example.dllo.idealmirror.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.activity.AddAddressActivity;
import com.example.dllo.idealmirror.bean.Address;
import com.example.dllo.idealmirror.tool.LogUtils;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

/**
 * Created by LYH on 16/3/31.
 */
public class AllAddressRcAdapter extends RecyclerView.Adapter<AllAddressRcAdapter.AllAddressViewHolder> {

    private Address data;
    private Context context;
    private ReclcleListrner listrner;
    private RecyclerItemLinstener relistener;
    public AllAddressRcAdapter(Address datas,Context context) {
        this.data = datas;
        this.context = context;
    }

    /**
     * 删除接口
     * @param listrners
     */
    public void getListener(ReclcleListrner listrners)
    {
        this.listrner = listrners;
    }

    public interface ReclcleListrner{
        void getData(String addr_id,int position);
    }

    /**
     * 设置默认地址接口
     * @param relisteners
     */
    public void RecyclerItem(RecyclerItemLinstener relisteners){
        this.relistener = relisteners;
    }

    public interface RecyclerItemLinstener{
        void getItemData(String addr);
    }



    @Override
    public AllAddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_alladdress_item,parent,false);
        return new AllAddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllAddressViewHolder holder, final int position) {



            holder.addresseeTv.setText(data.getData().getList().get(position).getUsername());
            holder.addressTv.setText(data.getData().getList().get(position).getAddr_info());
            holder.phoneNumberTv.setText(data.getData().getList().get(position).getCellphone());
            holder.position = position;
            holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddAddressActivity.class);
                intent.putExtra("nametitie", "编辑收件人姓名");
                intent.putExtra("numtitle", "编辑联系人电话号码");
                intent.putExtra("addtitle", "编辑收货地址");
                intent.putExtra("title","编辑地址");
                intent.putExtra("btntext","提交修改");
                intent.putExtra("name", data.getData().getList().get(position).getUsername());
                intent.putExtra("number",data.getData().getList().get(position).getCellphone());
                intent.putExtra("address",data.getData().getList().get(position).getAddr_info());
                intent.putExtra("addr_id",data.getData().getList().get(position).getAddr_id());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.getData().getList().size();
    }

    class AllAddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView addresseeTv,addressTv,phoneNumberTv;
        private RelativeLayout deleteRl;
        private AutoRelativeLayout setaddress;
        private ImageView line;
        private int position;

        public AllAddressViewHolder(View itemView) {
            super(itemView);
            addresseeTv = (TextView) itemView.findViewById(R.id.allAddress_addressee);
            addressTv = (TextView) itemView.findViewById(R.id.allAddress_address);
            phoneNumberTv = (TextView) itemView.findViewById(R.id.allAddress_phoneNumber);
            line = (ImageView) itemView.findViewById(R.id.editing);
           // setaddress = (AutoRelativeLayout) itemView.findViewById(R.id.additem);
           // setaddress.setOnClickListener(this);
            deleteRl = (RelativeLayout) itemView.findViewById(R.id.allAddress_delete);
            deleteRl.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

                    listrner.getData(data.getData().getList().get(position).getAddr_id(),position);
                    notifyDataSetChanged();

               /* case R.id.additem:
                    relistener.getitemData(data.getData().getList().get(position).getAddr_id());
                    break;*/


        }
    }

}
