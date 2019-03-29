package com.example.hieuhoang.now.Model.ObjectClass;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class LoadMoreScroll extends RecyclerView.OnScrollListener{
    private int sumItem = 0 ;
    private int firstItem = 0 ;
    private int itemLoad = 10 ;
    RecyclerView.LayoutManager layoutManager ;
    public LoadMoreScroll (RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        sumItem = layoutManager.getItemCount();
        firstItem = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition() ;
        if(sumItem <= firstItem + itemLoad) {

        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }
}
