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
import com.example.hieuhoang.now.Util.Util;
import com.example.hieuhoang.now.View.Store.StoreActivity;

import java.util.List;

public class rvRecommendAdapter extends RecyclerView.Adapter<rvRecommendAdapter.RecommendProductViewHolder> {
    private List<Store> mStores;
    private LayoutInflater mLayoutInflater;
    private Context context;

    public rvRecommendAdapter(List<Store> mStores, Context context) {
        this.mStores = mStores;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public RecommendProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.custom_recyclerview_recommend, parent, false);
        return new rvRecommendAdapter.RecommendProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecommendProductViewHolder holder, final int position) {
        final Store store = mStores.get(position);
        holder.viewRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iStore = new Intent(context, StoreActivity.class);
                iStore.putExtra(AppConstant.ID_STORE, store.getIdStore());
                context.startActivity(iStore);
            }
        });
        Util.loadImageStore(store.getImage(), context, holder.imgRecommend);
        holder.tvStoreName.setText(store.getStoreName());
        if(store.isPromo()){
            holder.tvPromo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mStores.size();
    }


    class RecommendProductViewHolder extends RecyclerView.ViewHolder {
        private TextView tvStoreName;
        private TextView tvPromo;
        private ImageView imgRecommend;
        private LinearLayout viewRecommend;

        public RecommendProductViewHolder(final View itemView) {
            super(itemView);
            tvPromo =  itemView.findViewById(R.id.tvPromo);
            tvStoreName =  itemView.findViewById(R.id.tvStoreName);
            imgRecommend =  itemView.findViewById(R.id.imgRecommend);
            viewRecommend = itemView.findViewById(R.id.viewRecommend);

        }
    }

    public void setData(List<Store> mStores) {
        this.mStores = mStores;
    }
}
