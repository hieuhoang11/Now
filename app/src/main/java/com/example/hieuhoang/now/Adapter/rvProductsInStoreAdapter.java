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
import com.example.hieuhoang.now.Model.ObjectClass.Product;
import com.example.hieuhoang.now.Presenter.Store.IPresenterStore;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Store.StoreActivity;

import java.util.List;
import java.util.Map;

public class rvProductsInStoreAdapter extends RecyclerView.Adapter<rvProductsInStoreAdapter.ProductsViewHolder> {
    private List<Product> mProducts;
    private LayoutInflater mLayoutInflater;
    private Context context;
    private IPresenterStore presenterLogicStore;
    private boolean isGrid;
    private StoreActivity storeActivity;
    private Map<String, Integer> map;
    private final int LAYOUT_LIST_PRODUCT = R.layout.custom_list_product_store;
    private final int LAYOUT_GRID_PRODUCT = R.layout.custom_grid_product_store;

    public rvProductsInStoreAdapter(List<Product> mProducts, Context context, StoreActivity storeActivity, IPresenterStore presenterLogicStore, boolean isGrid, Map<String, Integer> map) {
        this.mProducts = mProducts;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.isGrid = isGrid;
        this.presenterLogicStore = presenterLogicStore;
        this.map = map;
        this.storeActivity = storeActivity;
    }

    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        if (isGrid) layout = LAYOUT_GRID_PRODUCT;
        else layout = LAYOUT_LIST_PRODUCT;
        View itemView = mLayoutInflater.inflate(layout, parent, false);
        return new rvProductsInStoreAdapter.ProductsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductsViewHolder holder, int position) {
        final Product product = mProducts.get(position);
        holder.tvNameProduct.setText(product.getProductName());
        holder.tvNumberOfPurchases.setText(Common.qualityPurchased(product.getQuantityPurchase()));
        Common.loadImageFromServer(product.getImage(), context, holder.imgProduct);
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

        holder.btnSubtract.setVisibility(View.GONE);
        holder.tvQualityProductInCraftOrder.setVisibility(View.GONE);

        if (product.getQuantity() > 0) {
            holder.tvOutOfStock.setVisibility(View.GONE);
            holder.viewProductInStore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenterLogicStore.showSheetAddToCart(product);
                }
            });
            holder.btnSubtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenterLogicStore.getOrderDetail(storeActivity.getOrder().getIdOrder());
                }
            });

            holder.btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenterLogicStore.showSheetAddToCart(product);
                }
            });
            if (map != null) {
                if (map.get(product.getId()) != null) {
                    int value = map.get(product.getId());
                    holder.btnSubtract.setVisibility(View.VISIBLE);
                    holder.tvQualityProductInCraftOrder.setVisibility(View.VISIBLE);
                    holder.tvQualityProductInCraftOrder.setText(String.valueOf(value));
                }
            }
        } else {
            holder.btnPlus.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameProduct, tvProductPrice, tvNumberOfPurchases, tvDisCount, tvOutOfStock, tvQualityProductInCraftOrder;
        ImageView imgProduct, imgDisCount;
        View viewProductInStore;
        ImageButton btnPlus, btnSubtract;


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
            tvQualityProductInCraftOrder = itemView.findViewById(R.id.tvQualityProductInCraftOrder);
            btnSubtract = itemView.findViewById(R.id.btnSubtract);
            if (!isGrid)
                imgDisCount = itemView.findViewById(R.id.imgDisCount);
        }
    }

}
