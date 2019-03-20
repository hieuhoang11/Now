package com.example.hieuhoang.now.View.Store.InfoStore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hieuhoang.now.Common.Common;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Presenter.Store.InformationStore.IPresenterInfoStore;
import com.example.hieuhoang.now.Presenter.Store.InformationStore.PresenterLogicInfoStore;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Brand.BrandActivity;
import com.example.hieuhoang.now.View.Store.StoreActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InfoStoreFragment extends Fragment implements ViewInfoStore, OnMapReadyCallback {
    private Button btnQuantityBranch;
    private Store store;
    private IPresenterInfoStore presenterLogicInfoStore;
    GoogleMap map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_info_store, container, false);
        store = ((StoreActivity) getActivity()).getStore();

        Mapping(view);

        presenterLogicInfoStore = new PresenterLogicInfoStore(this);
        presenterLogicInfoStore.getQuantityBranch(store.getIdBrand());

        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    private void Mapping(View view) {
        btnQuantityBranch = view.findViewById(R.id.btnQuantityBranch);
        btnQuantityBranch.setVisibility(View.GONE);
        btnQuantityBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iBrand = new Intent(getContext(), BrandActivity.class);
                iBrand.putExtra(AppConstant.ID_BRAND, store.getIdBrand());
                startActivity(iBrand);
            }
        });
    }

    @Override
    public void showQuantityBranch(int quantity) {
        btnQuantityBranch.setVisibility(View.VISIBLE);
        btnQuantityBranch.setText(String.valueOf(quantity) + " " + getActivity().getResources().getString(R.string.branch));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng storeLocation = Common.getCoordinates(getContext(), store.getStoreAddress());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(storeLocation, 17));
        map.addMarker(new MarkerOptions()
                .title(store.getStoreName())
                .snippet(store.getStoreAddress())
                .position(storeLocation)
        );
    }
}
