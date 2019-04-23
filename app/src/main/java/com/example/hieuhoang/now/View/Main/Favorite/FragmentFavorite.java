package com.example.hieuhoang.now.View.Main.Favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.hieuhoang.now.Adapter.rvStoreAdapter;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Presenter.Main.Favorite.IPresenterFavorite;
import com.example.hieuhoang.now.Presenter.Main.Favorite.PresenterLogicFavorite;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.LoginRegister.LoginRegisterActivity;
import com.example.hieuhoang.now.View.Search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentFavorite extends Fragment implements ViewFavorite, View.OnClickListener {
    private Button btnLogin;
    private SwipeRefreshLayout swipeRefresh;
    private ImageButton btnSearch;
    private RecyclerView rvFavorite;
    private View viewLoginToContinue, viewNoData;
    private IPresenterFavorite presenterLogicFavorite;
    private rvStoreAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_favorite, container, false);
        Mapping(view);
        presenterLogicFavorite = new PresenterLogicFavorite(this, getContext());
        adapter = new rvStoreAdapter(new ArrayList<Store>(), getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvFavorite.setAdapter(adapter);
        rvFavorite.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenterLogicFavorite.getListFavorite();
    }

    private void Mapping(View view) {
        rvFavorite = view.findViewById(R.id.rvFavorite);
        btnLogin = view.findViewById(R.id.btnLogin);
        viewLoginToContinue = view.findViewById(R.id.viewLoginToContinue);
        viewNoData = view.findViewById(R.id.viewNoData);
        btnSearch = view.findViewById(R.id.btnSearch);
        swipeRefresh = view.findViewById(R.id.swipeRefresh) ;
        swipeRefresh.setColorSchemeResources(R.color.colorSwipe) ;
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onStart();
            }
        });
        btnLogin.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                Intent iLogin = new Intent(getContext(), LoginRegisterActivity.class);
                startActivity(iLogin);
                break;
            case R.id.btnSearch:
                Intent iSearch = new Intent(getContext(), SearchActivity.class);
                startActivity(iSearch);
                break;
        }
    }

    @Override
    public void noLogged() {
        viewLoginToContinue.setVisibility(View.VISIBLE);
        rvFavorite.setVisibility(View.GONE);
        viewNoData.setVisibility(View.GONE);
        swipeRefresh.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void loadListFavorite(List<Store> list) {
        viewLoginToContinue.setVisibility(View.GONE);
        rvFavorite.setVisibility(View.VISIBLE);
        viewNoData.setVisibility(View.VISIBLE);
        adapter.setData(list);
        adapter.notifyDataSetChanged();
        swipeRefresh.setVisibility(View.VISIBLE);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void noData() {
        viewLoginToContinue.setVisibility(View.GONE);
        rvFavorite.setVisibility(View.GONE);
        viewNoData.setVisibility(View.VISIBLE);
        swipeRefresh.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(false);
    }
}
