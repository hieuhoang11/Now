package com.example.hieuhoang.now.View.Main.Bill.DraftOrder;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieuhoang.now.Adapter.srvDraftOrderBillAdapter;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Presenter.Main.Bill.DraftOrder.IPresenterDraftOrder;
import com.example.hieuhoang.now.Presenter.Main.Bill.DraftOrder.PresenterLogicDraftOrder;
import com.example.hieuhoang.now.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentDraftOrder extends Fragment implements ViewDraftOrder {
    private TextView tvTotalItem;
    private Button btnDeleteAll;
    private View viewInDraft,viewNoData;
    private srvDraftOrderBillAdapter adapter;
    private RecyclerView rvDraftOrder;
    private SwipeRefreshLayout swipeRefresh;
    private IPresenterDraftOrder presenterLogicDraftOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_bill_draft_order, container, false);
        Mapping(view);
        init();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenterLogicDraftOrder.getListDraftOrder();
    }

    private void Mapping(View view) {
        rvDraftOrder = view.findViewById(R.id.rvDraftOrder);
        tvTotalItem = view.findViewById(R.id.tvTotalItem);
        btnDeleteAll = view.findViewById(R.id.btnDeleteAll);
        viewInDraft = view.findViewById(R.id.viewInDraft);
        viewNoData = view.findViewById(R.id.viewNoData);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeResources(R.color.colorSwipe) ;
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onStart();
            }
        });

        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDialog();
            }
        });
    }

    private void init() {
        presenterLogicDraftOrder = new PresenterLogicDraftOrder(this, getContext());
        List<Order> list = new ArrayList<>();
        adapter = new srvDraftOrderBillAdapter (list,getContext(),presenterLogicDraftOrder) ;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvDraftOrder.setAdapter(adapter);
        rvDraftOrder.setLayoutManager(layoutManager);
    }

    @Override
    public void loadListDraftOrder(List<Order> mOrders) {
        viewNoData.setVisibility(View.GONE);
        viewInDraft.setVisibility(View.VISIBLE);
        rvDraftOrder.setVisibility(View.VISIBLE);
        swipeRefresh.setVisibility(View.VISIBLE);
        tvTotalItem.setText(String.valueOf(mOrders.size()));
        this.adapter.setData(mOrders);
        this.adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void noHasDraftOrder() {
        viewInDraft.setVisibility(View.GONE);
        rvDraftOrder.setVisibility(View.GONE);
        swipeRefresh.setVisibility(View.GONE);
        viewNoData.setVisibility(View.VISIBLE);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onDeleteOrderSuccess() {
        Toast.makeText(getContext() ,getContext().getResources().getString(R.string.delete_draft_order_success) ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteAllOrderSuccess() {
        Toast.makeText(getContext() ,getContext().getResources().getString(R.string.delete_all_draft_order_success) ,Toast.LENGTH_SHORT).show();
    }

    public void showDialog () {
        final Dialog dialog = new Dialog(getContext()) ;
        View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_dialog,null) ;
        TextView tvContentDialog = view.findViewById(R.id.tvContentDialog) ;
        Button btnYes = view.findViewById(R.id.btnYes) ;
        Button btnCancel = view.findViewById(R.id.btnCancel) ;
        tvContentDialog.setText(getActivity().getResources().getString(R.string.do_you_want_delete_all_draft_orders));
        btnYes.setText(getActivity().getResources().getString(R.string.delete_all));
        btnCancel.setText(getActivity().getResources().getString(R.string.cancel));
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterLogicDraftOrder.deleteAllDraftOrder();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) ;
        dialog.setContentView(view);
        dialog.show();

    }
}