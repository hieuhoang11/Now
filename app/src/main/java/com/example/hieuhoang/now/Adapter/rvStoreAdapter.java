package com.example.hieuhoang.now.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hieuhoang.now.Model.ObjectClass.GroupProduct;
import com.example.hieuhoang.now.Presenter.Store.IPresenterStore;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Store.StoreActivity;

import java.util.List;
import java.util.Map;


public class rvStoreAdapter extends RecyclerView.Adapter<rvStoreAdapter.StoreViewHolder> {
    private List<GroupProduct> mGroupProducts;
    private LayoutInflater mLayoutInflater;
    private IPresenterStore presenterLogicStore ;
    private Context context;
    private int visible[];
    private int visibleText[];
    private boolean isGrid;
    private Map<String,Integer> map =null;
    private StoreActivity storeActivity ;

    public rvStoreAdapter(List<GroupProduct> mGroupProducts, Context context, StoreActivity storeActivity, IPresenterStore presenterLogicStore, boolean isGrid) {
        this.mGroupProducts = mGroupProducts;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.isGrid = isGrid;
        this.presenterLogicStore = presenterLogicStore ;
        this.storeActivity = storeActivity;
    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.custom_recyclerview_group_product, parent, false);
        return new rvStoreAdapter.StoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StoreViewHolder holder, int position) {
        GroupProduct groupProduct = mGroupProducts.get(position);
        holder.tvDanhMuc.setText(groupProduct.getNameGroup());
        holder.tvQualityProduct.setText(String.valueOf(groupProduct.getListProducts().size())+ " " + context.getResources().getString(R.string.item));
        LinearLayoutManager layoutManager;
        if (isGrid) {
            layoutManager = new GridLayoutManager(context, 2);
            holder.rvProductsInStore.setBackgroundColor(context.getResources().getColor(R.color.colorGridView));
        } else {
            layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            holder.rvProductsInStore.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }
        rvProductsInStoreAdapter adapter = new rvProductsInStoreAdapter(groupProduct.getListProducts(), context,  storeActivity,presenterLogicStore,isGrid,map);
        holder.rvProductsInStore.setAdapter(adapter);
        holder.rvProductsInStore.setLayoutManager(layoutManager);

        holder.linearDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideRecyclerView(holder.rvProductsInStore, holder.tvQualityProduct, holder.getAdapterPosition());
            }
        });
        holder.btnDanhmucStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideRecyclerView(holder.rvProductsInStore, holder.tvQualityProduct, holder.getAdapterPosition());
            }
        });
        holder.rvProductsInStore.setVisibility(visible[position]);
        holder.tvQualityProduct.setVisibility(visibleText[position]);
    }

    @Override
    public int getItemCount() {
        return mGroupProducts.size();
    }

    class StoreViewHolder extends RecyclerView.ViewHolder {
        private ImageButton btnDanhmucStore;
        private RecyclerView rvProductsInStore;
        private LinearLayout linearDanhMuc;
        private TextView tvQualityProduct, tvDanhMuc;

        private StoreViewHolder(View itemView) {
            super(itemView);
            btnDanhmucStore = itemView.findViewById(R.id.btnDanhmucStore);
            rvProductsInStore = itemView.findViewById(R.id.rvProductsInStore);
            linearDanhMuc = itemView.findViewById(R.id.linearDanhMuc);
            tvDanhMuc = itemView.findViewById(R.id.tvDanhMuc);
            tvQualityProduct = itemView.findViewById(R.id.tvQualityProduct);
        }
    }

    private void hideRecyclerView(RecyclerView recyclerView, TextView textView, int position) {
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            visible[position] = View.GONE;
            visibleText[position] = View.VISIBLE;
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            visible[position] = View.VISIBLE;
            visibleText[position] = View.GONE;
        }
    }

    public void setIsGrid(boolean b) {
        this.isGrid = b;
    }

    public void setHashMap(Map<String,Integer> map) {
        this.map = map ;
    }
    public void setData (List<GroupProduct> mGroupProducts) {
        this.mGroupProducts = mGroupProducts ;
        visible = new int[mGroupProducts.size()];
        visibleText = new int[mGroupProducts.size()];
        int l = visible.length;
        for (int i = 0; i < l; i++) {
            visible[i] = View.VISIBLE;
            visibleText[i] = View.INVISIBLE;
        }
    }

}
