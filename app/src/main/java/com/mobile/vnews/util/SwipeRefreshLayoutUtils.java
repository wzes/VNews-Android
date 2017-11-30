package com.mobile.vnews.util;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;


/**
 * Created by xuantang on 11/30/17.
 */

public class SwipeRefreshLayoutUtils {

    //  加载进度的dialog
    private SwipeRefreshLayout mSwipeRefreshLayout;
    /**
     * 显示ProgressDialog
     */
    public void showProgress(@NonNull SwipeRefreshLayout swipeRefreshLayout) {
       swipeRefreshLayout.setRefreshing(true);
    }

    /**
     * 取消ProgressDialog
     */
    public void dismissProgress() {
        if(mSwipeRefreshLayout != null && !mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
