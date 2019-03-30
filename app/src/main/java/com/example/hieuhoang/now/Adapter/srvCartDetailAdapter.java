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

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.hieuhoang.now.Util.Util;
import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;
import com.example.hieuhoang.now.Presenter.Store.IPresenterStore;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Store.ViewStore;

import java.util.List;

public class srvCartDetailAdapter extends RecyclerSwipeAdapter<srvCartDetailAdapter.DetailViewHolder> {
    private List<OrderDetail> mOrderDetails;
    private Context context;
    private IPresenterStore presenterLogicStore;
    private ViewStore storeActivity;

    public srvCartDetailAdapter(List<OrderDetail> mOrderDetails, Context context, ViewStore storeActivity, IPresenterStore presenterLogicStore) {
        this.mOrderDetails = mOrderDetails;
        this.context = context;
        this.presenterLogicStore = presenterLogicStore;
        this.storeActivity = storeActivity;
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_in_cart, parent, false);
        return new srvCartDetailAdapter.DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DetailViewHolder holder, int position) {
        final OrderDetail detail = mOrderDetails.get(position);
        holder.tvProductName.setText(detail.getProductName());
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

        holder.tvQuantity.setText(String.valueOf(detail.getQuantity()));
        String note = detail.getNote().equals("") ? context.getResources().getString(R.string.note) : detail.getNote();
        holder.tvNote.setText(note);

        holder.tvQualityProduct.setText(String.valueOf(detail.getQuantity()));

        holder.btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(holder.tvQualityProduct.getText().toString()) - 1;
                presenterLogicStore.updateQuantityProductInOrderDetail(detail.getIdOrder(), detail.getIdProduct(), q);
            }
        });

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(holder.tvQualityProduct.getText().toString()) + 1;
                if (presenterLogicStore.isEnoughItems(detail.getIdOrder(), detail.getIdProduct(), q)) {
                    presenterLogicStore.updateQuantityProductInOrderDetail(detail.getIdOrder(), detail.getIdProduct(), q);
                } else {
                    Toast.makeText(context, R.string.msg_out_of_stock, Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.tvNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeActivity.showSheetEditNote(detail.getIdProduct(), detail.getNote());
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeActivity.deleteItemOrderDetail(detail.getIdOrder(), detail.getIdProduct());
            }
        });
        mItemManger.bindView(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mOrderDetails.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeOrderDetail;
    }

    class DetailViewHolder extends RecyclerView.ViewHolder {
        private TextView tvProductName, tvNote, tvQualityProduct, tvOldPrice, tvNewPrice, tvTotalMoney, tvQuantity, tvDelete;
        private ImageView img;
        private ImageButton btnSubtract, btnAdd;

        public DetailViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductNameInCartDetail);
            tvOldPrice = itemView.findViewById(R.id.tvOldPriceInCartDetail);
            tvNewPrice = itemView.findViewById(R.id.tvNewPriceInCartDetail);
            img = itemView.findViewById(R.id.imgInCartDetail);
            tvTotalMoney = itemView.findViewById(R.id.tvTotalMoneyInCartDetail);
            btnSubtract = itemView.findViewById(R.id.btnSubtractInCartDetail);
            tvQuantity = itemView.findViewById(R.id.tvQuantityInCartDetail);
            btnAdd = itemView.findViewById(R.id.btnAddInCartDetail);
            tvNote = itemView.findViewById(R.id.tvNoteInCartDetail);
            tvQualityProduct = itemView.findViewById(R.id.tvQualityProductInCartDetail);
            tvDelete = itemView.findViewById(R.id.tvDelete);
        }
    }

    public void setData(List<OrderDetail> mOrderDetails) {
        this.mOrderDetails = mOrderDetails;
    }


}
