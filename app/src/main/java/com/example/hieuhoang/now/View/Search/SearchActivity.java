package com.example.hieuhoang.now.View.Search;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hieuhoang.now.Adapter.rvStoreAdapter;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.ILoadMore;
import com.example.hieuhoang.now.Model.ObjectClass.LoadMoreScroll;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Presenter.Search.IPresenterSearch;
import com.example.hieuhoang.now.Presenter.Search.PresenterLogicSearch;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.Util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements ViewSearch, View.OnKeyListener, ILoadMore, View.OnClickListener {
    private EditText edtSearch;
    private RecyclerView rvSearch;
    private TextView tvNoData;
    private ProgressBar progressBar;
    private Button btnService;
    private ImageView imgSearch;
    private SwipeRefreshLayout swipeRefresh;
    private ImageView imgBack;
    private IPresenterSearch presenterLogicSearch;
    private rvStoreAdapter adapter;
    private String idService;
    private List<Store> mStores;
    private Map<String,String> services ;
    private BottomSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Mapping();
        init();
        presenterLogicSearch = new PresenterLogicSearch(this);
    }

    private void Mapping() {
        edtSearch = findViewById(R.id.edtSearch);
        rvSearch = findViewById(R.id.rvSearch);
        tvNoData = findViewById(R.id.tvNoData);
        progressBar = findViewById(R.id.progressBar);
        btnService = findViewById(R.id.btnService);
        imgSearch = findViewById(R.id.imgSearch);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeResources(R.color.colorSwipe);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                search();
            }
        });
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        edtSearch.setOnKeyListener(this);
        btnService.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
    }

    private void init() {
        adapter = new rvStoreAdapter(new ArrayList<Store>(), SearchActivity.this);
        rvSearch.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvSearch.addOnScrollListener(new LoadMoreScroll(layoutManager, this));
        rvSearch.setLayoutManager(layoutManager);

        services = new HashMap<>( );
        services.put(AppConstant.ID_SERVICE_DRINK,getResources().getString(R.string.drinks)) ;
        services.put(AppConstant.ID_SERVICE_FOOD,getResources().getString(R.string.food)) ;
        services.put(AppConstant.ID_SERVICE_LIQUOR,getResources().getString(R.string.liquor)) ;
        services.put(AppConstant.ID_SERVICE_FLOWER,getResources().getString(R.string.flowers)) ;

        Intent intent = getIntent();
        idService = intent.getStringExtra(AppConstant.ID_SERVICE);
        if (idService == null)
            idService = AppConstant.ID_SERVICE_DEFAULT;

        btnService.setText(services.get(idService) ) ;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
            search(new ArrayList<Store>(), 0);
            return true;
        }
        return false;
    }

    private void search(List<Store> list, int start) {
        String condition = edtSearch.getText().toString().trim();
        if (condition.equals("")) {
            return;
        }
        condition = Util.standardizeString(condition);
        presenterLogicSearch.search(list, idService, condition, start);
    }

    private void search () {
        search (new ArrayList<Store>() , 0) ;
    }

    private void changeService (String idService) {
        if(!this.idService.equals(idService))
        {
            this.idService = idService ;
            btnService.setText(services.get(idService) ) ;
            search();
        }
        dialog.dismiss();
    }

    @Override
    public void displayListStore(List<Store> mStores) {
        adapter.setData(mStores);
        adapter.notifyDataSetChanged();

        rvSearch.setVisibility(View.VISIBLE);
        swipeRefresh.setVisibility(View.VISIBLE);
        tvNoData.setVisibility(View.GONE);
        dismissProcess();
        swipeRefresh.setRefreshing(false);
        this.mStores = mStores;
    }

    @Override
    public void noHasResult() {
        tvNoData.setVisibility(View.VISIBLE);
        swipeRefresh.setVisibility(View.GONE);
        rvSearch.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void dismissProcess() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void loadMore(int sumItem) {
        progressBar.setVisibility(View.VISIBLE);
        Log.i("kiemtra", "loadMore: ");
        search(this.mStores, sumItem);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnService:
                dialogService();
                break;
            case R.id.imgSearch:
                search();
                break;
            case R.id.imgBack:
                finish();
                break;
            case R.id.btnFood:
                changeService(AppConstant.ID_SERVICE_FOOD);
                break;
            case R.id.btnDrink:
                changeService(AppConstant.ID_SERVICE_DRINK);
                break;
            case R.id.btnFlower:
                changeService(AppConstant.ID_SERVICE_FLOWER);
                break;
            case R.id.btnLiquor:
                changeService(AppConstant.ID_SERVICE_LIQUOR);
                break;
        }
    }

    private void dialogService() {
        View view = getLayoutInflater().inflate(R.layout.custom_dialog_list_service, null);
        dialog = new BottomSheetDialog(this);
        (view.findViewById(R.id.btnFood)).setOnClickListener(this);
        (view.findViewById(R.id.btnDrink)).setOnClickListener(this);
        (view.findViewById(R.id.btnLiquor)).setOnClickListener(this);
        (view.findViewById(R.id.btnFlower)).setOnClickListener(this);
        dialog.setContentView(view);
        dialog.show();
    }
}
