package com.example.hieuhoang.now.View.Main.Bill.History;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hieuhoang.now.Adapter.rvHistoryOrderAdapter;
import com.example.hieuhoang.now.Util.Util;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Presenter.Main.Bill.History.IPresenterHistoryOrder;
import com.example.hieuhoang.now.Presenter.Main.Bill.History.PresenterLogicHistoryOrder;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.OrderDetail.OrderDetailActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class FragmentHistory extends Fragment implements ViewHistory, View.OnClickListener {
    private rvHistoryOrderAdapter adapter;
    private IPresenterHistoryOrder presenterLogic;
    private RecyclerView rvOrder;
    private View viewNoData;
    private TextView edtStartDate, edtEndDate;
    private SwipeRefreshLayout swipeRefreshLayout ;
    private final int[] statuses = {
            AppConstant.SUBMIT_ORDER_STATUS ,
            AppConstant.PROCESSING_ORDER_STATUS,
            AppConstant.ON_GOING_ORDER_STATUS,
            AppConstant.COMPLETE_ORDER_STATUS
    };
    private BottomSheetDialog dialog;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_bill_history, container, false);

        Mapping(view);
        init();
        addOnClick();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadOrder () ;
    }

    @Override
    public void loadListOrder(List<Order> mOrders) {
        adapter.setData(mOrders);
        adapter.notifyDataSetChanged();
        viewNoData.setVisibility(View.GONE);
        rvOrder.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void noHasOrder() {
        rvOrder.setVisibility(View.GONE);
        viewNoData.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showOrderStatus(Map<Integer, String> map, String idOrder) {
        showSheetOrderStatus(map,idOrder);

    }

    private void Mapping(View view) {
        rvOrder = view.findViewById(R.id.rvOrder);
        viewNoData = view.findViewById(R.id.viewNoData);
        edtEndDate = view.findViewById(R.id.edtEndDate);
        edtStartDate = view.findViewById(R.id.edtStartDate);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorSwipe) ;
    }

    private void addOnClick() {
        edtEndDate.setOnClickListener(this);
        edtStartDate.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               onStart();
            }
        });
    }


    private void init() {
        context = getContext();
        presenterLogic = new PresenterLogicHistoryOrder(context, this);

        initDate();

        adapter = new rvHistoryOrderAdapter(context, new ArrayList<Order>(), presenterLogic);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvOrder.setAdapter(adapter);
        rvOrder.setLayoutManager(layoutManager);

    }

    private void loadOrder () {
        String startDate = formatDate(edtStartDate.getText().toString());
        String endDate = formatDate(edtEndDate.getText().toString());
        presenterLogic.getListOrder(startDate, endDate);
    }

    private String formatDate(String date) {
        String[] time = date.split("-");
        int d = Integer.parseInt(time[0]);
        int m = Integer.parseInt(time[1]);
        int y = Integer.parseInt(time[2]);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(y, m - 1, d);
        return format.format(calendar.getTime());
    }

    private void initDate() {
        Calendar calendar = Calendar.getInstance() ;
        edtEndDate.setText(Util.formatDate(calendar.getTime()));

        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DATE);
        if (month == 0) {
            month = 11;
            year -= 1;
        } else month -= 1;
        calendar.set(year, month, day);
        edtStartDate.setText(Util.formatDate(calendar.getTime()));
    }

    private void chooseDate(final TextView edt) {
        String[] time = edt.getText().toString().split("-");
        int d = Integer.parseInt(time[0]);
        int m = Integer.parseInt(time[1]) - 1;
        int y = Integer.parseInt(time[2]);
        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                edt.setText(Util.formatDate(calendar.getTime()));
                loadOrder () ;
            }
        }, y, m, d);
        dialog.show();
    }


    private void showSheetOrderStatus(Map<Integer, String> map, final String idOrder) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_sheet_order_status, null);
        dialog = new BottomSheetDialog(context);

        ImageButton btnClose = view.findViewById(R.id.btnCloseSheet);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button btnDetail = view.findViewById(R.id.btnDetail);
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDetail = new Intent(context, OrderDetailActivity.class);
                iDetail.putExtra(AppConstant.ID_ORDER, idOrder);
                context.startActivity(iDetail);
            }
        });
        TextView[] tvNumber = new TextView[4];
        TextView[] tv = new TextView[4];
        TextView[] tvTime = new TextView[4];
        View[] lines = new View[3];

        tvNumber[0] = view.findViewById(R.id.tvSubmittedNumber) ;
        tv[0] = view.findViewById(R.id.tvSubmitted) ;
        tvTime[0] = view.findViewById(R.id.tvSubmittedTime);

        tvNumber[1] = view.findViewById(R.id.tvProcessedNumber);
        tv[1] = view.findViewById(R.id.tvProcessed);
        tvTime[1] = view.findViewById(R.id.tvProcessedTime);

        tvNumber[2] = view.findViewById(R.id.tvOnGoingNumber);
        tv[2] = view.findViewById(R.id.tvOnGoing);
        tvTime[2] = view.findViewById(R.id.tvOnGoingTime);

        tvNumber[3] = view.findViewById(R.id.tvCompletedNumber) ;
        tv[3] = view.findViewById(R.id.tvCompleted) ;
        tvTime[3] = view.findViewById(R.id.tvCompletedTime) ;

        lines[0] = view.findViewById(R.id.line1);
        lines[1] = view.findViewById(R.id.line2);
        lines[2] = view.findViewById(R.id.line3);

        int s = map.size();
        int status = 0;
        Drawable draw = getResources().getDrawable(R.drawable.custom_text_in_order_status_positive);
        int color = getResources().getColor(R.color.colorBlack);
        int colorLine = getResources().getColor(R.color.colorTextCircleInOrderStatus);
        while (s > 1) {
            tvNumber[status].setBackground(draw);
            tv[status].setTextColor(color);
            tvTime[status].setText(map.get(statuses[status]));
            if (status < 3) lines[status].setBackgroundColor(colorLine);
            status++;
            s--;
        }
        dialog.setContentView(view);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edtEndDate:
                chooseDate(edtEndDate);
                break;
            case R.id.edtStartDate:
                chooseDate(edtStartDate);
                break;
        }
    }
}