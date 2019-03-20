package com.example.hieuhoang.now.View.Main.Bill;


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

import com.example.hieuhoang.now.Presenter.Main.Bill.IPresenterBill;
import com.example.hieuhoang.now.Presenter.Main.Bill.PresenterLogicBill;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.LoginRegister.LoginRegisterActivity;
import com.example.hieuhoang.now.View.Main.Bill.DraftOrder.FragmentDraftOrder;
import com.example.hieuhoang.now.View.Main.Bill.History.FragmentHistory;
import com.example.hieuhoang.now.View.Main.Bill.OnGoing.FragmentOnGoing;

public class FragmentBill extends Fragment implements ViewBill,View.OnClickListener {
    private Button btnOnGoing, btnHistory, btnDraftOrder , btnLogin;
    private int colorNormal, colorTextIsChecked;
    private View viewLoginToContinue;
    private FrameLayout contentBill ;
    private FragmentManager fragmentManager;
    private int tabSelected = 0 ;
    private Button [] buttons;
    private IPresenterBill presenterLogicBill ;

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
        presenterLogicBill.checkLogin();
    }

    private void Mapping(View view) {
        btnOnGoing = view.findViewById(R.id.btnOnGoing);
        btnHistory = view.findViewById(R.id.btnHistory);
        btnDraftOrder = view.findViewById(R.id.btnDraftOrder);
        viewLoginToContinue= view.findViewById(R.id.viewLoginToContinue);
        contentBill = view.findViewById(R.id.contentBill);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnOnGoing.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
        btnDraftOrder.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void init() {

        presenterLogicBill = new PresenterLogicBill(this ,getContext()) ;

        this.buttons = new Button[3];
        buttons[0] = btnOnGoing ;
        buttons[1] = btnHistory ;
        buttons[2] = btnDraftOrder;

        colorNormal = getActivity().getResources().getColor(R.color.colorBlack);
        colorTextIsChecked = getActivity().getResources().getColor(R.color.colorTextTabSelected);

        fragmentManager = getActivity().getSupportFragmentManager();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOnGoing:
                setContent(new FragmentOnGoing(),0);
                break;
            case R.id.btnHistory:
                setContent(new FragmentHistory(),1);
                break;
            case R.id.btnDraftOrder:
                setContent(new FragmentDraftOrder(),2);
                break;
            case R.id.btnLogin :
                Intent iLogin = new Intent(getContext() , LoginRegisterActivity.class) ;
                startActivity(iLogin);
                break;
        }
    }

    private void setContent(Fragment fragment ,int index) {
        addFragment(fragment) ;
        setSelectedColor(index);
        tabSelected = index ;
    }

    private void setSelectedColor(int index) {
        buttons[tabSelected].setTextColor(this.colorNormal);
        buttons[index].setTextColor(this.colorTextIsChecked);
    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentBill, fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void notLogIn() {
        viewLoginToContinue.setVisibility(View.VISIBLE);
        contentBill.setVisibility(View.GONE);
    }

    @Override
    public void loadOrder() {
        contentBill.setVisibility(View.VISIBLE);
        viewLoginToContinue.setVisibility(View.GONE);
        setContent(new FragmentOnGoing(),0);
    }
}
