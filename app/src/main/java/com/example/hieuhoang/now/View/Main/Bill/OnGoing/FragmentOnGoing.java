package com.example.hieuhoang.now.View.Main.Bill.OnGoing;

import android.app.Dialog;
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
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hieuhoang.now.Adapter.rvOnGoingOrderAdapter;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Presenter.Main.Bill.OnGoing.IPresenterOnGoing;
import com.example.hieuhoang.now.Presenter.Main.Bill.OnGoing.PresenterLogicOnGoing;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.OrderDetail.OrderDetailActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragmentOnGoing extends Fragment implements ViewOnGoing{
    private rvOnGoingOrderAdapter adapter;
    private IPresenterOnGoing presenterLogic;
    private RecyclerView rvOrder;
    private TextView tvTotalItem;
    private SwipeRefreshLayout swipeRefresh;
    private View viewQuantityOrder, viewNoData;
    private final int[] statuses = {
            AppConstant.PROCESSING_ORDER_STATUS,
            AppConstant.ON_GOING_ORDER_STATUS,
            };
    private BottomSheetDialog dialog;
    private Context context ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_bill_on_going, container, false);
        Mapping(view);
        init();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenterLogic.getListOrder();
    }

    @Override
    public void loadListOrder(List<Order> mOrders) {
        viewNoData.setVisibility(View.GONE);
        viewQuantityOrder.setVisibility(View.VISIBLE);
        rvOrder.setVisibility(View.VISIBLE);
        swipeRefresh.setVisibility(View.VISIBLE);
        tvTotalItem.setText(String.valueOf(mOrders.size()));
        adapter.setData(mOrders);
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void noHasOrder() {
        viewQuantityOrder.setVisibility(View.GONE);
        rvOrder.setVisibility(View.GONE);
        swipeRefresh.setVisibility(View.GONE);
        viewNoData.setVisibility(View.VISIBLE);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showOrderStatus(Map<Integer, String> map,String idOrder) {
        showSheetOrderStatus(map,idOrder);
    }

    @Override
    public void onCancelOrderSuccess() {
        Toast.makeText(getActivity() , getResources().getString(R.string.cancel_order_success),Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    private void Mapping(View view) {
        rvOrder = view.findViewById(R.id.rvOrder);
        tvTotalItem = view.findViewById(R.id.tvTotalItem);
        viewQuantityOrder = view.findViewById(R.id.viewQuantityOrder);
        viewNoData = view.findViewById(R.id.viewNoData);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeResources(R.color.colorSwipe) ;
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onStart();
            }
        });
    }


    private void init() {
        context = getContext() ;
        presenterLogic = new PresenterLogicOnGoing(this, context);
        adapter = new rvOnGoingOrderAdapter(context, new ArrayList<Order>(), presenterLogic);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvOrder.setAdapter(adapter);
        rvOrder.setLayoutManager(layoutManager);

    }

    private void showSheetOrderStatus(Map<Integer, String> map , final String idOrder) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_sheet_order_status,null);
        dialog = new BottomSheetDialog(context);
        ImageButton btnClose = view.findViewById(R.id.btnCloseSheet);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button btnDetail = view.findViewById(R.id.btnDetail) ;
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDetail = new Intent(context , OrderDetailActivity.class) ;
                iDetail.putExtra(AppConstant.ID_ORDER , idOrder) ;
                context.startActivity(iDetail);
            }
        });
        TextView tvSubmittedTime;
        TextView []tvNumber = new TextView[2];
        TextView []tv = new TextView[2];
        TextView []tvTime = new TextView[2];
        View [] lines = new View[2];

        Button btnCancelOrder;
        tvSubmittedTime = view.findViewById(R.id.tvSubmittedTime) ;
        tvNumber[0] = view.findViewById(R.id.tvProcessedNumber) ;
        tv[0] = view.findViewById(R.id.tvProcessed) ;
        tvTime[0] = view.findViewById(R.id.tvProcessedTime) ;
        tvNumber[1] = view.findViewById(R.id.tvOnGoingNumber) ;
        tv[1] = view.findViewById(R.id.tvOnGoing) ;
        tvTime[1] = view.findViewById(R.id.tvOnGoingTime) ;

        lines[0] = view.findViewById(R.id.line1) ;
        lines[1] = view.findViewById(R.id.line2) ;

        btnCancelOrder= view.findViewById(R.id.btnCancelOrder) ;
        btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirmCancelOrder(idOrder);
            }
        });
        int s = map.size() ;
        tvSubmittedTime.setText(map.get(AppConstant.SUBMIT_ORDER_STATUS));
        if(s > 1) {
            s -- ;
            int status = 0 ;
            Drawable draw = getResources().getDrawable(R.drawable.custom_text_in_order_status_positive) ;
            int color = getResources().getColor(R.color.colorBlack) ;
            int colorLine = getResources().getColor(R.color.colorTextCircleInOrderStatus) ;
            while (s > 0) {
                tvNumber[status].setBackground(draw);
                tv[status].setTextColor(color);
                tvTime[status].setText(map.get(statuses[status]));
                lines[status].setBackgroundColor(colorLine);
                status ++ ;
                s--;
            }
        }else {
            btnCancelOrder.setVisibility(View.VISIBLE);
        }
        dialog.setContentView(view);
        dialog.show();
    }


    private void dialogConfirmCancelOrder(final String idOrder) {
        final Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
        TextView tvContentDialog = view.findViewById(R.id.tvContentDialog);
        Button btnYes = view.findViewById(R.id.btnYes);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        tvContentDialog.setText(getResources().getString(R.string.do_you_want_to_cancel_order));
        btnYes.setText(getResources().getString(R.string.cancel_order));
        btnCancel.setText(getResources().getString(R.string.close));
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                presenterLogic.cancelOrder(idOrder);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.show();

    }
}
