package com.example.hieuhoang.now.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.hieuhoang.now.Common.Common;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Store.StoreActivity;

import java.util.List;


public class rvDraftOrderBillAdapter extends RecyclerView.Adapter<rvDraftOrderBillAdapter.DraftOrderViewHolder>{
    List<Order> mOrders;
    Context context ;
    LayoutInflater layoutInflater ;

    public rvDraftOrderBillAdapter(List<Order> mOrders, Context context) {
        this.mOrders = mOrders;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData (List<Order> mOrders) {
        this.mOrders = mOrders ;
    }

    @Override
    public DraftOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.custom_draft_order_in_bill, parent, false);
        return new rvDraftOrderBillAdapter.DraftOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DraftOrderViewHolder holder, int position) {
        final Order order = mOrders.get(position) ;
        Common.loadImageFromInternet(AppConstant.SERVER_NAME_IMG + order.getStoreImage() , context , holder.imgStore);
        holder.tvStoreName.setText(order.getStoreName());
        holder.tvStoreAddress.setText(order.getStoreAddress());
        holder.tvQuantity.setText(String.valueOf(order.getQuantityProduct()));
        holder.tvTotalMoney.setText(Common.formatNumber(order.getTotalMoney()));

        holder.viewDraftOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iStore = new Intent(context, StoreActivity.class) ;
                iStore.putExtra(AppConstant.ID_STORE,order.getIdStore()) ;
                context.startActivity(iStore);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    class DraftOrderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgStore;
        private TextView tvStoreName,tvStoreAddress,tvQuantity,tvTotalMoney ;
        View viewDraftOrder;
        public DraftOrderViewHolder(View itemView) {
            super(itemView);
            imgStore = itemView.findViewById(R.id.imgStore);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvStoreAddress = itemView.findViewById(R.id.tvStoreAddress);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotalMoney = itemView.findViewById(R.id.tvTotalMoney);
            viewDraftOrder= itemView.findViewById(R.id.viewDraftOrder);
        }
    }
}
