package com.example.hieuhoang.now.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.hieuhoang.now.Common.Common;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Presenter.Main.Bill.DraftOrder.IPresenterDraftOrder;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Store.StoreActivity;

import java.util.List;

public class srvDraftOrderBillAdapter extends RecyclerSwipeAdapter<srvDraftOrderBillAdapter.DraftOrderViewHolder> {


    private String TAG = "kiemtra" ;
    private List<Order> mOrders;
    private Context context ;
    private IPresenterDraftOrder presenterLogicDraftOrder ;

    public srvDraftOrderBillAdapter(List<Order> mOrders, Context context , IPresenterDraftOrder presenterLogicDraftOrder) {
        this.mOrders = mOrders;
        this.context = context;
        this.presenterLogicDraftOrder = presenterLogicDraftOrder ;
    }

    @Override
    public DraftOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_swipe_draft_order_in_bill, parent, false);
        return new DraftOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DraftOrderViewHolder holder, int position) {
        final Order order = mOrders.get(position) ;

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wrapper));

        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });

        holder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iStore = new Intent(context, StoreActivity.class) ;
                iStore.putExtra(AppConstant.ID_STORE,order.getIdStore()) ;
                context.startActivity(iStore);
            }
        });

        Common.loadImageFromServer(order.getStoreImage() , context , holder.imgStore);
        holder.tvStoreName.setText(order.getStoreName());
        holder.tvStoreAddress.setText(order.getStoreAddress());
        holder.tvQuantity.setText(String.valueOf(order.getQuantityProduct()));
        holder.tvTotalMoney.setText(Common.formatNumber(order.getTotalMoney()));

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(order.getIdOrder());
            }
        });

        mItemManger.bindView(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeDraftOrder;
    }

    class DraftOrderViewHolder extends RecyclerView.ViewHolder {
        private SwipeLayout swipeLayout;
        private ImageView imgStore;
        private TextView tvStoreName,tvStoreAddress,tvQuantity,tvTotalMoney ,tvDelete;
       private DraftOrderViewHolder(View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.swipeDraftOrder);
            imgStore = itemView.findViewById(R.id.imgStore);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvStoreAddress = itemView.findViewById(R.id.tvStoreAddress);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotalMoney = itemView.findViewById(R.id.tvTotalMoney);
            tvDelete = itemView.findViewById(R.id.tvDelete);
        }
    }

    public void setData(List<Order> mOrders) {
        this.mOrders = mOrders ;
    }

    private void showDialog (final String idOrder) {
        final Dialog dialog = new Dialog(context) ;
        View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog,null) ;
        TextView tvContentDialog = view.findViewById(R.id.tvContentDialog) ;
        Button btnYes = view.findViewById(R.id.btnYes) ;
        Button btnCancel = view.findViewById(R.id.btnCancel) ;
        tvContentDialog.setText(context.getResources().getString(R.string.do_you_want_delete_this_draft_order));
        btnYes.setText(context.getResources().getString(R.string.delete));
        btnCancel.setText(context.getResources().getString(R.string.cancel));
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                presenterLogicDraftOrder.deleteDraftOrder(idOrder);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) ;
        dialog.setContentView(view);
        dialog.show();

    }
}
