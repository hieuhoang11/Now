package com.example.hieuhoang.now.View.Store;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieuhoang.now.Adapter.ViewPagerAdapter;
import com.example.hieuhoang.now.Adapter.srvCartDetailAdapter;
import com.example.hieuhoang.now.Common.Common;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.Product;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Presenter.Store.IPresenterStore;
import com.example.hieuhoang.now.Presenter.Store.PresenterLogicStore;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.LoginRegister.LoginRegisterActivity;
import com.example.hieuhoang.now.View.Store.Fragment.InformationStoreFragment;
import com.example.hieuhoang.now.View.Store.Fragment.ProductsInStore.ListProductInStoreFragment;
import com.example.hieuhoang.now.View.SubmitOrder.SubmitOrderActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoreActivity extends AppCompatActivity implements ViewStore, View.OnClickListener, View.OnLongClickListener {
    IPresenterStore presenterStore;
    //Store
    private TextView tvNameOfStore, tvAddressOfStore;
    private ImageView imgStore;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Product product;
    private Store store;
    private Order order;
    //sheet
    private ImageView imgBottomSheet, imgProductBottomSheet;
    private ImageButton btnSubtract, btnAdd, btnCloseBottomSheet;
    private View bottom_sheet;
    private TextView tvQuantity, tvNameProductBottomSheet, tvOldPriceBottomSheet, tvNewPriceBottomSheet, tvQuantityPurchasedBottomSheet, tvNote;

    //sheet add to cart
    private View bottom_sheet_add_to_cart;
    private TextView tvTotal;
    private int peekHeight;

    //sheet cart
    private View bottom_sheet_cart_in_store, viewCart, viewDelivery;
    private TextView tvTotalMoneyOfCart, tvNumberItemInCart;

    //bottom_sheet_cart_detail
    private View bottom_sheet_cart_detail;
    private ImageButton btnCloseSheetDetail;
    private TextView tvResetDetail, tvTotalItemsInCartDetail, tvTotalMoneyOfCartDetail;
    private RecyclerView rvProductsInCartDetail;

    private BottomSheetBehavior sheetBehavior, sheetBehaviorAddToCart, sheetCart, sheetCartDetail;
    private ViewPagerAdapter adapter;
   // private Dialog dialog;
    private srvCartDetailAdapter cartDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        Mapping();
        addOnClick();

        Intent intent = getIntent();
        String idStore = intent.getStringExtra(AppConstant.ID_STORE);

        presenterStore = new PresenterLogicStore(this, getApplicationContext());
        presenterStore.getStoreByID(idStore);

        init();

        presenterStore.getDraftOrder(idStore);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            toolbar.setTitle(store.getStoreName());
//        }

    }

    private void Mapping() {
        appBarLayout = findViewById(R.id.appBar);
        tvNameOfStore = findViewById(R.id.tvNameOfStore);
        tvAddressOfStore = findViewById(R.id.tvAddressOfStore);
        imgStore = findViewById(R.id.imgStore);
        viewPager = findViewById(R.id.viewPagerStore);
        tabLayout = findViewById(R.id.tabLayoutStore);

        //bottom sheet
        bottom_sheet = findViewById(R.id.bottom_sheet);
        imgProductBottomSheet = findViewById(R.id.imgProductBottomSheet);
        btnCloseBottomSheet = findViewById(R.id.btnCloseBottomSheet);
        tvNameProductBottomSheet = findViewById(R.id.tvNameProductBottomSheet);
        tvOldPriceBottomSheet = findViewById(R.id.tvOldPriceBottomSheet);
        tvNewPriceBottomSheet = findViewById(R.id.tvNewPriceBottomSheet);
        imgBottomSheet = findViewById(R.id.imgBottomSheet);
        tvQuantityPurchasedBottomSheet = findViewById(R.id.tvQuantityPurchasedBottomSheet);
        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvNote = findViewById(R.id.tvNote);

        //add to cart
        bottom_sheet_add_to_cart = findViewById(R.id.bottom_sheet_add_to_cart);
        tvTotal = findViewById(R.id.tvTotal);
        //cart
        bottom_sheet_cart_in_store = findViewById(R.id.bottom_sheet_cart_in_store);
        tvNumberItemInCart = findViewById(R.id.tvNumberItemInCart);
        tvTotalMoneyOfCart = findViewById(R.id.tvTotalMoneyOfCart);
        viewCart = findViewById(R.id.viewCart);
        viewDelivery = findViewById(R.id.viewDelivery);
        //cart detail
        bottom_sheet_cart_detail = findViewById(R.id.bottom_sheet_cart_detail);
        btnCloseSheetDetail = findViewById(R.id.btnCloseSheetDetail);
        tvResetDetail = findViewById(R.id.tvResetDetail);
        tvTotalItemsInCartDetail = findViewById(R.id.tvTotalItemsInCartDetail);
        tvTotalMoneyOfCartDetail = findViewById(R.id.tvTotalMoneyOfCartDetail);
        rvProductsInCartDetail = findViewById(R.id.rvProductsInCartDetail);

    }

    private void addOnClick() {
        appBarLayout.setOnClickListener(this);
        appBarLayout.setOnLongClickListener(this);

        btnCloseBottomSheet.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSubtract.setOnClickListener(this);
        tvQuantity.setOnClickListener(this);
        bottom_sheet_add_to_cart.setOnClickListener(this);
        viewCart.setOnClickListener(this);
        viewDelivery.setOnClickListener(this);
        btnCloseSheetDetail.setOnClickListener(this);
        tvResetDetail.setOnClickListener(this);
    }

    private void init() {

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new ListProductInStoreFragment());
        fragmentList.add(new InformationStoreFragment());

        List<String> fragmentTitleList = new ArrayList<>();
        fragmentTitleList.add(getResources().getString(R.string.delivery));
        fragmentTitleList.add(getResources().getString(R.string.information));

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, fragmentTitleList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        cartDetailAdapter = new srvCartDetailAdapter(new ArrayList<OrderDetail>(), getApplicationContext(), this, presenterStore);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvProductsInCartDetail.setAdapter(cartDetailAdapter);
        rvProductsInCartDetail.setLayoutManager(layoutManager);

        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);

                        if (tab.getPosition() != 0) {
                            closeCartAndCartDetail();
                        } else if (order != null) showCart(order);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);

                    }
                }
        );
        // bottom sheet
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        bottom_sheet.setVisibility(View.GONE);

        sheetBehaviorAddToCart = BottomSheetBehavior.from(bottom_sheet_add_to_cart);
        bottom_sheet_add_to_cart.setVisibility(View.GONE);
        peekHeight = sheetBehaviorAddToCart.getPeekHeight();

        sheetCart = BottomSheetBehavior.from(bottom_sheet_cart_in_store);
        bottom_sheet_cart_in_store.setVisibility(View.GONE);

        sheetCartDetail = BottomSheetBehavior.from(bottom_sheet_cart_detail);
        bottom_sheet_cart_detail.setVisibility(View.GONE);
    }

    @Override
    public void loadInformationStore(Store store) {
        this.store = store;
        tvNameOfStore.setText(store.getStoreName());
        tvAddressOfStore.setText(store.getStoreAddress());
        Common.loadImageFromInternet(AppConstant.SERVER_NAME_IMG + store.getImage(), getApplicationContext(), imgStore);
    }

    @Override
    public void showCartDetail(List<OrderDetail> orderDetailList) {

        bottom_sheet_cart_detail.setVisibility(View.VISIBLE);
        cartDetailAdapter.setData(orderDetailList);
        cartDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public void showBottomSheetAddToCart(Product product) {
        sheetBehavior.setPeekHeight(900);
        sheetBehaviorAddToCart.setPeekHeight(this.peekHeight);

        bottom_sheet_add_to_cart.setVisibility(View.VISIBLE);
        bottom_sheet.setVisibility(View.VISIBLE);
        appBarLayout.animate().alpha((float) 0.3).start();

        tvQuantity.setText(String.valueOf(1));
        Common.loadImageFromInternet(AppConstant.SERVER_NAME_IMG + product.getImage().trim(), getApplicationContext(), imgProductBottomSheet);
        tvNameProductBottomSheet.setText(product.getProductName());
        String oldPriceStr = Common.formatNumber(product.getPrice());
        if (product.getDiscount() != 0) {
            tvNewPriceBottomSheet.setVisibility(View.VISIBLE);
            String disCount = Common.formatNumber(product.getDiscount());
            tvNewPriceBottomSheet.setText(disCount);
            tvOldPriceBottomSheet.setText(Common.oldPriceFormat(oldPriceStr));
            imgBottomSheet.setVisibility(View.VISIBLE);
            tvTotal.setText(disCount);
        } else {
            tvTotal.setText(oldPriceStr);
            tvOldPriceBottomSheet.setText(oldPriceStr);
            imgBottomSheet.setVisibility(View.GONE);
            tvNewPriceBottomSheet.setVisibility(View.GONE);
        }

        tvQuantityPurchasedBottomSheet.setText(Common.qualityPurchased(product.getQuantityPurchase()));

        this.product = product;
    }

    @Override
    public void startLoginActivity() {
        Intent iLogin = new Intent(StoreActivity.this, LoginRegisterActivity.class);
        startActivity(iLogin);
    }

    @Override
    public void showCart(Order order) {
        this.closeBottomSheetAddToCart();
        bottom_sheet_cart_in_store.setVisibility(View.VISIBLE);

        String quantity = String.valueOf(order.getQuantityProduct());
        String totalMoney = Common.formatNumber(order.getTotalMoney());

        tvNumberItemInCart.setText(quantity);
        tvTotalMoneyOfCart.setText(totalMoney);

        tvTotalItemsInCartDetail.setText(quantity);
        tvTotalMoneyOfCartDetail.setText(totalMoney);

        this.order = order;

    }

    @Override
    public void onResetDraftOrderSuccess() {
        this.closeCartAndCartDetail();
        this.order = null;
    }

    @Override
    public void disPlayQuantityOfProductInCraftOrder(Map<String, Integer> map) {
        ListProductInStoreFragment fragment = (ListProductInStoreFragment) adapter.getItem(0);
        fragment.displayQuantityInDraftOrder(map);
    }

    @Override
    public void showSheetEditNote(String idProduct, String note) {
        showBottomSheetDialogEditNote(order.getIdOrder(), idProduct, note);
    }

    @Override
    public void deleteItemOrderDetail(String idOrder, String idProduct) {
        dialogConfirmDeleteItemOrderDetail(idOrder, idProduct);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCloseBottomSheet:
                closeBottomSheetAddToCart();
                break;
            case R.id.btnAdd:
                String id = null;
                if (this.order != null) {
                    id = this.order.getIdOrder();
                }
                int a = Integer.parseInt(tvQuantity.getText().toString()) + 1;
                if (presenterStore.isEnoughItems(id, this.product.getId(), a)) {
                    tvQuantity.setText(String.valueOf(a));
                    tvTotal.setText(Common.formatNumber(totalMoney(a)));
                } else {
                    Toast.makeText(this.getApplicationContext(), R.string.msg_out_of_stock, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnSubtract:
                int s = Integer.parseInt(tvQuantity.getText().toString()) - 1;
                if (s >= 1) {
                    tvQuantity.setText(String.valueOf(s));
                    tvTotal.setText(Common.formatNumber(totalMoney(s)));
                }
                break;
            case R.id.bottom_sheet_add_to_cart:
                String id1 = null;
                if (this.order != null) {
                    id1 = this.order.getIdOrder();
                }
                if (!presenterStore.isEnoughItems(id1, this.product.getId(), Integer.parseInt(tvQuantity.getText().toString()))) {
                    Toast.makeText(this.getApplicationContext(), R.string.msg_out_of_stock, Toast.LENGTH_SHORT).show();
                    break;
                }
                String idStore = String.valueOf(this.store.getID_Store());
                String idProduct = this.product.getId();
                int quantity = Integer.parseInt(tvQuantity.getText().toString().trim());
                String note = tvNote.getText().toString().trim();
                this.presenterStore.addProductsToCart(this.order, idStore, idProduct, quantity, note);
                break;
            case R.id.viewCart:
                if (bottom_sheet_cart_detail.getVisibility() != View.VISIBLE)
                    presenterStore.getOrderDetail(this.order.getIdOrder());
                break;
            case R.id.viewDelivery:
                Intent iSubmitOrder = new Intent(StoreActivity.this, SubmitOrderActivity.class);
                iSubmitOrder.putExtra(AppConstant.ID_ORDER, this.order.getIdOrder());
                startActivity(iSubmitOrder);
                break;
            case R.id.btnCloseSheetDetail:
                bottom_sheet_cart_detail.setVisibility(View.GONE);
                break;
            case R.id.tvResetDetail:
                dialogConfirmResetCart();
                break;
            case R.id.tvQuantity:
                showBottomSheetDialogTypeInQuantity();
                break;
            case R.id.content:
            case R.id.appBar:
                Log.i("kiemtra", "onClick: ");
                closeBottomSheetAddToCart();
                break;
        }
    }

    @Override
    public void closeCartAndCartDetail() {
        bottom_sheet_cart_in_store.setVisibility(View.GONE);
        bottom_sheet_cart_detail.setVisibility(View.GONE);
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.content:
            case R.id.appBar:
                closeBottomSheetAddToCart();
                break;
        }
        return true;
    }

    private float totalMoney(int quantity) {
        return product.getDiscount() == 0 ? quantity * product.getPrice() : quantity * product.getDiscount();
    }

    private void closeBottomSheetAddToCart() {
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        sheetBehavior.setPeekHeight(0);
        sheetBehaviorAddToCart.setPeekHeight(0);
        bottom_sheet_add_to_cart.setVisibility(View.GONE);
        bottom_sheet.setVisibility(View.GONE);

        appBarLayout.animate().alpha((float) 1.0).start();
    }

    public Store getStore() {
        return store;
    }

    public Order getOrder() {
        return order;
    }

    public IPresenterStore getPresenterStore() {
        return presenterStore;
    }

    private void dialogConfirmResetCart() {
        final Dialog dialog = new Dialog(StoreActivity.this);
        View view = LayoutInflater.from(StoreActivity.this).inflate(R.layout.custom_dialog, null);
        TextView tvContentDialog = view.findViewById(R.id.tvContentDialog);
        Button btnYes = view.findViewById(R.id.btnYes);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        tvContentDialog.setText(getResources().getString(R.string.do_you_want_reset_draft_order));
        btnYes.setText(getResources().getString(R.string.reset));
        btnCancel.setText(getResources().getString(R.string.cancel));
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                presenterStore.resetOrder(order.getIdOrder());
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.show();

    }

    public void dialogConfirmDeleteItemOrderDetail(final String idOrder, final String idProduct) {
        final Dialog dialog = new Dialog(StoreActivity.this);
        View view = LayoutInflater.from(StoreActivity.this).inflate(R.layout.custom_dialog, null);
        TextView tvContentDialog = view.findViewById(R.id.tvContentDialog);
        Button btnYes = view.findViewById(R.id.btnYes);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        tvContentDialog.setText(getResources().getString(R.string.do_you_want_delete_this_items));
        btnYes.setText(getResources().getString(R.string.delete));
        btnCancel.setText(getResources().getString(R.string.cancel));
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                presenterStore.deleteOrderDetail(idOrder, idProduct);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.show();

    }

    public void showBottomSheetDialogTypeInQuantity() {
        View view = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_dialog_quantity, null);
        final EditText edtQuantity = view.findViewById(R.id.edtQuantity);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        view.findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = edtQuantity.getText().toString().trim();
                if (!quantity.equals("") && !quantity.equals("0")) {
                    tvQuantity.setText(quantity);
                    tvTotal.setText(Common.formatNumber(totalMoney(Integer.parseInt(quantity))));
                }
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    public void showBottomSheetDialogEditNote(final String idOrder, final String idProduct, String note) {
        View view = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_dialog_edit_note, null);
        final EditText edtNote = view.findViewById(R.id.edtNote);
        if (!note.equals("")) edtNote.setText(note);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        view.findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = edtNote.getText().toString().trim();
                if (!n.equals("")) {
                    presenterStore.updateNoteDetailOrder(idOrder, idProduct, n);
                }
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }
}
