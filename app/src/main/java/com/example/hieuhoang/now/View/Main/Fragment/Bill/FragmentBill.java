package com.example.hieuhoang.now.View.Main.Fragment.Bill;


import android.os.Bundle;
import android.support.annotation.NonNull;
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
    private Button btnOnGoing, btnHistory, btnDraftOrder;
    private int colorNormal, colorTextIsChecked;
    private FragmentManager fragmentManager;
    private int tabSelected = 0 ;
    private Button [] buttons;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bill, container, false);
        Mapping(view);
        init();

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
        this.buttons = new Button[3];
        buttons[0] = btnOnGoing ;
        buttons[1] = btnHistory ;
        buttons[2] = btnDraftOrder;

        colorNormal = getActivity().getResources().getColor(R.color.colorBlack);
        colorTextIsChecked = getActivity().getResources().getColor(R.color.colorTextTabSelected);

        fragmentManager = getActivity().getSupportFragmentManager();

        setContent(new FragmentOnGoing(),0);
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
}
