package com.example.hieuhoang.now.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.hieuhoang.now.View.Main.Home.FragmentSlider;

import java.util.List;

public class rvSliderAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragments ;

    public rvSliderAdapter(FragmentManager fm , List<Fragment > fragments) {
        super(fm);
        this.fragments = fragments ;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
