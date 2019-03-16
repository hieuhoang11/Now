package com.example.hieuhoang.now.View.Test;

import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.example.hieuhoang.now.R;

public class test3Activity extends AppCompatActivity {


    private static final String TAG = test3Activity.class.getSimpleName();

    @BindView(R.id.btn_bottom_sheet)
    Button btnBottomSheet;

    @BindView(R.id.bottom_sheet)
    CoordinatorLayout layoutBottomSheet;


    BottomSheetBehavior sheetBehavior, sheetBehaviorAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setPeekHeight(0);


        View bottom_sheet_add_to_cart = findViewById(R.id.bottom_sheet_add_to_cart);
        sheetBehaviorAddToCart = BottomSheetBehavior.from(bottom_sheet_add_to_cart);

        Log.i("kiemtra", "onCreate: "+sheetBehaviorAddToCart.getPeekHeight());
        sheetBehaviorAddToCart.setPeekHeight(0);
        ;
        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
//        sheetBehaviorAddToCart.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                sheetBehaviorAddToCart.setState(BottomSheetBehavior.STATE_COLLAPSED);
////                switch (newState) {
////                    case BottomSheetBehavior.STATE_HIDDEN:
////                        break;
////                    case BottomSheetBehavior.STATE_EXPANDED: {
////                        btnBottomSheet.setText("Close Sheet");
////                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
////                    }
////                    break;
////                    case BottomSheetBehavior.STATE_COLLAPSED: {
////                        btnBottomSheet.setText("Expand Sheet");
////                    }
////                    break;
////                    case BottomSheetBehavior.STATE_DRAGGING:
////                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
////                        break;
////                    case BottomSheetBehavior.STATE_SETTLING:
////                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
////                        break;
////                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });
    }

    /**
     * manually opening / closing bottom sheet on button click
     */
    @OnClick(R.id.btn_bottom_sheet)
    public void toggleBottomSheet() {
        // sheetBehavior.setSkipCollapsed(true) ;



        sheetBehavior.setPeekHeight(700);
//        int[] attrs = new int[] {R.attr.actionBarSize};
//        TypedArray ta = getApplicationContext().obtainStyledAttributes(attrs);
//        int toolBarHeight = ta.getDimensionPixelSize(0, -1);
//        ta.recycle();
        sheetBehaviorAddToCart.setPeekHeight(84);
        Log.i("kiemtra", "toggleBottomSheet: " );
//        View view = getLayoutInflater().inflate(R.layout.custom_bottom_sheet_add_to_cart, null);
//
//        BottomSheetDialog dialog = new BottomSheetDialog(this);
//        dialog.setContentView(view);
//        dialog.show();
//        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
//            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//            btnBottomSheet.setText("Close sheet");
//        } else {
//            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//            btnBottomSheet.setText("Expand sheet");
//        }
    }

    //
    @OnClick(R.id.btn_bottom_sheet_dialog)
    public void showBottomSheetDialog() {
        View view = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet_dialog, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();
    }

    //
//
//    /**
//     * showing bottom sheet dialog fragment
//     * same layout is used in both dialog and dialog fragment
//     */
    @OnClick(R.id.btn_bottom_sheet_dialog_fragment)
    public void showBottomSheetDialogFragment() {
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }
}
