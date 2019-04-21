package com.example.hieuhoang.now.View.Main.Bill;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Presenter.Main.Bill.IPresenterBill;
import com.example.hieuhoang.now.Presenter.Main.Bill.PresenterLogicBill;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.Util.Util;
import com.example.hieuhoang.now.View.LoginRegister.LoginRegisterActivity;
import com.example.hieuhoang.now.View.Main.Bill.DraftOrder.FragmentDraftOrder;
import com.example.hieuhoang.now.View.Main.Bill.History.FragmentHistory;
import com.example.hieuhoang.now.View.Main.Bill.OnGoing.FragmentOnGoing;
import com.example.hieuhoang.now.View.Search.SearchActivity;

import java.util.Collections;

public class FragmentBill extends Fragment implements ViewBill, View.OnClickListener {
    private Button btnOnGoing, btnHistory, btnDraftOrder, btnLogin;
    private ImageButton btnSearch;
    private int colorNormal, colorTextIsChecked;
    private View viewLoginToContinue;
    private FrameLayout[] frameLayouts;
    private Fragment[] fragments;
    private int[] contents = {R.id.contentOngoing, R.id.contentHistory, R.id.contentDraft};
    private View viewContentBill;
    private FragmentManager fragmentManager;
    private int tabSelected = -1;
    private Button[] buttons;
    private IPresenterBill presenterLogicBill;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bill, container, false);
        Mapping(view);
        init();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void Mapping(View view) {
        btnSearch = view.findViewById(R.id.btnSearch);
        btnOnGoing = view.findViewById(R.id.btnOnGoing);
        btnHistory = view.findViewById(R.id.btnHistory);
        btnDraftOrder = view.findViewById(R.id.btnDraftOrder);
        viewLoginToContinue = view.findViewById(R.id.viewLoginToContinue);
        btnLogin = view.findViewById(R.id.btnLogin);
        viewContentBill = view.findViewById(R.id.viewContentBill);

        frameLayouts = new FrameLayout[3];
        frameLayouts[0] = view.findViewById(R.id.contentOngoing);
        frameLayouts[1] = view.findViewById(R.id.contentHistory);
        frameLayouts[2] = view.findViewById(R.id.contentDraft);

        frameLayouts[0].setVisibility(View.GONE);
        frameLayouts[1].setVisibility(View.GONE);
        frameLayouts[2].setVisibility(View.GONE);

        btnOnGoing.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
        btnDraftOrder.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }


    private void init() {
        Context context = getContext();
        presenterLogicBill = new PresenterLogicBill(this, context);
        this.buttons = new Button[3];
        buttons[0] = btnOnGoing;
        buttons[1] = btnHistory;
        buttons[2] = btnDraftOrder;

        colorNormal = Util.getIdColor(context, R.color.colorBlack);
        colorTextIsChecked = Util.getIdColor(context, R.color.colorTextTabSelected);

        fragmentManager = getFragmentManager();
        fragments = new Fragment[3];

        Bundle bundle = getArguments();
        if (bundle != null)
            tabSelected = bundle.getInt(AppConstant.TAB, -1);
        if (tabSelected != -1) {
            if (fragments[1] == null) {
                fragments[1] = new FragmentHistory();
                setContent(fragments[1], 1);
            }
        } else presenterLogicBill.checkLogin();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOnGoing:
                if (tabSelected == -1) break;
                if (fragments[0] == null) {
                    fragments[0] = new FragmentOnGoing();
                    setContent(fragments[0], 0);
                    break;
                }
                visibleFragment(0);
                break;
            case R.id.btnHistory:
                if (tabSelected == -1) break;
                if (fragments[1] == null) {
                    fragments[1] = new FragmentHistory();
                    setContent(fragments[1], 1);
                    break;
                }
                visibleFragment(1);
                break;
            case R.id.btnDraftOrder:
                if (tabSelected == -1) break;
                if (fragments[2] == null) {
                    fragments[2] = new FragmentDraftOrder();
                    setContent(fragments[2], 2);
                    break;
                }
                visibleFragment(2);
                break;
            case R.id.btnLogin:
                Intent iLogin = new Intent(getContext(), LoginRegisterActivity.class);
                startActivity(iLogin);
                break;
            case R.id.btnSearch:
                Intent iSearch = new Intent(getContext(), SearchActivity.class);
                startActivity(iSearch);
                break;
        }
    }

    private void setContent(Fragment fragment, int index) {
        addFragment(fragment, index);
        visibleFragment(index);
    }

    private void setSelectedColor(int index) {
        buttons[tabSelected].setTextColor(this.colorNormal);
        buttons[index].setTextColor(this.colorTextIsChecked);
    }

    private void visibleFragment(int index) {
        frameLayouts[tabSelected].setVisibility(View.GONE);
        frameLayouts[index].setVisibility(View.VISIBLE);
        setSelectedColor(index);
        tabSelected = index;
    }

    private void addFragment(Fragment fragment, int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(contents[index], fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void noLogged() {
        viewLoginToContinue.setVisibility(View.VISIBLE);
        viewContentBill.setVisibility(View.GONE);
    }

    @Override
    public void loadOrder() {
        viewContentBill.setVisibility(View.VISIBLE);
        viewLoginToContinue.setVisibility(View.GONE);
        if (tabSelected == -1) {
            tabSelected = 0;
            fragments[0] = new FragmentOnGoing();
            setContent(fragments[0], 0);
        } else fragments[tabSelected].onStart();
    }

    public void setFragmentChecked(int index) {
        visibleFragment(index);
        fragments[tabSelected].onStart();
    }
}
