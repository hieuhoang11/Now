package com.example.hieuhoang.now.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hieuhoang.now.Util.Util;
import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;
import com.example.hieuhoang.now.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class rvOrderDetailAdapter extends RecyclerView.Adapter<rvOrderDetailAdapter.SubmitViewHolder> {
    private List<OrderDetail> detailList;
    private Context context;
    private Map<String, Integer> map;
    private int BACK_GROUND_COLOR_WARNING;

    public rvOrderDetailAdapter(List<OrderDetail> detailList, Context context) {
        this.detailList = detailList;
        this.context = context;
        map = new HashMap<>();
        BACK_GROUND_COLOR_WARNING = context.getResources().getColor(R.color.colorWarning);
    }

    @Override
    public SubmitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_order_detail, parent, false);
        return new SubmitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubmitViewHolder holder, int position) {
        OrderDetail detail = detailList.get(position);
        holder.tvProductName.setText(detail.getProductName());
        holder.tvNote.setText(detail.getNote());
        holder.tvQuality.setText(String.valueOf(detail.getQuantity()));
        String note = detail.getNote().equals("") ? context.getResources().getString(R.string.note) : detail.getNote();
        holder.tvNote.setText(note);

        String price = Util.formatNumber(detail.getProductPrice());

        float totalMoney = 0;
        if (detail.getDisCount() != 0) {
            holder.tvNewPrice.setText(Util.formatNumber(detail.getDisCount()));
            holder.tvOldPrice.setText(Util.oldPriceFormat(price));
            totalMoney = detail.getQuantity() * detail.getDisCount();
        } else {
            holder.tvNewPrice.setVisibility(View.GONE);
            holder.img.setVisibility(View.GONE);
            holder.tvOldPrice.setText(price);
            holder.tvOldPrice.setTextColor(context.getResources().getColor(R.color.colorBlack));
            totalMoney = detail.getQuantity() * detail.getProductPrice();
        }
        holder.tvTotalMoney.setText(Util.formatNumber(totalMoney));
        if (map.get(detail.getIdProduct()) != null) {
            holder.viewItem.setBackgroundColor(BACK_GROUND_COLOR_WARNING);
            holder.viewQuantity.setVisibility(View.VISIBLE);
            holder.tvQuantityAvailable.setText(String.valueOf(map.get(detail.getIdProduct())));
        } else {
            holder.viewQuantity.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    class SubmitViewHolder extends RecyclerView.ViewHolder {
        private TextView tvProductName, tvNote, tvOldPrice, tvNewPrice, tvQuality, tvTotalMoney, tvQuantityAvailable;
        private ImageView img;
        private View viewItem, viewQuantity;

        public SubmitViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvNote = itemView.findViewById(R.id.tvNote);
            tvOldPrice = itemView.findViewById(R.id.tvOldPrice);
            tvNewPrice = itemView.findViewById(R.id.tvNewPrice);
            tvQuality = itemView.findViewById(R.id.tvQuality);
            tvTotalMoney = itemView.findViewById(R.id.tvTotalMoney);
            img = itemView.findViewById(R.id.img);
            viewItem = itemView.findViewById(R.id.viewItem);
            tvQuantityAvailable = itemView.findViewById(R.id.tvQuantityAvailable);
            viewQuantity = itemView.findViewById(R.id.viewQuantity);
        }
    }

    public void setData(List<OrderDetail> detailList) {
        this.detailList = detailList;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }
}
