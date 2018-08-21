package com.giiso.submmited.ui.base.fragment;

import com.giiso.submmited.http.presenter.BaseObserver;
import com.giiso.submmited.interf.OnTabReselectListener;

/**
 * Created by lyb on 2017-08-22
 */
public abstract class BaseGeneralRecyclerFragment<T> extends BaseRecyclerViewFragment<T> implements OnTabReselectListener {


    @Override
    public void onTabReselect() {
        if (mRecyclerView != null && !isRefreshing) {
            mRecyclerView.scrollToPosition(0);
            swipeToLoadLayout.setRefreshing(true);
            onRefresh();
        }
    }
}
