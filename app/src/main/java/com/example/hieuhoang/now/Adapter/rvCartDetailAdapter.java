package com.example.hieuhoang.now.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieuhoang.now.Common.Common;
import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;
import com.example.hieuhoang.now.Presenter.Store.IPresenterStore;
import com.example.hieuhoang.now.R;

import java.util.List;

public class rvCartDetailAdapter extends RecyclerView.Adapter<rvCartDetailAdapter.DetailViewHolder> {
    private List<OrderDetail> mOrderDetails;
    private LayoutInflater mLayoutInflater;
    private Context context;
    private IPresenterStore presenterLogicStore ;

    public rvCartDetailAdapter(List<OrderDetail> mOrderDetails, Context context,IPresenterStore presenterLogicStore) {
        this.mOrderDetails = mOrderDetails;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.presenterLogicStore = presenterLogicStore ;
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.custom_item_in_cart, parent, false);
        return new rvCartDetailAdapter.DetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DetailViewHolder holder, int position) {
        final OrderDetail detail = mOrderDetails.get(position);
        holder.tvProductNameInCartDetail.setText(detail.getProductName());
        String price = Common.formatNumber(detail.getProductPrice());
        float totalMoney = detail.getQuantity() ;
        if(detail.getDisCount() != 0){
            holder.tvNewPriceInCartDetail.setText(Common.formatNumber(detail.getDisCount()));
            holder.tvOldPriceInCartDetail.setText(Common.oldPriceFormat(price));
            holder.tvOldPriceInCartDetail.setTextColor(context.getResources().getColor(R.color.colorOldPrice));
            totalMoney *= detail.getDisCount() ;
        } else {
            holder.tvNewPriceInCartDetail.setVisibility(View.GONE);
            holder.imgInCartDetail.setVisibility(View.GONE);
            holder.tvOldPriceInCartDetail.setText(price);
            totalMoney *= detail.getProductPrice() ;
        }

        holder.tvQuantityInCartDetail.setText(String.valueOf(detail.getQuantity()));
        String note = detail.getNote().equals("") ? context.getResources().getString(R.string.note) : detail.getNote();
        holder.tvNoteInCartDetail.setText(note);
        holder.tvTotalMoneyInCartDetail.setText(Common.formatNumber(totalMoney));
        holder.tvQualityProductInCartDetail.setText(String.valueOf(detail.getQuantity()));

        holder.btnSubtractInCartDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(holder.tvQualityProductInCartDetail.getText().toString()) -1;
                presenterLogicStore.updateQuantityProductInOrderDetail(detail.getIdOrder(),detail.getIdProduct() , q);
            }
        });

        holder.btnAddInCartDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(holder.tvQualityProductInCartDetail.getText().toString()) +1;
                if(presenterLogicStore.isEnoughItems(detail.getIdOrder() , detail.getIdProduct(),q)) {
                    presenterLogicStore.updateQuantityProductInOrderDetail(detail.getIdOrder(),detail.getIdProduct() , q);
                }
                else {
                    Toast.makeText(context, R.string.msg_out_of_stock, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mOrderDetails.size();
    }

    class DetailViewHolder extends RecyclerView.ViewHolder {
        private TextView tvProductNameInCartDetail, tvNoteInCartDetail,tvQualityProductInCartDetail ,tvOldPriceInCartDetail, tvNewPriceInCartDetail, tvTotalMoneyInCartDetail, tvQuantityInCartDetail;
        private ImageView imgInCartDetail;
        private ImageButton btnSubtractInCartDetail, btnAddInCartDetail;

        public DetailViewHolder(View itemView) {
            super(itemView);
            tvProductNameInCartDetail = itemView.findViewById(R.id.tvProductNameInCartDetail);
            tvOldPriceInCartDetail = itemView.findViewById(R.id.tvOldPriceInCartDetail);
            tvNewPriceInCartDetail = itemView.findViewById(R.id.tvNewPriceInCartDetail);
            imgInCartDetail = itemView.findViewById(R.id.imgInCartDetail);
            tvTotalMoneyInCartDetail = itemView.findViewById(R.id.tvTotalMoneyInCartDetail);
            btnSubtractInCartDetail = itemView.findViewById(R.id.btnSubtractInCartDetail);
            tvQuantityInCartDetail = itemView.findViewById(R.id.tvQuantityInCartDetail);
            btnAddInCartDetail = itemView.findViewById(R.id.btnAddInCartDetail);
            tvNoteInCartDetail = itemView.findViewById(R.id.tvNoteInCartDetail);
            tvQualityProductInCartDetail= itemView.findViewById(R.id.tvQualityProductInCartDetail);
        }
    }
}
