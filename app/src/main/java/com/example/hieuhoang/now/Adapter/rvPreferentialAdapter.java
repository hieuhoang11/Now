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


public class rvPreferentialAdapter extends RecyclerView.Adapter<rvPreferentialAdapter.PreferentialViewHolder> {
    private List<Store> mStores;
    private LayoutInflater mLayoutInflater;
    private Context context;

    public rvPreferentialAdapter(List<Store> mStores, Context context) {
        this.mStores = mStores;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context ;
    }

    @Override
    public PreferentialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.custom_preferential_store, parent, false);
        return new rvPreferentialAdapter.PreferentialViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PreferentialViewHolder holder,final int position) {
        Store store = mStores.get(position);
        holder.viewPreferential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iStore = new Intent(context, StoreActivity.class) ;
                iStore.putExtra(AppConstant.ID_STORE,mStores.get(position).getIdStore());
                context.startActivity(iStore);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStores.size();
    }

    class PreferentialViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPreferentialStoreName;
        private ImageView imgPreferentialStore;
        private TextView tvPreferentialStoreAddress;
        private LinearLayout viewPreferential;
        public PreferentialViewHolder(View itemView) {
            super(itemView);
            tvPreferentialStoreName = (TextView) itemView.findViewById(R.id.tvPreferentialStoreName);
            tvPreferentialStoreAddress = (TextView) itemView.findViewById(R.id.tvPreferentialStoreAddress);
            imgPreferentialStore = (ImageView) itemView.findViewById(R.id.imgPreferentialStore);
            viewPreferential= (LinearLayout) itemView.findViewById(R.id.viewPreferential);
        }
    }
}
