package com.example.hieuhoang.now.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hieuhoang.now.Util.Util;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.HotProduct;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Store.StoreActivity;

import java.util.List;


public class rvHotAdapter extends RecyclerView.Adapter<rvHotAdapter.HotProductViewHolder> {
    private List<HotProduct> mHotProducts;
    private LayoutInflater mLayoutInflater;
    private int layout;
    private Context context;

    public rvHotAdapter(List<HotProduct> hotProducts, Context context) {
        this.mHotProducts = hotProducts;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.layout = R.layout.custom_hot_product;
        this.context = context;
    }

    public rvHotAdapter(List<HotProduct> hotProducts, Context context, int layout) {
        this.mHotProducts = hotProducts;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.layout = layout;
        this.context = context;
    }

    @Override
    public HotProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(this.layout, parent, false);
        return new HotProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HotProductViewHolder holder, int position) {
        final HotProduct product = mHotProducts.get(position);
        Util.loadImageFromServer( product.getImageProduct(), context,holder.imgHotProduct);
        holder.tvHotOldPrice.setText(Util.oldPriceFormat(Util.formatNumber(product.getOldPrice())));
        holder.tvHotNewPrice.setText(Util.formatNumber(product.getNewPrice()));
        holder.tvHotProductName.setText(product.getProductName());
        holder.tvHotQuality.setText(product.getDiscountNumber()+" giảm giá");
        holder.tvHotStoreName.setText(product.getStoreName());
        holder.viewHotProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iStore = new Intent(context, StoreActivity.class);
                iStore.putExtra(AppConstant.ID_STORE, product.getIdStore());
                context.startActivity(iStore);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHotProducts.size();
    }

    class HotProductViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHotStoreName;
        private TextView tvHotQuality;
        private TextView tvHotProductName;
        private TextView tvHotOldPrice;
        private TextView tvHotNewPrice;
        private View viewHotProduct;
        private ImageView imgHotProduct;

        public HotProductViewHolder(View itemView) {
            super(itemView);
            tvHotStoreName =  itemView.findViewById(R.id.hotStoreName);
            tvHotQuality =  itemView.findViewById(R.id.hotQuality);
            tvHotProductName =  itemView.findViewById(R.id.hotProductName);
            tvHotOldPrice =  itemView.findViewById(R.id.hotOldPrice);
            tvHotNewPrice =  itemView.findViewById(R.id.hotNewPrice);
            viewHotProduct = itemView.findViewById(R.id.viewHotProduct);
            imgHotProduct = itemView.findViewById(R.id.imgHotProduct);
        }
    }
    public void setData (List<HotProduct> mHotProducts) {
        this.mHotProducts = mHotProducts ;
    }
}
