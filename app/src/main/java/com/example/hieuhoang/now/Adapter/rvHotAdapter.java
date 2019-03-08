package com.example.hieuhoang.now.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hieuhoang.now.Common.Common;
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
        this.layout = R.layout.custom_recyclerview_hot;
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
        Common.loadImageFromInternet(AppConstant.SERVER_NAME_IMG + product.getImageProduct(), context,holder.imgHotProduct);
        holder.tvHotOldPrice.setText(Common.oldPriceFormat(product.getOldPrice()+"đ"));
        holder.tvHotNewPrice.setText(product.getNewPrice()+"đ");
        holder.tvHotProductName.setText(product.getProductName());
        holder.tvHotQuality.setText(product.getQuantityDiscount()+" giảm giá");
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
        private LinearLayout viewHotProduct;
        private ImageView imgHotProduct;

        public HotProductViewHolder(View itemView) {
            super(itemView);
            tvHotStoreName = (TextView) itemView.findViewById(R.id.hotStoreName);
            tvHotQuality = (TextView) itemView.findViewById(R.id.hotQuality);
            tvHotProductName = (TextView) itemView.findViewById(R.id.hotProductName);
            tvHotOldPrice = (TextView) itemView.findViewById(R.id.hotOldPrice);
            tvHotNewPrice = (TextView) itemView.findViewById(R.id.hotNewPrice);
            viewHotProduct = (LinearLayout) itemView.findViewById(R.id.viewHotProduct);
            imgHotProduct = (ImageView) itemView.findViewById(R.id.imgHotProduct);
        }
    }
}