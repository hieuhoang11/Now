package com.example.hieuhoang.now.View.Main.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieuhoang.now.Adapter.rvHotAdapter;
import com.example.hieuhoang.now.Adapter.rvRecommendAdapter;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.Location.ModelLocation;
import com.example.hieuhoang.now.Model.ObjectClass.HotProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Presenter.Main.Home.IPresenterHome;
import com.example.hieuhoang.now.Presenter.Main.Home.PresenterLogicHome;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Locate.LocateActivity;
import com.example.hieuhoang.now.View.Location.LocationActivity;
import com.example.hieuhoang.now.View.Main.Home.Service.ServiceFragment;
import com.example.hieuhoang.now.View.HotProduct.HotProductsActivity;
import com.example.hieuhoang.now.View.Search.SearchActivity;
import com.example.hieuhoang.now.View.Store.StoreActivity;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;

import static android.app.Activity.RESULT_OK;

public class FragmentHome extends Fragment implements ViewHome, View.OnClickListener {
    private RecyclerView rvHotProductList, rvRecommendProductList;
    private TextView tvReadMoreHot, tvHot;
    private ImageButton btnFood, btnDrink, btnLiquor, btnFlower;
    private Button btnLocation, btnSearch;
    private CardView cardViewBanner;
    private IPresenterHome presenterHome;
    private String TAG = "kiemtra";
    private rvHotAdapter hotAdapter;
    private rvRecommendAdapter recommendAdapter;
    private final int REQUEST_CODE_LOCATION = 111;
    private FragmentManager fragmentManager;
    private BannerSlider bannerSlider;
    private final int REQUEST_CODE_LOCATE = 112;
    private String idService;
    private List<Store> mStore;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home, container, false);

        presenterHome = new PresenterLogicHome(this);

        Mapping(view);
        fragmentManager = getFragmentManager();
        LinearLayoutManager hotProductManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        hotAdapter = new rvHotAdapter(new ArrayList<HotProduct>(), getContext());
        rvHotProductList.setAdapter(hotAdapter);
        rvHotProductList.setLayoutManager(hotProductManager);

        LinearLayoutManager recommendManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recommendAdapter = new rvRecommendAdapter(new ArrayList<Store>(), getContext());
        rvRecommendProductList.setAdapter(recommendAdapter);
        rvRecommendProductList.setLayoutManager(recommendManager);

        presenterHome.loadBanner();
        presenterHome.loadListRecommendProduct();
        presenterHome.loadListHotProduct();

        return view;
    }

    private void Mapping(View view) {
        bannerSlider = view.findViewById(R.id.banner_slider);
        btnLocation = view.findViewById(R.id.btnLocation);
        btnSearch = view.findViewById(R.id.btnSearch);
        rvHotProductList = view.findViewById(R.id.rvHotProductList);
        rvRecommendProductList = view.findViewById(R.id.rvRecommend);
        btnFood = view.findViewById(R.id.btnFood);
        btnDrink = view.findViewById(R.id.btnDrink);
        btnLiquor = view.findViewById(R.id.btnLiquor);
        btnFlower = view.findViewById(R.id.btnFlower);
        cardViewBanner = view.findViewById(R.id.cardViewBanner);
        tvReadMoreHot = view.findViewById(R.id.tvReadMoreHot);

        tvReadMoreHot.setOnClickListener(this);
        btnFood.setOnClickListener(this);
        btnDrink.setOnClickListener(this);
        btnLiquor.setOnClickListener(this);
        btnFlower.setOnClickListener(this);

        btnLocation.setOnClickListener(this);
        btnSearch.setOnClickListener(this);


        bannerSlider.setOnBannerClickListener(new OnBannerClickListener() {
                                                  @Override
                                                  public void onClick(int position) {
                                                      Intent iStore = new Intent(getContext(),StoreActivity.class);
                                                      iStore.putExtra(AppConstant.ID_STORE,mStore.get(position).getIdStore());
                                                      startActivity(iStore);
                                                  }
                                              }
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvReadMoreHot:
                Intent intent = new Intent(getContext(), HotProductsActivity.class);
                startActivity(intent);
                break;
            case R.id.btnFood:
                addServiceFragment(AppConstant.ID_SERVICE_FOOD);
                break;
            case R.id.btnDrink:
                addServiceFragment(AppConstant.ID_SERVICE_DRINK);
                break;
            case R.id.btnLiquor:
                addServiceFragment(AppConstant.ID_SERVICE_LIQUOR);
                break;
            case R.id.btnFlower:
                addServiceFragment(AppConstant.ID_SERVICE_FLOWER);
                break;

            case R.id.btnLocation:
                Intent iLocation = new Intent(getContext(), LocationActivity.class);
                startActivityForResult(iLocation, REQUEST_CODE_LOCATION);
                break;
            case R.id.btnSearch:
                Intent iSearch = new Intent(getContext(), SearchActivity.class);
                startActivity(iSearch);
                break;
        }
    }

//    private void addServiceFragment(String idService) {
////        if (ModelLocation.isPlayServicesAvailable(getContext()) && ModelLocation.isGpsOn(getContext())) {
////            this.idService = idService;
////            Intent iLocate = new Intent(getContext(), LocateActivity.class);
////            startActivityForResult(iLocate, REQUEST_CODE_LOCATE);
////        } else
//            addServiceFragment(idService, -1, -1);
//
//    }

    private void addServiceFragment(String idService) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.ID_SERVICE, idService);
//        bundle.putDouble(AppConstant.LONGITUDE, longitude);
//        bundle.putDouble(AppConstant.LATITUDE, latitude);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ServiceFragment fragment = new ServiceFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.Home, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_LOCATE && resultCode == RESULT_OK && data != null) {
//            double longitude = data.getDoubleExtra(AppConstant.LONGITUDE, -1);
//            double latitude = data.getDoubleExtra(AppConstant.LATITUDE, -1);
//            addServiceFragment(idService, longitude, latitude);
//        }
//    }

    @Override
    public void loadHotProducts(List<HotProduct> hotProducts) {

        hotAdapter.setData(hotProducts);
        hotAdapter.notifyDataSetChanged();

    }

    @Override
    public void loadRecommendStores(List<Store> recommendProducts) {
        recommendAdapter.setData(recommendProducts);
        recommendAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadBanner(List<Store> mStore) {
        cardViewBanner.setVisibility(View.VISIBLE);
        List<Banner> banners = new ArrayList<>();

        //add banner using image url
        for (Store s : mStore) {
            banners.add(new RemoteBanner(AppConstant.SERVER_NAME_IMG + s.getImage()));
        }
        //add banner using resource drawable
        //banners.add(new DrawableBanner(R.drawable.yourDrawable));

        bannerSlider.setBanners(banners);
        this.mStore = mStore;
    }

    @Override
    public void disappearBanner() {
        cardViewBanner.setVisibility(View.GONE);
    }


}