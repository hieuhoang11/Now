package com.example.hieuhoang.now.View.Service;


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

import android.widget.Button;
import android.widget.TextView;

import com.example.hieuhoang.now.Adapter.rvHotAdapter;
import com.example.hieuhoang.now.Adapter.rvItemServiceAdapter;
import com.example.hieuhoang.now.Adapter.rvPreferentialAdapter;
import com.example.hieuhoang.now.Model.ObjectClass.HotProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Presenter.Service.PresenterLogicService;
import com.example.hieuhoang.now.R;

import java.util.List;


public class ServiceFragment extends Fragment implements ViewService, View.OnClickListener {
    private RecyclerView rvPreferentialService, rvHotProductService, rvItemService;
    private TextView txtNewItemService, txtNearbyItemService, txtJustOrderItemService, txtRecommendItemService;
    Button btnBackService;
    private PresenterLogicService presenterLogicService;
    LinearLayoutManager layoutManager;
    private rvItemServiceAdapter adapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.abc_test, container, false);

        Mapping(view);

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new rvItemServiceAdapter(null, getContext());
        rvItemService.setAdapter(adapter);
        rvItemService.setLayoutManager(layoutManager);

        presenterLogicService = new PresenterLogicService(this);
        presenterLogicService.loadHotProducts();
        presenterLogicService.loadPreferential();
        presenterLogicService.loadRecommendStores();

        return view;
    }

    private void Mapping(View view) {
        rvPreferentialService = view.findViewById(R.id.rvPreferentialService);
        rvHotProductService = view.findViewById(R.id.rvHotProductService);
        rvItemService = view.findViewById(R.id.rvItemService);
        txtNewItemService = view.findViewById(R.id.txtNewItemService);
        txtNearbyItemService = view.findViewById(R.id.txtNearbyItemService);
        txtJustOrderItemService = view.findViewById(R.id.txtJustOrderItemService);
        txtRecommendItemService = view.findViewById(R.id.txtRecommendItemService);
        btnBackService = view.findViewById(R.id.btnBackService);

        btnBackService.setOnClickListener(this);
        txtRecommendItemService.setOnClickListener(this);
        txtJustOrderItemService.setOnClickListener(this);
        txtNewItemService.setOnClickListener(this);
        txtNearbyItemService.setOnClickListener(this);
        txtRecommendItemService.setTextColor(getResources().getColor(R.color.colorTextTabSelected));
    }

    @Override
    public void loadPreferential(List<Store> mStores) {
        LinearLayoutManager preferentialManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvPreferentialAdapter hotAdapter = new rvPreferentialAdapter(mStores, getContext());
        rvPreferentialService.setAdapter(hotAdapter);
        rvPreferentialService.setLayoutManager(preferentialManager);
    }

    @Override
    public void loadHotProduct(List<HotProduct> mHotProducts) {
        LinearLayoutManager hotProductManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvHotAdapter hotAdapter = new rvHotAdapter(mHotProducts, getContext());
        rvHotProductService.setAdapter(hotAdapter);
        rvHotProductService.setLayoutManager(hotProductManager);

    }

    public void dataChanged(List<Store> mStores) {
        adapter.setData(mStores);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadRecommendStore(List<Store> mStores) {
        dataChanged(mStores);
    }

    @Override
    public void loadJustOrderStore(List<Store> mStores) {
        dataChanged(mStores);
    }

    @Override
    public void loadNearByStore(List<Store> mStores) {
        dataChanged(mStores);
    }

    @Override
    public void loadNewStore(List<Store> mStores) {
        dataChanged(mStores);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtJustOrderItemService:
                setTextColor(txtJustOrderItemService);
                presenterLogicService.loadJustOrderStores();
                break;
            case R.id.txtNewItemService:
                setTextColor(txtNewItemService);
                presenterLogicService.loadNewStores();
                break;
            case R.id.txtNearbyItemService:
                setTextColor(txtNearbyItemService);
                presenterLogicService.loadNearbyStores();
                break;
            case R.id.txtRecommendItemService:
                setTextColor(txtRecommendItemService);
                presenterLogicService.loadRecommendStores();
                break;
            case R.id.btnBackService :
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(this);
                fragmentTransaction.commit();
                break;
        }
    }

    public void setTextColor(TextView txtChecked) {
        txtJustOrderItemService.setTextColor(getResources().getColor(R.color.colorBlack));
        txtNewItemService.setTextColor(getResources().getColor(R.color.colorBlack));
        txtNearbyItemService.setTextColor(getResources().getColor(R.color.colorBlack));
        txtRecommendItemService.setTextColor(getResources().getColor(R.color.colorBlack));
        txtChecked.setTextColor(getResources().getColor(R.color.colorTextTabSelected));
    }
}
