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

import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Store.StoreActivity;

import java.util.List;

/**
 * Created by Hieu Hoang on 24/02/2019.
 */

public class rvItemServiceAdapter extends RecyclerView.Adapter<rvItemServiceAdapter.ItemServiceViewHolder> {
    private List<Store> mStores;
    private LayoutInflater mLayoutInflater;
    private Context context ;

    public rvItemServiceAdapter(List<Store> mStores, Context context) {
        this.mStores = mStores;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context ;
    }

    public void setData(List<Store> mStores){
        this.mStores = mStores ;
    }

    @Override
    public ItemServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.custom_recyclerview_store_service, parent, false);
        return new rvItemServiceAdapter.ItemServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemServiceViewHolder holder,final int position) {
        holder.viewItemService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iStore = new Intent(context, StoreActivity.class) ;
                iStore.putExtra(AppConstant.ID_STORE,mStores.get(position).getID_Store());
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
        private TextView txtName;
        private TextView txtAddress;
        private TextView txtPromo;
        private TextView txtPrice;
private LinearLayout viewItemService;
        public ItemServiceViewHolder(View itemView) {
            super(itemView);
            imgStore = itemView.findViewById(R.id.imgItemService);
            txtName = itemView.findViewById(R.id.txtNameStoreItemService);
            txtAddress = itemView.findViewById(R.id.txtAddressStoreItemService);
            txtPromo = itemView.findViewById(R.id.txtPromotionStoreItemService);
            txtPrice = itemView.findViewById(R.id.txtPriceStoreItemService);
            viewItemService= itemView.findViewById(R.id.viewItemService);
        }
    }
}
