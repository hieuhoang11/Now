package com.example.hieuhoang.now.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hieuhoang.now.Common.Common;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Presenter.Main.Bill.OnGoing.IPresenterOnGoing;
import com.example.hieuhoang.now.R;

import java.util.List;

public class rvOnGoingOrderAdapter extends RecyclerView.Adapter<rvOnGoingOrderAdapter.OrderViewHolder> {
    private String TAG = "kiemtra";
    private List<Order> mOrders;
    private Context context;
    private IPresenterOnGoing presenterLogic;

    public rvOnGoingOrderAdapter(Context context, List<Order> mOrders, IPresenterOnGoing presenterLogic) {
        this.mOrders = mOrders;
        this.context = context ;
        this.presenterLogic = presenterLogic;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_ongoing_order_in_bill, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        final Order order = mOrders.get(position);
        Common.loadImageFromServer(order.getStoreImage(), context, holder.imgStore);
        holder.tvStoreName.setText(order.getStoreName());
        holder.tvStoreAddress.setText(order.getStoreAddress());
        holder.tvQuantity.setText(String.valueOf(order.getQuantityProduct()));
        holder.tvTotalMoney.setText(Common.formatNumber(order.getTotalMoney()));
        holder.viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterLogic.getDetailOrderStatus(order.getIdOrder());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgStore;
        private TextView tvStoreName, tvStoreAddress, tvQuantity, tvTotalMoney;
        private View viewOrder;

        private OrderViewHolder(View itemView) {
            super(itemView);
            imgStore = itemView.findViewById(R.id.imgStore);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvStoreAddress = itemView.findViewById(R.id.tvStoreAddress);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotalMoney = itemView.findViewById(R.id.tvTotalMoney);
            viewOrder = itemView.findViewById(R.id.viewOrder);
        }
    }
    public void setData (List<Order> mOrders) {
        this.mOrders = mOrders ;
    }
}
