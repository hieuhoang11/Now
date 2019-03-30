package com.example.hieuhoang.now.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hieuhoang.now.Util.Util;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Store.StoreActivity;

import java.util.List;


public class rvItemStoreAdapter extends RecyclerView.Adapter<rvItemStoreAdapter.ItemServiceViewHolder> {
    private List<Store> mStores;
    private LayoutInflater mLayoutInflater;
    private Context context;

    public rvItemStoreAdapter(List<Store> mStores, Context context) {
        this.mStores = mStores;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setData(List<Store> mStores) {
        this.mStores = mStores;
    }

    @Override
    public ItemServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.custom_recyclerview_item_store, parent, false);
        return new rvItemStoreAdapter.ItemServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemServiceViewHolder holder, final int position) {
        final Store store = mStores.get(position);
        holder.tvName.setText(store.getStoreName());
        holder.tvAddress.setText(store.getStoreAddress());
        Util.loadImageFromServer(store.getImage(),context,holder.imgStore);
        holder.viewItemStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iStore = new Intent(context, StoreActivity.class);
                iStore.putExtra(AppConstant.ID_STORE, store.getIdStore());
                context.startActivity(iStore);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStores.size();
    }


    class ItemServiceViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgStore;
        private TextView tvName;
        private TextView tvAddress;
        private TextView tvPromo;
        private TextView tvPrice;
        private LinearLayout viewItemStore;

        public ItemServiceViewHolder(View itemView) {
            super(itemView);
            imgStore = itemView.findViewById(R.id.imgItemService);
            tvName = itemView.findViewById(R.id.tvNameStoreItemService);
            tvAddress = itemView.findViewById(R.id.tvAddressStoreItemService);
            tvPromo = itemView.findViewById(R.id.tvPromotionStoreItemService);
            tvPrice = itemView.findViewById(R.id.tvPriceStoreItemService);
            viewItemStore = itemView.findViewById(R.id.viewItemStore);
        }
    }
}
