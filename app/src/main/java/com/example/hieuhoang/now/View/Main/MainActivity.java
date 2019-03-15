package com.example.hieuhoang.now.View.Main;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Main.Fragment.Account.FragmentAccount;
import com.example.hieuhoang.now.View.Main.Fragment.Bill.FragmentBill;
import com.example.hieuhoang.now.View.Main.Fragment.Notification.FragmentNotification;
import com.example.hieuhoang.now.View.Main.Fragment.Home.FragmentMainHome;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnHome,btnBill,btnNotification,btnAccount;
    private FragmentMainHome fragmentHome ;
    private FragmentBill fragmentBill ;
    private FragmentNotification fragmentNotification ;
    private FragmentAccount fragmentAccount ;
    private int tabSelected = 0 ;
   // private String [] tag = {"home" , "bill" ,"notification" , "account"} ;

    private int[] tabIconsUnselected = {
            R.drawable.icon_home_24dp,
            R.drawable.icon_bill_24dp,
            R.drawable.icon_notifications_24dp,
            R.drawable.icon_user_24dp
    };
    private int[] tabIconsSelected = {
            R.drawable.icon_home_actived_24dp,
            R.drawable.icon_bill_actived_24dp,
            R.drawable.icon_notifications_actived_24dp,
            R.drawable.icon_user_actived_24dp
    };
    private FragmentManager fragmentManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Mapping () ;
        init ();
    }

    private void Mapping () {
        btnHome = findViewById(R.id.btnHome) ;
        btnBill = findViewById(R.id.btnBill) ;
        btnNotification = findViewById(R.id.btnNotification) ;
        btnAccount = findViewById(R.id.btnAccount) ;

        btnHome.setOnClickListener(this);
        btnBill.setOnClickListener(this);
        btnNotification.setOnClickListener(this);
        btnAccount.setOnClickListener(this);
    }

    private void init () {
        btnHome.setImageResource(tabIconsSelected[tabSelected]);
        fragmentManager = getSupportFragmentManager();
        fragmentHome = new FragmentMainHome();
        fragmentBill = new FragmentBill();
        fragmentNotification = new FragmentNotification();
        fragmentAccount = new FragmentAccount() ;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contentMain, fragmentHome);
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentMain, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHome :
                setContent(fragmentHome,btnHome , 0) ;
                break;
            case R.id.btnBill :
                setContent(fragmentBill,btnBill , 1) ;
                break;
            case R.id.btnNotification :
                setContent(fragmentNotification,btnNotification , 2) ;
                break;
            case R.id.btnAccount :
                setContent(fragmentAccount,btnAccount , 3) ;
                break;
        }
    }

    private void setContent (Fragment fragment,ImageButton imageButton , int index) {
        if(tabSelected == index) return;
        setIcon (imageButton ,index) ;
        fragmentManager.popBackStack();
        replaceFragment(fragment);
        tabSelected = index ;
    }

    private void setIcon (ImageButton imageButton , int index) {
        btnHome.setImageResource(tabIconsUnselected[0]);
        btnBill.setImageResource(tabIconsUnselected[1]);
        btnNotification.setImageResource(tabIconsUnselected[2]);
        btnAccount.setImageResource(tabIconsUnselected[3]);
        imageButton.setImageResource(tabIconsSelected[index]);
    }

//    private ViewPager viewPager;
//    private TabLayout tabLayout;
//

//
//    private final int tabDefault = 0 ;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Mapping();
//
////        Start tabLayout
//
//        List<Fragment> fragmentList = new ArrayList<>();
//        fragmentList.add(new FragmentMainHome());
//        fragmentList.add(new FragmentBill());
//        fragmentList.add(new FragmentNotification());
//        fragmentList.add(new FragmentAccount());
//
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList);
//        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(tabDefault);
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
//        viewPager.setOnDragListener(new View.OnDragListener() {
//            @Override
//            public boolean onDrag(View v, DragEvent event) {
//                return true;
//            }
//        });
//
//        tabLayout.setupWithViewPager(viewPager);
//        setupTabIcons();
//        tabLayout.getTabAt(tabDefault).setIcon(tabIconsSelected[tabDefault]);
//
//        tabLayout.setOnTabSelectedListener(
//                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
//
//                    @Override
//                    public void onTabSelected(TabLayout.Tab tab) {
//                        super.onTabSelected(tab);
//                            tab.setIcon(tabIconsSelected[tab.getPosition()]);
//                    }
//
//                    @Override
//                    public void onTabUnselected(TabLayout.Tab tab) {
//                        super.onTabUnselected(tab);
//                        tab.setIcon(tabIconsUnselected[tab.getPosition()]);
//                    }
//
//                    @Override
//                    public void onTabReselected(TabLayout.Tab tab) {
//                        super.onTabReselected(tab);
//
//                    }
//                }
//        );
//
//        // End tabLayout
//    }
//
//
//    private void Mapping() {
//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        tabLayout = (TabLayout) findViewById(R.id.tabLayoutMain);
//    }
//
//    private void setupTabIcons() {
//        tabLayout.getTabAt(0).setIcon(tabIconsUnselected[0]);
//        tabLayout.getTabAt(1).setIcon(tabIconsUnselected[1]);
//        tabLayout.getTabAt(2).setIcon(tabIconsUnselected[2]);
//        tabLayout.getTabAt(3).setIcon(tabIconsUnselected[3]);
//    }

}
