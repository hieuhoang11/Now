package com.example.hieuhoang.now.View.Store;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieuhoang.now.Adapter.StoreAdapter;
import com.example.hieuhoang.now.Common.Common;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.GroupProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Product;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Presenter.Store.IPresenterStore;
import com.example.hieuhoang.now.Presenter.Store.PresenterLogicStore;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.LoginRegister.LoginRegisterActivity;

import java.util.List;

public class StoreActivity extends AppCompatActivity implements ViewStore, View.OnClickListener, View.OnLongClickListener {
    IPresenterStore presenterStore;
    TextView tvNameOfStore, tvAddressOfStore, tvNameProductBottomSheet, tvOldPriceBottomSheet, tvNewPriceBottomSheet, tvQuantityPurchasedBottomSheet;
    ImageView imgStore, imgProductBottomSheet, imgBottomSheet;
    ImageButton btnSearchStore, btnShowGrid, btnShowList, btnSubtract, btnAdd;
    TextView tvQuality, tvTotal;
    RecyclerView rvStore;
    LinearLayout linearSearch;
    EditText edtSearchInStore;
    View bottom_sheet_add_to_cart, content, bottom_sheet;
    AppBarLayout appBarLayout;
    private boolean isSearch = false;
    private StoreAdapter adapter;
    public BottomSheetBehavior sheetBehavior, sheetBehaviorAddToCart;

