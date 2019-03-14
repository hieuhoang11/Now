package com.example.hieuhoang.now.View.Main.Fragment.Bill.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.example.hieuhoang.now.Adapter.rvDraftOrderBillAdapter;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Presenter.Main.Bill.IPresenterDraftOrder;
import com.example.hieuhoang.now.Presenter.Main.Bill.PresenterLogicDraftOrder;
import com.example.hieuhoang.now.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentDraftOrder extends Fragment implements ViewDraftOrder {
    private TextView tvTotalItem;
    private Button btnDeleteAll;
    private View viewInDraft;
    private rvDraftOrderBillAdapter adapter;
    private RecyclerView rvDraftOrder;
    private IPresenterDraftOrder presenterLogicDraftOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_bill_draft_order, container, false);
        Mapping(view);

        init();
        presenterLogicDraftOrder = new PresenterLogicDraftOrder(this, getContext());
        presenterLogicDraftOrder.getListDraftOrder();
        return view;
    }

    private void Mapping(View view) {
        rvDraftOrder = view.findViewById(R.id.rvDraftOrder);
        tvTotalItem = view.findViewById(R.id.tvTotalItem);
        btnDeleteAll = view.findViewById(R.id.btnDeleteAll);
        viewInDraft = view.findViewById(R.id.viewInDraft);
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDialog();
            }
        });
    }

    private void init() {
        List<Order> list = new ArrayList<>();
        adapter = new rvDraftOrderBillAdapter(list, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvDraftOrder.setAdapter(adapter);
        rvDraftOrder.setLayoutManager(layoutManager);

    }

    @Override
    public void loadListDraftOrder(List<Order> mOrders) {
        viewInDraft.setVisibility(View.VISIBLE);
        rvDraftOrder.setVisibility(View.VISIBLE);
        tvTotalItem.setText(String.valueOf(mOrders.size()));
        this.adapter.setData(mOrders);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void noHasDraftOrder() {
        viewInDraft.setVisibility(View.GONE);
        rvDraftOrder.setVisibility(View.GONE);
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