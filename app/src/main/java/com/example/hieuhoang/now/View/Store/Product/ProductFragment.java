package com.example.hieuhoang.now.View.Store.Product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.hieuhoang.now.Adapter.rvGroupProductAdapter;
import com.example.hieuhoang.now.Model.ObjectClass.GroupProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.Product;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Presenter.Store.ListProduct.IPresenterListProductInStore;
import com.example.hieuhoang.now.Presenter.Store.ListProduct.PresenterLogicListProductInStore;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.Util.Util;
import com.example.hieuhoang.now.View.Store.StoreActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductFragment extends Fragment implements ViewProduct, View.OnClickListener, TextWatcher, View.OnKeyListener {

    private ImageButton btnSearchStore, btnShowGrid, btnShowList;
    private RecyclerView rvStore;
    private LinearLayout linearSearch;
    private EditText edtSearchInStore;
    private boolean isSearch = false;
    private rvGroupProductAdapter adapter;
    private StoreActivity activity;
    private IPresenterListProductInStore presenterListProductInStore;
    private List<GroupProduct> mGroupProducts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_product_in_store, container, false);
        Mapping(view);
        addOnClick();
        activity = (StoreActivity) getActivity();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new rvGroupProductAdapter(new ArrayList<GroupProduct>(), getContext(), activity, false);
        rvStore.setAdapter(adapter);
        rvStore.setLayoutManager(layoutManager);

        presenterListProductInStore = new PresenterLogicListProductInStore(this, getContext());


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Store store = activity.getStore();
        Order order = activity.getOrder();
        presenterListProductInStore.getListProduct(store);
        presenterListProductInStore.showQuantityProductInCraftOrder(order);
    }

    private void Mapping(View view) {
        btnSearchStore = view.findViewById(R.id.btnSearchStore);
        rvStore = view.findViewById(R.id.rvStore);
        edtSearchInStore = view.findViewById(R.id.edtSearchInStore);
        btnShowGrid = view.findViewById(R.id.btnShowGrid);
        btnShowList = view.findViewById(R.id.btnShowList);
        linearSearch = view.findViewById(R.id.linearSearch);
        linearSearch.setVisibility(View.GONE);

    }

    private void addOnClick() {
        btnSearchStore.setOnClickListener(this);
        btnShowGrid.setOnClickListener(this);
        btnShowList.setOnClickListener(this);
        edtSearchInStore.addTextChangedListener(this);
        edtSearchInStore.setOnKeyListener(this);
    }

    @Override
    public void loadListProductInStore(List<GroupProduct> mGroupProducts, boolean isGrid) {
        setIcon(isGrid);
        adapter.setData(mGroupProducts);
        adapter.setIsGrid(isGrid);
        adapter.notifyDataSetChanged();
        this.mGroupProducts = mGroupProducts ;
    }

    @Override
    public void displayQuantityInDraftOrder(Map<String, Integer> map) {
        if (adapter == null) return;
        this.adapter.setHashMap(map);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearchStore:
                if (isSearch) {
                    linearSearch.setVisibility(View.GONE);
                    edtSearchInStore.setText("");
                    btnSearchStore.setImageResource(R.drawable.icon_search_24dp);
                } else {
                    linearSearch.setVisibility(View.VISIBLE);
                    btnSearchStore.setImageResource(R.drawable.icon_search_actived_24dp);
                }
                isSearch = !isSearch;
                break;
            case R.id.btnShowGrid:
                presenterListProductInStore.setIsGrid(true);
                setIcon(true);
                this.dataSetChanged(true);
                break;
            case R.id.btnShowList:
                presenterListProductInStore.setIsGrid(false);
                setIcon(false);
                this.dataSetChanged(false);
                break;
        }
    }

    private void setIcon(boolean b) {
        if (b) {
            btnShowGrid.setImageResource(R.drawable.icon_grid_view_actived);
            btnShowList.setImageResource(R.drawable.icon_list_view);
        } else {
            btnShowList.setImageResource(R.drawable.icon_list_view_actived);
            btnShowGrid.setImageResource(R.drawable.icon_grid_view);
        }

    }

    private void dataSetChanged(boolean isGrid) {
        this.adapter.setIsGrid(isGrid);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String str = Util.standardizeString(s.toString());
        str = Util.removeAccent(str).toLowerCase();
        if (str.length() == 0) {
            adapter.setData(this.mGroupProducts);
            adapter.notifyDataSetChanged();
        } else {
            List<GroupProduct> mG = new ArrayList<>() ;
            List<Product> mP = new ArrayList<>() ;
            for(GroupProduct group : this.mGroupProducts) {
                List<Product> list = group.getListProducts() ;
                for(Product p : list) {
                    if(Util.removeAccent(p.getProductName()).toLowerCase().contains(str)) {
                        mP.add(p) ;
                    }
                }
            }
            if (mP.size() > 0) {
                GroupProduct g = new GroupProduct() ;
                g.setListProducts(mP);
                mG.add(g) ;
            }
            adapter.setData(mG);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
            return true;
        }
        return false;
    }
}
