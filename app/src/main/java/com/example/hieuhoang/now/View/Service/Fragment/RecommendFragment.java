package com.example.hieuhoang.now.View.Service.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hieuhoang.now.Adapter.rvItemServiceAdapter;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Presenter.Service.IPresenterFragmentService;
import com.example.hieuhoang.now.Presenter.Service.PresenterLogicFragmentService;
import com.example.hieuhoang.now.R;

import java.util.List;

public class RecommendFragment extends Fragment implements ViewFragment {

    RecyclerView rvItemFragmentService;
    IPresenterFragmentService presenterLogicFragmentService ;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_recommend_service, container, false);

        rvItemFragmentService = view.findViewById(R.id.rvItemFragmentService);

        presenterLogicFragmentService = new PresenterLogicFragmentService(this);
        presenterLogicFragmentService.loadRecommendStores();
        return view;
    }

    @Override
    public void loadProducts(List<Store> mStores) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rvItemServiceAdapter hotAdapter = new rvItemServiceAdapter(mStores,getActivity().getApplicationContext());
        rvItemFragmentService.setAdapter(hotAdapter);
        rvItemFragmentService.setLayoutManager(layoutManager);
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        getParentFragment().onDestroy();
//
//    }
}