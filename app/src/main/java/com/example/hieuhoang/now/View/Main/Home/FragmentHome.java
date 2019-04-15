package com.example.hieuhoang.now.View.Main.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hieuhoang.now.Adapter.rvHotAdapter;
import com.example.hieuhoang.now.Adapter.rvRecommendAdapter;
import com.example.hieuhoang.now.Adapter.rvSliderAdapter;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.Location.ModelLocation;
import com.example.hieuhoang.now.Model.ObjectClass.HotProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Presenter.Main.Home.IPresenterHome;
import com.example.hieuhoang.now.Presenter.Main.Home.PresenterLogicHome;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.Util.Util;
import com.example.hieuhoang.now.View.Location.LocationActivity;
import com.example.hieuhoang.now.View.Main.Home.Service.ServiceFragment;
import com.example.hieuhoang.now.View.HotProduct.HotProductsActivity;
import com.example.hieuhoang.now.View.Main.MainActivity;
import com.example.hieuhoang.now.View.Search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment implements ViewHome, View.OnClickListener, ViewPager.OnPageChangeListener {
    private RecyclerView rvHotProductList, rvRecommendProductList;
    private TextView tvReadMoreHot, tvHot, tvDots[];
    private ImageButton btnFood, btnDrink, btnLiquor, btnFlower;
    private Button btnLocation, btnSearch;
    private LinearLayout layoutDots;
    private ViewPager slider;
    private IPresenterHome presenterHome;
    private LinearLayout linearHot;
    private String TAG = "kiemtra";
    private rvHotAdapter hotAdapter;
    private rvRecommendAdapter recommendAdapter;
    private final int REQUEST_CODE_LOCATION = 111;
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> listFragment;

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

        presenterHome.loadListRecommendProduct();
        presenterHome.loadListHotProduct();

        String[] str = {"anhcuahang/images3.jpg", "anhcuahang/images35.jpg", "anhcuahang/images30.jpg", "anhcuahang/images39.jpg", "anhcuahang/images39.jpg"};
        loadSlider(str);
        addDotsSlider(0);
        return view;
    }

    private void Mapping(View view) {

        btnLocation = view.findViewById(R.id.btnLocation);
        btnSearch = view.findViewById(R.id.btnSearch);
        slider = view.findViewById(R.id.slider);
        slider.addOnPageChangeListener(this);
        rvHotProductList = view.findViewById(R.id.rvHotProductList);
        rvRecommendProductList = view.findViewById(R.id.rvRecommend);
        btnFood = view.findViewById(R.id.btnFood);
        btnDrink = view.findViewById(R.id.btnDrink);
        btnLiquor = view.findViewById(R.id.btnLiquor);
        btnFlower = view.findViewById(R.id.btnFlower);
        layoutDots = view.findViewById(R.id.layoutDots);
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
        btnDrink.setOnClickListener(this);
        btnLiquor.setOnClickListener(this);
        btnFlower.setOnClickListener(this);

        btnLocation.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
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

    private void addServiceFragment(String idService) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.ID_SERVICE, idService);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ServiceFragment fragment = new ServiceFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.Home, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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

    @Override
    public void loadSlider(String[] images) {
        listFragment = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {
            Fragment fragment = new FragmentSlider();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.IMAGE, images[i]);
            // bundle.putInt("rr",i);
            fragment.setArguments(bundle);
            listFragment.add(fragment);

        }

        rvSliderAdapter adapter = new rvSliderAdapter(getFragmentManager(), listFragment);
        slider.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void addDotsSlider(int index) {
        layoutDots.removeAllViews();
        tvDots = new TextView[listFragment.size()];
        for (int i = 0; i < listFragment.size(); i++) {
            tvDots[i].setTextSize(20);
            tvDots[i] = new TextView(getContext());
            tvDots[i].setText(Html.fromHtml("&#8226;"));
            tvDots[i].setTextColor(Util.getIdColor(getContext(), R.color.colorDotInActive));
            tvDots[i].setTextSize(AppConstant.DOT_SIZE);
            layoutDots.addView(tvDots[i]);
        }
        tvDots[index].setTextColor(Util.getIdColor(getContext(), R.color.colorDotActive));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        addDotsSlider(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}