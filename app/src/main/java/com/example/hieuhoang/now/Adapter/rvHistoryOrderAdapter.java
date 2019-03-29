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
import com.example.hieuhoang.now.Presenter.Main.Bill.History.IPresenterHistoryOrder;
import com.example.hieuhoang.now.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class rvHistoryOrderAdapter extends RecyclerView.Adapter<rvHistoryOrderAdapter.OrderViewHolder> {

    private String TAG = "kiemtra";
    private List<Order> mOrders;
    private Context context;
    private IPresenterHistoryOrder presenterLogic;

    public rvHistoryOrderAdapter(Context context, List<Order> mOrders, IPresenterHistoryOrder presenterLogic) {
        this.context = context;
        this.mOrders = mOrders;
        this.presenterLogic = presenterLogic;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_history_order_in_bill, parent, false);
        return new rvHistoryOrderAdapter.OrderViewHolder(view);
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
        holder.tvTime.setText(formatDate(order.getTime()));
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgStore;
        private TextView tvStoreName, tvStoreAddress, tvQuantity, tvTotalMoney, tvTime;
        private View viewOrder;

        private OrderViewHolder(View itemView) {
            super(itemView);
            imgStore = itemView.findViewById(R.id.imgStore);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvStoreAddress = itemView.findViewById(R.id.tvStoreAddress);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotalMoney = itemView.findViewById(R.id.tvTotalMoney);
            viewOrder = itemView.findViewById(R.id.viewOrder);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }

    public void setData(List<Order> mOrders) {
        this.mOrders = mOrders;
    }

    private String formatDate(String date) {
        String[] time = date.split("-");
        int d = Integer.parseInt(time[2]);
        int m = Integer.parseInt(time[1]);
        int y = Integer.parseInt(time[0]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(y, m - 1, d);
        return Common.formatDate(calendar.getTime());
    }

}
