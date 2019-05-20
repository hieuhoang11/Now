package com.example.hieuhoang.now.Model.ObjectClass;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class LoadMoreScroll extends RecyclerView.OnScrollListener{
    private int sumItem = 0 ;
    private int firstItem = 0 ;
    private int itemLoad = 5 ;
    RecyclerView.LayoutManager layoutManager ;
    private ILoadMore iLoadMore ;
    public LoadMoreScroll (RecyclerView.LayoutManager layoutManager, ILoadMore iLoadMore) {
        this.layoutManager = layoutManager;
        this.iLoadMore = iLoadMore ;
    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        sumItem = layoutManager.getItemCount();
        firstItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition() ;
        Log.i("kiemtra", "onScrolled: " + sumItem + "  " + firstItem);
        if(sumItem <= firstItem + itemLoad) {
            Log.i("kiemtra", "onScrolled: ");
            iLoadMore.loadMore(sumItem);
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }
}
