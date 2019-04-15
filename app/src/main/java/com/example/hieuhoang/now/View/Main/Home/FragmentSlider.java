package com.example.hieuhoang.now.View.Main.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.Util.Util;

public class FragmentSlider extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_slider,container,false) ;
        ImageView img = view.findViewById(R.id.imgSlider) ;
        Bundle bundle = getArguments() ;
        String path = bundle.getString(AppConstant.IMAGE,"");
//        int i = bundle.getInt("rr") ;
//        Toast.makeText(getContext() , "" + i ,Toast.LENGTH_SHORT).show();
        //Util.loadImageFromServer(path,getContext(),img);
        return view;
    }
}
