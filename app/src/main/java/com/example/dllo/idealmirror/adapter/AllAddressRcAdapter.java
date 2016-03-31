package com.example.dllo.idealmirror.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.bean.Address;

import java.util.List;

/**
 * Created by LYH on 16/3/31.
 */
public class AllAddressRcAdapter extends RecyclerView.Adapter<AllAddressRcAdapter.AllAddressViewHolder> {

    private List<Address> data;
    private int position;

    //添加假数据
    public void addData(List<Address> data){
        this.data = data;
        notifyDataSetChanged();//通知适配器，数据是实时更新的
    }

    @Override
    public AllAddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_alladdress_item,parent,false);
        return new AllAddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllAddressViewHolder holder, int position) {

        if (data != null && data.size() > 0){
            Address address = data.get(position);
            holder.addresseeTv.setText(address.getAddressee());
            holder.addressTv.setText(address.getAddress());
            holder.phoneNumberTv.setText(address.getPhoneNumber());
            holder.position = position;
        }
    }

    @Override
    public int getItemCount() {
        return data != null && data.size() > 0 ? data.size() : 0;
    }

    class AllAddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView addresseeTv,addressTv,phoneNumberTv;
        private RelativeLayout deleteRl;
        private LinearLayout line;
        private int position;

        public AllAddressViewHolder(View itemView) {
            super(itemView);
            addresseeTv = (TextView) itemView.findViewById(R.id.allAddress_addressee);
            addressTv = (TextView) itemView.findViewById(R.id.allAddress_address);
            phoneNumberTv = (TextView) itemView.findViewById(R.id.allAddress_phoneNumber);
            line = (LinearLayout) itemView.findViewById(R.id.allAddress_line);
            deleteRl = (RelativeLayout) itemView.findViewById(R.id.allAddress_delete);
            deleteRl.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            data.remove(position);
            notifyDataSetChanged();
        }
    }

}
