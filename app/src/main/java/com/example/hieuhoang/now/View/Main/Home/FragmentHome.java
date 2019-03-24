package com.example.hieuhoang.now.View.Main.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hieuhoang.now.Adapter.rvHotAdapter;
import com.example.hieuhoang.now.Adapter.rvRecommendAdapter;
import com.example.hieuhoang.now.Model.Location.ModelLocation;
import com.example.hieuhoang.now.Model.ObjectClass.HotProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Presenter.Main.Home.IPresenterHome;
import com.example.hieuhoang.now.Presenter.Main.Home.PresenterLogicHome;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Main.Home.Service.ServiceFragment;
import com.example.hieuhoang.now.View.HotProduct.HotProductsActivity;
import com.example.hieuhoang.now.View.Main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment implements ViewHome, View.OnClickListener {
    private RecyclerView rvHotProductList, rvRecommendProductList;
    private TextView tvReadMoreHot, tvHot;
    private ImageButton btnFood;
    private IPresenterHome presenterHome;
    private LinearLayout linearHot;
    private String TAG = "kiemtra";
    private rvHotAdapter hotAdapter;
    private rvRecommendAdapter recommendAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home, container, false);

        presenterHome = new PresenterLogicHome(this);

        Mapping(view);

        LinearLayoutManager hotProductManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        hotAdapter = new rvHotAdapter(new ArrayList<HotProduct>(), getContext());
        rvHotProductList.setAdapter(hotAdapter);
        rvHotProductList.setLayoutManager(hotProductManager);

        LinearLayoutManager recommendManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recommendAdapter = new rvRecommendAdapter(new ArrayList<Store>(), getContext());
        rvRecommendProductList.setAdapter(recommendAdapter);
        rvRecommendProductList.setLayoutManager(recommendManager);

        Log.i(TAG, "onCreateView: Home 1");
        presenterHome.loadListRecommendProduct();
        presenterHome.loadListHotProduct();
        Log.i(TAG, "onCreateView: Home");
        return view;
    }

    private void Mapping(View view) {
        rvHotProductList = view.findViewById(R.id.rvHotProductList);
        rvRecommendProductList = view.findViewById(R.id.rvRecommend);
        btnFood = view.findViewById(R.id.btnFood);
        //  linearHot = view.findViewById(R.id.linearHot);
        //  linearHot.setVisibility(View.GONE);
//        tvHot  = view.findViewById(R.id.tvHot);
        tvReadMoreHot = view.findViewById(R.id.tvReadMoreHot);
//
//        tvHot.setVisibility(View.GONE);
//        tvReadMoreHot.setVisibility(View.GONE);
//        rvHotProductList.setVisibility(View.GONE);

        tvReadMoreHot.setOnClickListener(this);
        btnFood.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvReadMoreHot:
                Intent intent = new Intent(getContext(), HotProductsActivity.class);
                startActivity(intent);
                break;
            case R.id.btnFood:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.Home, new ServiceFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }
    }


    @Override
    public void loadHotProducts(List<HotProduct> hotProducts) {

        //linearHot.setVisibility(View.VISIBLE);
        hotAdapter.setData(hotProducts);
        hotAdapter.notifyDataSetChanged();

    }

    @Override
    public void loadRecommendStores(List<Store> recommendProducts) {
        recommendAdapter.setData(recommendProducts);
        recommendAdapter.notifyDataSetChanged();
    }

}