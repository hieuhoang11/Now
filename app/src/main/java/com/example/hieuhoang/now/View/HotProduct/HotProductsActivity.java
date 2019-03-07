package com.example.hieuhoang.now.View.HotProduct;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.example.hieuhoang.now.Adapter.rvHotAdapter;
import com.example.hieuhoang.now.Model.ObjectClass.HotProduct;
import com.example.hieuhoang.now.Presenter.HotProduct.IPresenterHotProduct;
import com.example.hieuhoang.now.Presenter.HotProduct.PresenterLogicHotProduct;
import com.example.hieuhoang.now.R;

import java.util.List;

public class HotProductsActivity extends AppCompatActivity implements ViewHotProduct,View.OnClickListener{

    private RecyclerView rvHotProducts ;
    //private List<HotProduct> listHotProducts ;
    private ImageButton btnBackHotProducts ;
    private IPresenterHotProduct presenterHotProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_products);


        rvHotProducts = findViewById(R.id.rvHotProducts);

        btnBackHotProducts= findViewById(R.id.btnBackHotProducts);
        btnBackHotProducts.setOnClickListener(this);
        presenterHotProduct = new PresenterLogicHotProduct(this);
        presenterHotProduct.loadListHotProduct();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackHotProducts :
                finish();
                break;
        }
    }

    @Override
    public void loadHotProducts(List<HotProduct> productList) {
        rvHotAdapter hotAdapter = new rvHotAdapter(productList,HotProductsActivity.this,R.layout.custom_hot_product_grid_2);
        LinearLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        rvHotProducts.setAdapter(hotAdapter);
        rvHotProducts.setLayoutManager(layoutManager);
    }
}
