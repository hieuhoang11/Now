package com.example.hieuhoang.now.View.Main.Home.Service;


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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.hieuhoang.now.Adapter.rvHotAdapter;
import com.example.hieuhoang.now.Adapter.rvStoreAdapter;
import com.example.hieuhoang.now.Adapter.rvPreferentialAdapter;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.HotProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Presenter.Service.PresenterLogicService;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.Util.Util;
import com.example.hieuhoang.now.View.Search.SearchActivity;
import java.util.List;


public class ServiceFragment extends Fragment implements ViewService, View.OnClickListener {
    private RecyclerView rvPreferentialService, rvHotProductService, rvItemService;
    private TextView txtNewItemService, txtNearbyItemService, txtJustOrderItemService, txtRecommendItemService,tvService;
    private Button btnSearch;
    private ImageButton btnBackService ;
    private PresenterLogicService presenterLogicService;
    private rvStoreAdapter adapter;
    private String idService;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_service, container, false);

        Mapping(view);
        init();

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
        btnSearch = view.findViewById(R.id.btnSearch) ;
        tvService = view.findViewById(R.id.tvService) ;

        btnSearch.setOnClickListener(this);
        btnBackService.setOnClickListener(this);
        txtRecommendItemService.setOnClickListener(this);
        txtJustOrderItemService.setOnClickListener(this);
        txtNewItemService.setOnClickListener(this);
        txtNearbyItemService.setOnClickListener(this);
        txtRecommendItemService.setTextColor(Util.getIdColor(getContext() , R.color.colorTextTabSelected));
    }

    private void init () {
        Bundle bundle = getArguments();
        if (bundle != null) {
            idService = bundle.getString(AppConstant.ID_SERVICE);
        }
        if(idService == null)
            idService = AppConstant.ID_SERVICE_DEFAULT ;
        String serviceName ="";
        switch(idService) {
            case AppConstant.ID_SERVICE_FOOD :
                serviceName = getResources().getString(R.string.food) ;
                break;
            case AppConstant.ID_SERVICE_DRINK :
                serviceName = getResources().getString(R.string.drinks) ;
                break;
            case AppConstant.ID_SERVICE_FLOWER :
                serviceName = getResources().getString(R.string.flowers) ;
                break;
            case AppConstant.ID_SERVICE_LIQUOR :
                serviceName = getResources().getString(R.string.liquor) ;
                break;
        }

        tvService.setText(serviceName) ;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new rvStoreAdapter(null, getContext());
        rvItemService.setAdapter(adapter);
        rvItemService.setLayoutManager(layoutManager);

        presenterLogicService = new PresenterLogicService(this);
        presenterLogicService.loadHotProducts();
        presenterLogicService.loadPreferential();
        presenterLogicService.loadRecommendStores();
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
                presenterLogicService.loadJustOrderStores(idService);
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
            case R.id.btnBackService:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(this);
                fragmentTransaction.commit();
                break;
            case R.id.btnSearch:
                Intent iSearch = new Intent(getContext(), SearchActivity.class) ;
                iSearch.putExtra(AppConstant.ID_SERVICE , idService) ;
                startActivity(iSearch);
                break;
        }
    }

    public void setTextColor(TextView txtChecked) {
        int colorBlack = Util.getIdColor(getContext(),R.color.colorBlack);
        txtJustOrderItemService.setTextColor(colorBlack);
        txtNewItemService.setTextColor(colorBlack);
        txtNearbyItemService.setTextColor(colorBlack);
        txtRecommendItemService.setTextColor(colorBlack);
        txtChecked.setTextColor(Util.getIdColor(getContext(),R.color.colorTextTabSelected));
    }
}
