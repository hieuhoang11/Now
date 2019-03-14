package com.example.hieuhoang.now.View.Main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.hieuhoang.now.Adapter.ViewPagerAdapter;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Main.Fragment.FragmentAccount;
import com.example.hieuhoang.now.View.Main.Fragment.Bill.FragmentBill;
import com.example.hieuhoang.now.View.Main.Fragment.FragmentNotification;
import com.example.hieuhoang.now.View.Main.Fragment.Home.FragmentMainHome;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private int[] tabIconsUnselected = {
            R.drawable.icon_home_24dp,
            R.drawable.icon_home_24dp,
            R.drawable.icon_notifications_24dp,
            R.drawable.icon_user_24dp
    };

    private int[] tabIconsSelected = {
            R.drawable.icon_home_actived_24dp,
            R.drawable.icon_clear_black_24dp,
            R.drawable.icon_notifications_actived_24dp,
            R.drawable.icon_user_actived_24dp
    };

    private final int tabDefault = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Mapping();
        //  initSpinner();

//        Start tabLayout

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentMainHome());
        fragmentList.add(new FragmentBill());
        fragmentList.add(new FragmentNotification());
        fragmentList.add(new FragmentAccount());

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(tabDefault);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        tabLayout.getTabAt(tabDefault).setIcon(tabIconsSelected[tabDefault]);

        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                            tab.setIcon(tabIconsSelected[tab.getPosition()]);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        tab.setIcon(tabIconsUnselected[tab.getPosition()]);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);

                    }
                }
        );

        // End tabLayout
    }


    private void Mapping() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutMain);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIconsUnselected[0]);
        tabLayout.getTabAt(1).setIcon(tabIconsUnselected[1]);
        tabLayout.getTabAt(2).setIcon(tabIconsUnselected[2]);
        tabLayout.getTabAt(3).setIcon(tabIconsUnselected[3]);
    }

}
