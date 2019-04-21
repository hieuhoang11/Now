package com.example.hieuhoang.now.View.Main;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Main.Account.FragmentAccount;
import com.example.hieuhoang.now.View.Main.Bill.FragmentBill;
import com.example.hieuhoang.now.View.Main.Home.FragmentHome;
import com.example.hieuhoang.now.View.Main.Favorite.FragmentFavorite;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentHome fragmentHome;
    private FragmentBill fragmentBill;
    private FragmentFavorite fragmentFavorite;
    private FragmentAccount fragmentAccount;
    private int tabSelected = 0;
    private String TAG = "kiemtra";
    private int[] content = {R.id.Home, R.id.Bill, R.id.Favorite, R.id.Account};
    private View[] views;
    private ImageButton[] buttons;
    //public static ModelLocation modelLocation ;

    private int[] tabIconsUnselected = {
            R.drawable.icon_home_24dp,
            R.drawable.icon_bill_24dp,
            R.drawable.icon_favorite_in_active_24dp,
            R.drawable.icon_user_24dp
    };
    private int[] tabIconsSelected = {
            R.drawable.icon_home_actived_24dp,
            R.drawable.icon_bill_actived_24dp,
            R.drawable.icon_favorite_active_24dp,
            R.drawable.icon_user_actived_24dp
    };
    private FragmentManager fragmentManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Mapping();
        init();
        addOnClick();
        //modelLocation = new ModelLocation(this,getApplicationContext());
        //modelLocation.Connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: Main");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: Main");
    }

    private void Mapping() {
        buttons = new ImageButton[4];
        buttons[0] = findViewById(R.id.btnHome);
        buttons[1] = findViewById(R.id.btnBill);
        buttons[2] = findViewById(R.id.btnFavorite);
        buttons[3] = findViewById(R.id.btnAccount);

        views = new View[4];
        for (int i = 0 ; i< content.length ;i++) {
            views[i] = findViewById(content[i]);
            views[i].setVisibility(View.GONE);
        }
        views[0].setVisibility(View.VISIBLE);

    }

    private void addOnClick() {
        buttons[0].setOnClickListener(this);
        buttons[1].setOnClickListener(this);
        buttons[2].setOnClickListener(this);
        buttons[3].setOnClickListener(this);
    }


    private void init() {

        fragmentManager = getSupportFragmentManager();

        fragmentHome = new FragmentHome();
        buttons[0].setImageResource(tabIconsSelected[tabSelected]);
        addFragment( fragmentHome,R.id.Home);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHome:
                if (fragmentHome == null) {
                    fragmentHome = new FragmentHome();
                    setContent(fragmentHome, 0);
                } else visibleFragment(0);
                break;
            case R.id.btnBill:
                if (fragmentBill == null) {
                    fragmentBill = new FragmentBill();
                    setContent(fragmentBill, 1);
                } else  {
                    visibleFragment(1);
                    fragmentBill.onStart();
                }
                break;
            case R.id.btnFavorite:
                if (fragmentFavorite == null) {
                    fragmentFavorite = new FragmentFavorite();
                    setContent(fragmentFavorite, 2);
                } else visibleFragment(2);
                break;
            case R.id.btnAccount:
                if (fragmentAccount == null) {
                    fragmentAccount = new FragmentAccount();
                    setContent(fragmentAccount, 3);
                } else visibleFragment(3);
                break;
        }
    }

    private void setContent(Fragment fragment, int index) {
        if (tabSelected == index) return;
        addFragment(fragment, content[index]);
        visibleFragment(index);
    }

    private void visibleFragment(int index) {
        views[tabSelected].setVisibility(View.GONE);
        views[index].setVisibility(View.VISIBLE);
        setIcon(index);
        tabSelected = index;
    }

    private void setIcon(int index) {
        buttons[tabSelected].setImageResource(tabIconsUnselected[tabSelected]);
        buttons[index].setImageResource(tabIconsSelected[index]);
    }

    private void addFragment(Fragment fragment, int content) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(content, fragment);
        fragmentTransaction.commit();
    }

    public void setFragmentChecked (int index){
        if (index == 1) {
            if (fragmentBill == null) {
                fragmentBill = new FragmentBill();
                Bundle bundle = new Bundle();
                bundle.putInt(AppConstant.TAB,1);
                fragmentBill.setArguments(bundle);
                setContent(fragmentBill, 1);
            } else  {
                visibleFragment(1);
                fragmentBill.setFragmentChecked(1);
            }
        }
        else if (index == 2) {
            if (fragmentFavorite == null) {
                fragmentFavorite = new FragmentFavorite();
                setContent(fragmentFavorite, 2);
            } else visibleFragment(2);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: Main ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }


}