    ImageButton btnCloseBottomSheet;
    private int peekHeight = 0;
    // private int qualityProduct = 0;
    private Product product;
    private Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        Mapping();
        Intent intent = getIntent();
        String idStore = intent.getStringExtra(AppConstant.ID_STORE);
        presenterStore = new PresenterLogicStore(this,getApplicationContext());
        presenterStore.getStoreByID(idStore);
        presenterStore.getListProduct(idStore);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setTitle(store.getStoreName());
        }

        // bottom sheet
        bottom_sheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        //sheetBehavior.setPeekHeight(0);
        bottom_sheet.setVisibility(View.GONE);

        sheetBehaviorAddToCart = BottomSheetBehavior.from(bottom_sheet_add_to_cart);
        peekHeight = sheetBehaviorAddToCart.getPeekHeight();
        //sheetBehaviorAddToCart.setPeekHeight(0);
        bottom_sheet_add_to_cart.setVisibility(View.GONE);

    }

    private void Mapping() {
        appBarLayout = findViewById(R.id.appBar);
        content = findViewById(R.id.content);
        tvNameOfStore = findViewById(R.id.tvNameOfStore);
        tvAddressOfStore = findViewById(R.id.tvAddressOfStore);
        imgStore = findViewById(R.id.imgStore);
        btnSearchStore = findViewById(R.id.btnSearchStore);
        //epListProduct= findViewById(R.id.epListProduct);
        rvStore = findViewById(R.id.rvStore);
        edtSearchInStore = findViewById(R.id.edtSearchInStore);
        btnShowGrid = findViewById(R.id.btnShowGrid);
        btnShowList = findViewById(R.id.btnShowList);
        linearSearch = findViewById(R.id.linearSearch);
        linearSearch.setVisibility(View.GONE);
        appBarLayout.setOnClickListener(this);
        appBarLayout.setOnLongClickListener(this);
        content.setOnClickListener(this);
        content.setOnLongClickListener(this);
        //bottom sheet
        imgProductBottomSheet = findViewById(R.id.imgProductBottomSheet);
        btnCloseBottomSheet = findViewById(R.id.btnCloseBottomSheet);
        tvNameProductBottomSheet = findViewById(R.id.tvNameProductBottomSheet);
        tvOldPriceBottomSheet = findViewById(R.id.tvOldPriceBottomSheet);
        tvNewPriceBottomSheet = findViewById(R.id.tvNewPriceBottomSheet);
        imgBottomSheet = findViewById(R.id.imgBottomSheet);
        tvQuantityPurchasedBottomSheet = findViewById(R.id.tvQuantityPurchasedBottomSheet);
        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);
        tvQuality = findViewById(R.id.tvQuality);
        bottom_sheet_add_to_cart = findViewById(R.id.bottom_sheet_add_to_cart);
        tvTotal = findViewById(R.id.tvTotal);
        bottom_sheet = findViewById(R.id.bottom_sheet);

        btnCloseBottomSheet.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSubtract.setOnClickListener(this);
        tvQuality.setOnClickListener(this);
        bottom_sheet_add_to_cart.setOnClickListener(this);

        //
        btnSearchStore.setOnClickListener(this);
        btnShowGrid.setOnClickListener(this);
        btnShowList.setOnClickListener(this);
    }

    @Override
    public void loadInformationStore(Store store) {
        this.store = store ;
        tvNameOfStore.setText(store.getStoreName());
        tvAddressOfStore.setText(store.getStoreAddress());
        Common.loadImageFromInternet(AppConstant.SERVER_NAME_IMG + store.getImage(), getApplicationContext(), imgStore);
    }

    @Override
    public void loadListProduct(List<GroupProduct> mGroupProducts , boolean isGrid) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new StoreAdapter(mGroupProducts, getApplicationContext(), this, isGrid);
        rvStore.setAdapter(adapter);
        rvStore.setLayoutManager(layoutManager);
    }

    @Override
    public void addToCartSuccess () {
        presenterStore.addProductsToCart();
    }

    @Override
    public void showBottomSheet() {
        sheetBehavior.setPeekHeight(900);

        //sheetBehaviorAddToCart.setPeekHeight(peekHeight);
        bottom_sheet_add_to_cart.setVisibility(View.VISIBLE);
        bottom_sheet.setVisibility(View.VISIBLE);
        content.animate().alpha((float) 0.3).start();
        appBarLayout.animate().alpha((float) 0.3).start();

        tvQuality.setText(String.valueOf(1));
        Common.loadImageFromInternet(AppConstant.SERVER_NAME_IMG + product.getImage().trim(), getApplicationContext(), imgProductBottomSheet);
        tvNameProductBottomSheet.setText(product.getNameProduct());
        String oldPriceStr = Common.formatNumber(product.getPrice());
        if (product.getDiscount() != 0) {
            tvNewPriceBottomSheet.setVisibility(View.VISIBLE);
            String disCount = Common.formatNumber(product.getDiscount());
            tvNewPriceBottomSheet.setText(disCount);
            tvOldPriceBottomSheet.setText(Common.oldPriceFormat(oldPriceStr));
            imgBottomSheet.setVisibility(View.VISIBLE);
            tvTotal.setText(disCount);
        } else {
            tvTotal.setText(oldPriceStr);
            tvOldPriceBottomSheet.setText(oldPriceStr);
            imgBottomSheet.setVisibility(View.GONE);
            tvNewPriceBottomSheet.setVisibility(View.GONE);
        }

        tvQuantityPurchasedBottomSheet.setText(Common.qualityPurchased(product.getQualityPurchase()));
    }

    @Override
    public void startLoginActivity() {
        Intent iLogin = new Intent(StoreActivity.this, LoginRegisterActivity.class) ;
        startActivity(iLogin);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearchStore:
                if (isSearch) {
                    linearSearch.setVisibility(View.GONE);
                    edtSearchInStore.setText("");
                } else linearSearch.setVisibility(View.VISIBLE);
                isSearch = !isSearch;
                break;
            case R.id.btnShowGrid:
                presenterStore.setIsGrid(true) ;
                adapter.setIsGrid(true);
                adapter.notifyDataSetChanged();
                break;
            case R.id.btnShowList:
                presenterStore.setIsGrid(false) ;
                adapter.setIsGrid(false);
                adapter.notifyDataSetChanged();
                break;
            case R.id.btnCloseBottomSheet:
                closeBottomSheet();
                break;
            case R.id.btnAdd:
                int a = Integer.parseInt(tvQuality.getText().toString()) + 1;
                if (a > this.product.getQuality()) {
                    Toast.makeText(this.getApplicationContext(), R.string.msg_out_of_stock, Toast.LENGTH_SHORT).show();
                } else {
                    tvQuality.setText(String.valueOf(a));
                    tvTotal.setText(Common.formatNumber(totalMoney(a)));
                }
                break;
            case R.id.btnSubtract:
                int s = Integer.parseInt(tvQuality.getText().toString()) - 1;
                if (s >= 1) {
                    tvQuality.setText(String.valueOf(s));
                    tvTotal.setText(Common.formatNumber(totalMoney(s)));
                }
                break;
            case R.id.bottom_sheet_add_to_cart:
                addToCart();
                break;
            case R.id.content:
            case R.id.appBar:
                Log.i("kiemtra", "onClick: ");
                closeBottomSheet();
                break;
        }
    }

    private float totalMoney(int quality) {
        return product.getDiscount() == 0 ? quality * product.getPrice() : quality * product.getDiscount();
    }

    private void addToCart() {
        this.presenterStore.addProductsToCart();
    }

    private void closeBottomSheet() {
        Log.i("kiemtra", "closeBottomSheet: ");
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        sheetBehavior.setPeekHeight(0);
//        sheetBehaviorAddToCart.setPeekHeight(0);
        bottom_sheet_add_to_cart.setVisibility(View.GONE);
        bottom_sheet.setVisibility(View.GONE);

        content.animate().alpha((float) 1.0).start();
        appBarLayout.animate().alpha((float) 1.0).start();
    }

    public void showBottomSheet(Product product) {
        this.product = product;
        presenterStore.showBottomSheet();
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.content:
            case R.id.appBar:
                Log.i("kiemtra", "onClick: ");
                closeBottomSheet();
                break;
        }
        return true;
    }
}
