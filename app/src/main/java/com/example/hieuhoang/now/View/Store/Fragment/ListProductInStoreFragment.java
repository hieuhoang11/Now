package com.example.hieuhoang.now.View.Store.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.hieuhoang.now.Adapter.StoreAdapter;
import com.example.hieuhoang.now.Model.ObjectClass.GroupProduct;
import com.example.hieuhoang.now.Presenter.Store.ListProduct.IPresenterListProductInStore;
import com.example.hieuhoang.now.Presenter.Store.ListProduct.PresenterLogicListProductInStore;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Store.StoreActivity;

import java.util.List;
import java.util.Map;

public class ListProductInStoreFragment extends Fragment implements ViewListProductInStore, View.OnClickListener {

    private ImageButton btnSearchStore, btnShowGrid, btnShowList;
    private RecyclerView rvStore;
    private LinearLayout linearSearch;
    private EditText edtSearchInStore;
    private boolean isSearch = false;
    private StoreAdapter adapter;
    private StoreActivity activity;
    private IPresenterListProductInStore presenterListProductInStore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_product_in_store, container, false);
        Mapping(view);
        addOnClick();
        activity = (StoreActivity) getActivity();
        presenterListProductInStore = new PresenterLogicListProductInStore(this, getContext());
        presenterListProductInStore.getListProduct(String.valueOf(activity.getStore().getID_Store()));
        presenterListProductInStore.showQuantityProductInCraftOrder(activity.getOrder());
        return view;
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

    private void addOnClick(){
        btnSearchStore.setOnClickListener(this);
        btnShowGrid.setOnClickListener(this);
        btnShowList.setOnClickListener(this);
    }

    @Override
    public void loadListProductInStore(List<GroupProduct> mGroupProducts, boolean isGrid) {
        setIcon(isGrid);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new StoreAdapter(mGroupProducts, getContext(), activity,activity.getPresenterStore(), isGrid);
        rvStore.setAdapter(adapter);
        rvStore.setLayoutManager(layoutManager);
    }

    @Override
    public void showQuantityInCraftOrder(Map<String, Integer> map) {
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
                } else  {
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

    private void setIcon (boolean b) {
        if(b) {
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

}
