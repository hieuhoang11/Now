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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hieuhoang.now.Adapter.StoreAdapter;
import com.example.hieuhoang.now.Model.ObjectClass.GroupProduct;
import com.example.hieuhoang.now.Presenter.Store.IPresenterListProductInStore;
import com.example.hieuhoang.now.Presenter.Store.PresenterLogicListProductInStore;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Store.StoreActivity;

import java.util.List;

/**
 * Created by Hieu Hoang on 08/03/2019.
 */

public class ListProductInStoreFragment extends Fragment implements ViewListProductInStore, View.OnClickListener {

    ImageButton btnSearchStore, btnShowGrid, btnShowList;
    RecyclerView rvStore;
    LinearLayout linearSearch;
    EditText edtSearchInStore;
    private boolean isSearch = false;
    private StoreAdapter adapter;
    StoreActivity activity;
    IPresenterListProductInStore presenterListProductInStore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_product_in_store, container, false);
        Mapping(view);
        addOnClick();
        activity = (StoreActivity) getActivity();
        presenterListProductInStore = new PresenterLogicListProductInStore(this, getActivity().getApplicationContext());
        presenterListProductInStore.getListProduct(String.valueOf(activity.getStore().getID_Store()));
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new StoreAdapter(mGroupProducts, getActivity().getApplicationContext(), activity, isGrid);
        rvStore.setAdapter(adapter);
        rvStore.setLayoutManager(layoutManager);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearchStore:
                if (isSearch) {
                    linearSearch.setVisibility(View.GONE);
                    edtSearchInStore.setText("");
                } else linearSearch.setVisibility(View.VISIBLE);
                isSearch = !isSearch;
                break;
            case R.id.btnShowGrid:
                presenterListProductInStore.setIsGrid(true);
                this.dataSetChanged(true);
                break;
            case R.id.btnShowList:
                presenterListProductInStore.setIsGrid(false);
                this.dataSetChanged(false);
                break;
        }
    }

    void dataSetChanged(boolean isGrid) {
        this.adapter.setIsGrid(isGrid);
        this.adapter.notifyDataSetChanged();
    }
}
