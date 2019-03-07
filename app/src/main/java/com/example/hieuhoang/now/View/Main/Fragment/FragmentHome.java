package com.example.hieuhoang.now.View.Main.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hieuhoang.now.Adapter.rvHotAdapter;
import com.example.hieuhoang.now.Adapter.rvRecommendAdapter;
import com.example.hieuhoang.now.Model.ObjectClass.HotProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Presenter.Home.IPresenterHome;
import com.example.hieuhoang.now.Presenter.Home.PresenterLogicHome;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Service.ServiceFragment;
import com.example.hieuhoang.now.View.Home.ViewHome;
import com.example.hieuhoang.now.View.HotProduct.HotProductsActivity;
import java.util.List;

public class FragmentHome extends Fragment implements ViewHome,View.OnClickListener{
    private RecyclerView rvHotProductList ,rvRecommendProductList;
    private TextView tvReadMoreHot ,tvHot ;
    private ImageButton btnFood;
    private IPresenterHome presenterHome;
    private LinearLayout linearHot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home,container,false);

        presenterHome = new PresenterLogicHome(this);

        Mapping(view);

        presenterHome.loadListRecommendProduct();
        presenterHome.loadListHotProduct();
        return view;
    }

    private void Mapping(View view){
        rvHotProductList = view.findViewById(R.id.rvHotProductList);
        rvRecommendProductList= view.findViewById(R.id.rvRecommend);
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
            case R.id.tvReadMoreHot :
                Intent intent = new Intent(getContext(), HotProductsActivity.class) ;
                startActivity(intent);
                break;
            case R.id.btnFood :
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.mainHomeContainer, new ServiceFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }
    }


    @Override
    public void loadHotProducts(List<HotProduct> hotProducts) {

        //linearHot.setVisibility(View.VISIBLE);

        LinearLayoutManager hotProductManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvHotAdapter hotAdapter = new rvHotAdapter(hotProducts,getContext());
        rvHotProductList.setAdapter(hotAdapter);
        rvHotProductList.setLayoutManager(hotProductManager);
    }

    @Override
    public void loadRecommendStores(List<Store> recommendProducts) {

        LinearLayoutManager recommendManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvRecommendAdapter recommendAdapter = new rvRecommendAdapter(recommendProducts,getContext()) ;
        rvRecommendProductList.setAdapter(recommendAdapter);
        rvRecommendProductList.setLayoutManager(recommendManager);
    }

}