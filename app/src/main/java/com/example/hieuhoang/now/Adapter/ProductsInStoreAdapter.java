package com.example.hieuhoang.now.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hieuhoang.now.Common.Common;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Product;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Store.StoreActivity;

import java.util.List;

public class ProductsInStoreAdapter extends RecyclerView.Adapter<ProductsInStoreAdapter.ProductsViewHolder> {
    private List<Product> mProducts;
    private LayoutInflater mLayoutInflater;
    private Context context;
    private StoreActivity activity;
    private boolean isGrid;
    private final int LAYOUT_LIST_PRODUCT = R.layout.custom_list_product_store;
    private final int LAYOUT_GRID_PRODUCT = R.layout.custom_grid_product_store;

    public ProductsInStoreAdapter(List<Product> mProducts, Context context, StoreActivity activity, boolean isGrid) {
        this.mProducts = mProducts;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.isGrid = isGrid;
        this.activity = activity ;
    }

    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        if (isGrid) layout = LAYOUT_GRID_PRODUCT;
        else layout = LAYOUT_LIST_PRODUCT;
        View itemView = mLayoutInflater.inflate(layout, parent, false);
        return new ProductsInStoreAdapter.ProductsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductsViewHolder holder, int position) {
        final Product product = mProducts.get(position);
        holder.tvNameProduct.setText(product.getProductName());
        holder.tvNumberOfPurchases.setText(Common.qualityPurchased(product.getQuantityPurchase()));
        Common.loadImageFromInternet(AppConstant.SERVER_NAME_IMG + product.getImage(), context, holder.imgProduct);
        final String price = Common.formatNumber(product.getPrice());
        if (product.getDiscount() == 0) {
            holder.tvProductPrice.setText(price);
            holder.tvDisCount.setVisibility(View.GONE);
            if (!isGrid) holder.imgDisCount.setVisibility(View.GONE);
        } else {
            holder.tvProductPrice.setText(Common.oldPriceFormat(price));
            holder.tvDisCount.setText(Common.formatNumber(product.getDiscount()));
            holder.tvProductPrice.setTextColor(context.getResources().getColor(R.color.colorOldPrice));
        }


        if(product.getQuantity() > 0 ) {
            holder.tvOutOfStock.setVisibility(View.GONE);
            holder.viewProductInStore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.showBottomSheet(product);
                }
            });
        } else {
            holder.btnPlus.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameProduct, tvProductPrice, tvNumberOfPurchases, tvDisCount , tvOutOfStock ;
        ImageView imgProduct, imgDisCount;
        View viewProductInStore;
        ImageButton btnPlus;


        public ProductsViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvNumberOfPurchases = itemView.findViewById(R.id.tvNumberOfPurchases);
            tvDisCount = itemView.findViewById(R.id.tvDisCount);
            viewProductInStore = itemView.findViewById(R.id.viewProductInStore);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            tvOutOfStock = itemView.findViewById(R.id.tvOutOfStock);
            if (!isGrid)
                imgDisCount = itemView.findViewById(R.id.imgDisCount);
        }
    }
}
