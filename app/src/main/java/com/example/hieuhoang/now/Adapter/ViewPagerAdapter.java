package com.example.hieuhoang.now.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList;
    List<String> mFragmentTitleList;
    public ViewPagerAdapter(FragmentManager fm ,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList ;
//        fragmentList.add(new FragmentHome());
//        fragmentList.add(new FragmentBill());
//        fragmentList.add(new FragmentNotification());
//        fragmentList.add(new FragmentAccount());
    }

    public ViewPagerAdapter(FragmentManager fm ,List<Fragment> fragmentList,List<String> mFragmentTitleList) {
        super(fm);
        this.fragmentList = fragmentList ;
        this.mFragmentTitleList = mFragmentTitleList ;
//        fragmentList.add(new FragmentHome());
//        fragmentList.add(new FragmentBill());
//        fragmentList.add(new FragmentNotification());
//        fragmentList.add(new FragmentAccount());
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mFragmentTitleList == null || mFragmentTitleList.size() == 0)
            return "";
        return mFragmentTitleList.get(position);
    }
}
