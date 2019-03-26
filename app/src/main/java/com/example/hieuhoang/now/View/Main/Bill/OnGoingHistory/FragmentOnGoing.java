package com.example.hieuhoang.now.View.Main.Bill.OnGoingHistory;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hieuhoang.now.Adapter.rvOrderInBillAdapter;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Presenter.Main.Bill.OnGoingHistory.IPresenterOnGoingHistory;
import com.example.hieuhoang.now.Presenter.Main.Bill.OnGoingHistory.PresenterLogicOnGoing;
import com.example.hieuhoang.now.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragmentOnGoing extends Fragment implements ViewOnGoingHistory, View.OnClickListener {
    private rvOrderInBillAdapter adapter;
    private IPresenterOnGoingHistory presenterLogic;
    private RecyclerView rvOrder;
    private TextView tvTotalItem;
    private View viewQuantityOrder, viewNoData;
    private final int[] status = {
            AppConstant.PROCESSING_ORDER_STATUS,
            AppConstant.ON_GOING_ORDER_STATUS,
            AppConstant.COMPLETE_ORDER_STATUS};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_bill_on_going, container, false);
        Mapping(view);
        init();
        addOnClick();
        return view;
    }

    @Override
    public void loadListOrder(List<Order> mOrders) {
        viewNoData.setVisibility(View.GONE);
        viewQuantityOrder.setVisibility(View.VISIBLE);
        rvOrder.setVisibility(View.VISIBLE);
        tvTotalItem.setText(String.valueOf(mOrders.size()));
        adapter.setData(mOrders);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void noHasOrder() {
        viewQuantityOrder.setVisibility(View.GONE);
        rvOrder.setVisibility(View.GONE);
        viewNoData.setVisibility(View.VISIBLE);
    }

    @Override
    public void showOrderStatus(Map<Integer, String> map) {
        showSheetOrderStatus(map);
    }

    private void Mapping(View view) {
        rvOrder = view.findViewById(R.id.rvOrder);
        tvTotalItem = view.findViewById(R.id.tvTotalItem);
        viewQuantityOrder = view.findViewById(R.id.viewQuantityOrder);
        viewNoData = view.findViewById(R.id.viewNoData);
    }

    private void addOnClick() {

    }


    private void init() {
        presenterLogic = new PresenterLogicOnGoing(this, getContext());
        adapter = new rvOrderInBillAdapter(getContext(), new ArrayList<Order>(), presenterLogic);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvOrder.setAdapter(adapter);
        rvOrder.setLayoutManager(layoutManager);
        presenterLogic.getListDraftOrder();
    }

    private void showSheetOrderStatus(Map<Integer, String> map) {
        View view = getLayoutInflater().inflate(R.layout.layout_sheet_order_status, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(getContext());

        ImageButton btnClose = view.findViewById(R.id.btnCloseSheet);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView tvSubmittedNumber ,tvSubmitted,tvSubmittedTime;
        TextView tvProcessedNumber ,tvProcessed,tvProcessedTime ;
        TextView tvOnGoingNumber,tvOnGoing,tvOnGoingTime ;
        TextView tvCompletedNumber,tvCompleted,tvCompletedTime;
//        tvSubmittedNumber = view.findViewById(R.id.tvSubmittedNumber) ;
//        tvSubmitted = view.findViewById(R.id.tvSubmitted) ;
        tvSubmittedTime = view.findViewById(R.id.tvSubmittedTime) ;
        tvProcessedNumber = view.findViewById(R.id.tvProcessedNumber) ;
        tvProcessed = view.findViewById(R.id.tvProcessed) ;
        tvProcessedTime = view.findViewById(R.id.tvProcessedTime) ;
        tvOnGoingNumber = view.findViewById(R.id.tvOnGoingNumber) ;
        tvOnGoing = view.findViewById(R.id.tvOnGoing) ;
        tvOnGoingTime = view.findViewById(R.id.tvOnGoingTime) ;
        tvCompletedNumber = view.findViewById(R.id.tvCompletedNumber) ;
        tvCompleted = view.findViewById(R.id.tvCompleted) ;
        tvCompletedTime = view.findViewById(R.id.tvCompletedTime) ;

        tvSubmittedTime.setText(map.get(AppConstant.SUBMIT_ORDER_STATUS));

        dialog.setContentView(view);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
