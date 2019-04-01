package com.example.hieuhoang.now.View.Brand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.hieuhoang.now.Adapter.rvStoreAdapter;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Presenter.Brand.IPresenterBrand;
import com.example.hieuhoang.now.Presenter.Brand.PresenterLogicBrand;
import com.example.hieuhoang.now.R;

import java.util.ArrayList;
import java.util.List;

public class BrandActivity extends AppCompatActivity implements ViewBrand {
    private IPresenterBrand presenterLogicBrand;
    private rvStoreAdapter adapter;
    private RecyclerView rvListStore;
    private TextView tvBrandName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        Mapping();
        Intent intent = getIntent();
        String idBrand = intent.getStringExtra(AppConstant.ID_BRAND);

        adapter = new rvStoreAdapter(new ArrayList<Store>(), BrandActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(BrandActivity.this, LinearLayoutManager.VERTICAL, false);
        rvListStore.setAdapter(adapter);
        rvListStore.setLayoutManager(layoutManager);

        presenterLogicBrand = new PresenterLogicBrand(this);
        presenterLogicBrand.getListStore(idBrand);
        presenterLogicBrand.getBrandName(idBrand);
    }

    private void Mapping() {
        rvListStore = findViewById(R.id.rvListStore) ;
        tvBrandName = findViewById(R.id.tvBrandName) ;
    }

    @Override
    public void loadListStore(List<Store> mStores) {
        adapter.setData(mStores);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setBrandName(String brandName) {
        tvBrandName.setText(brandName);
    }
}
