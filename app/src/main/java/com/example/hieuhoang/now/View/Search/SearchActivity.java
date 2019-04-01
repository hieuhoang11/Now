package com.example.hieuhoang.now.View.Search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
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
import java.util.List;

public class SearchActivity extends AppCompatActivity implements ViewSearch, View.OnKeyListener,ILoadMore {
    private EditText edtSearch;
    private RecyclerView rvSearch;
    private TextView tvNoData;
    private ProgressBar progressBar;
    private IPresenterSearch presenterLogicSearch;
    private rvStoreAdapter adapter;
    private String idService = AppConstant.ID_SERVICE_DEFAULT ;
    private List<Store> mStores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Mapping();
        init() ;
        presenterLogicSearch = new PresenterLogicSearch(this);
    }

    private void Mapping() {
        edtSearch = findViewById(R.id.edtSearch);
        rvSearch = findViewById(R.id.rvSearch);
        tvNoData = findViewById(R.id.tvNoData) ;
        progressBar = findViewById(R.id.progressBar) ;
        edtSearch.setOnKeyListener(this);
    }

    private void init() {
        adapter = new rvStoreAdapter(new ArrayList<Store>(),SearchActivity.this);
        rvSearch.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext() , LinearLayoutManager.VERTICAL , false) ;
        rvSearch.addOnScrollListener(new LoadMoreScroll(layoutManager,this));
        rvSearch.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
            search(new ArrayList<Store>(),0);
            return true;
        }
        return false;
    }

    private void search(List<Store> list,int start) {
        String condition = edtSearch.getText().toString().trim() ;
        if(condition.equals("")) {
            return;
        }
        Log.i("kiemtra", "search: nè");
        condition = Util.standardizeString(condition) ;
        presenterLogicSearch.search(list ,idService,condition,start);
    }

    @Override
    public void displayListStore(List<Store> mStores) {
        Log.i("kiemtra", "displayListStore: " + mStores.size());
        adapter.setData(mStores);
        adapter.notifyDataSetChanged();

        rvSearch.setVisibility(View.VISIBLE);
        tvNoData.setVisibility(View.GONE);
        dismissProcess();
        this.mStores = mStores ;
    }

    @Override
    public void noHasResult() {
        tvNoData.setVisibility(View.VISIBLE);
        rvSearch.setVisibility(View.GONE);
    }

    @Override
    public void dismissProcess() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void loadMore(int sumItem) {
        progressBar.setVisibility(View.VISIBLE);
        Log.i("kiemtra", "loadMore: ");
        search(this.mStores , sumItem) ;
    }
}
