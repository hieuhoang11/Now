package com.example.hieuhoang.now.View.Main;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Main.Account.FragmentAccount;
import com.example.hieuhoang.now.View.Main.Bill.FragmentBill;
import com.example.hieuhoang.now.View.Main.Home.FragmentHome;
import com.example.hieuhoang.now.View.Main.Notification.FragmentNotification;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentHome fragmentHome;
    private FragmentBill fragmentBill;
    private FragmentNotification fragmentNotification;
    private FragmentAccount fragmentAccount;
    private int tabSelected = 0;
    private String TAG = "kiemtra";
    private int[] content = {R.id.Home, R.id.Bill, R.id.Notification, R.id.Account};
    private View[] views;
    private ImageButton[] buttons;
    //public static ModelLocation modelLocation ;

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

        Mapping();
        init();
        addOnClick();
        //modelLocation = new ModelLocation(this,getApplicationContext());
        //modelLocation.Connect();
    }

    private void Mapping() {
        buttons = new ImageButton[4];
        buttons[0] = findViewById(R.id.btnHome);
        buttons[1] = findViewById(R.id.btnBill);
        buttons[2] = findViewById(R.id.btnNotification);
        buttons[3] = findViewById(R.id.btnAccount);

        views = new View[4];
        views[0] = findViewById(R.id.Home);
        views[1] = findViewById(R.id.Bill);
        views[2] = findViewById(R.id.Notification);
        views[3] = findViewById(R.id.Account);

        views[1].setVisibility(View.GONE);
        views[2].setVisibility(View.GONE);
        views[3].setVisibility(View.GONE);
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
                } else visibleFragment(1);
                break;
            case R.id.btnNotification:
                if (fragmentNotification == null) {
                    fragmentNotification = new FragmentNotification();
                    setContent(fragmentNotification, 2);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //modelLocation.disConnect () ;
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
