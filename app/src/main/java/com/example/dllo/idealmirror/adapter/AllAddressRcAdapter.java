package com.example.dllo.idealmirror.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.activity.AddAddressActivity;
import com.example.dllo.idealmirror.bean.Address;
import com.example.dllo.idealmirror.tool.ContentViewLinister;
import com.example.dllo.idealmirror.tool.SwipeLayout;

/**
 * Created by LYH on 16/3/31.
 */
public class AllAddressRcAdapter extends RecyclerView.Adapter<AllAddressRcAdapter.AllAddressViewHolder> {

    private Address data;
    private Context context;
    private AddressRcListener addListener;
    private RecyclerItemListener itemListener;
    private String token;

    public AllAddressRcAdapter(Address datas, Context context,String userToken) {
        this.data = datas;
        this.context = context;
        this.token = userToken;
    }

    /**
     * 删除接口
     * @param listener
     */
    public void getListener(AddressRcListener listener) {
        this.addListener = listener;
    }

    public interface AddressRcListener {
        void getData(String addr_id, int position);
    }

    /**
     * 设置默认地址接口
     * @param listener
     */
    public void RecyclerItem(RecyclerItemListener listener) {
        this.itemListener = listener;
    }

    public interface RecyclerItemListener {
        /**
         * 利用接口传递默认地址数据
         * @param addr     addr_id
         * @param position 位置
         */
        void getItemData(String addr, int position);

    }


    @Override
    public AllAddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_alladdress_item, parent, false);
        return new AllAddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllAddressViewHolder holder, final int position) {
        holder.receiverTv.setText(data.getData().getList().get(position).getUsername());
        holder.addressTv.setText(data.getData().getList().get(position).getAddr_info());
        holder.phoneNumberTv.setText(data.getData().getList().get(position).getCellphone());
        holder.position = position;
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddAddressActivity.class);
                intent.putExtra("nameTitle", context.getString(R.string.AllAddress_name));
                intent.putExtra("numTitle", context.getString(R.string.AllAddress_address));
                intent.putExtra("addTitle", context.getString(R.string.AllAddress_setaddress));
                intent.putExtra("title", context.getString(R.string.setmessage));
                intent.putExtra("btnText", context.getString(R.string.AllAddress_xijiao));
                intent.putExtra("name", data.getData().getList().get(position).getUsername());
                intent.putExtra("number", data.getData().getList().get(position).getCellphone());
                intent.putExtra("address", data.getData().getList().get(position).getAddr_info());
                intent.putExtra("addr_id", data.getData().getList().get(position).getAddr_id());
                intent.putExtra("token",token);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.getData().getList().size();
    }

    class AllAddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView receiverTv, addressTv, phoneNumberTv;
        private RelativeLayout deleteRl;
        private ImageView edit;
        private SwipeLayout addressLayout;
        private int position;

        public AllAddressViewHolder(View itemView) {
            super(itemView);
            receiverTv = (TextView) itemView.findViewById(R.id.all_address_receiver);
            addressTv = (TextView) itemView.findViewById(R.id.all_address_address);
            phoneNumberTv = (TextView) itemView.findViewById(R.id.all_address_phone);
            edit = (ImageView) itemView.findViewById(R.id.editing);
            addressLayout = (SwipeLayout) itemView.findViewById(R.id.all_address_layout);
            addressLayout.setContentListener(new ContentViewLinister() {
                @Override
                public void doSomeThing() {
                    itemListener.getItemData(data.getData().getList().get(position).getAddr_id(), position);

                }
            });
            deleteRl = (RelativeLayout) itemView.findViewById(R.id.all_address_delete);

            deleteRl.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            addListener.getData(data.getData().getList().get(position).getAddr_id(), position);
            notifyDataSetChanged();

        }
    }

}
