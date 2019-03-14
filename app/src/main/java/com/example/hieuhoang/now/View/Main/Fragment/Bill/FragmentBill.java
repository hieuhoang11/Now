package com.example.hieuhoang.now.View.Main.Fragment.Bill;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Main.Fragment.Bill.Fragment.FragmentDraftOrder;
import com.example.hieuhoang.now.View.Main.Fragment.Bill.Fragment.FragmentHistory;
import com.example.hieuhoang.now.View.Main.Fragment.Bill.Fragment.FragmentOnGoing;

public class FragmentBill extends Fragment implements View.OnClickListener {
    Button btnOnGoing, btnHistory, btnDraftOrder;
    private int colorBlack, colorWhite;
    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bill, container, false);
        init();
        Mapping(view);
        return view;
    }

    private void Mapping(View view) {
        btnOnGoing = view.findViewById(R.id.btnOnGoing);
        btnHistory = view.findViewById(R.id.btnHistory);
        btnDraftOrder = view.findViewById(R.id.btnDraftOrder);

        btnOnGoing.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
        btnDraftOrder.setOnClickListener(this);
    }

    private void init() {
        colorBlack = getActivity().getResources().getColor(R.color.colorBlack);
        colorWhite = getActivity().getResources().getColor(R.color.colorWhite);

        fragmentManager = getActivity().getSupportFragmentManager();

        addFragment(new FragmentOnGoing());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOnGoing:
                addFragment(new FragmentOnGoing());
                break;
            case R.id.btnHistory:
                addFragment(new FragmentHistory());
                break;
            case R.id.btnDraftOrder:
                addFragment(new FragmentDraftOrder());
                break;
        }
    }

    private void setBackgroundColor(Button btn) {
        setBackgroundColor(btnOnGoing, this.colorWhite, this.colorBlack);
        setBackgroundColor(btnHistory, this.colorWhite, this.colorBlack);
        setBackgroundColor(btnDraftOrder, this.colorWhite, this.colorBlack);
        setBackgroundColor(btn, this.colorBlack, this.colorWhite);
    }

    private void setBackgroundColor(Button btn, int colorBackground, int colorText) {
        btn.setBackgroundColor(colorBackground);
        btn.setTextColor(colorText);
    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contentBill, fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
