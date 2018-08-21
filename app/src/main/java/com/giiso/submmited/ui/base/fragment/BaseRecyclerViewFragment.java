package com.giiso.submmited.ui.base.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.giiso.submmited.R;
import com.giiso.submmited.base.Constants;
import com.giiso.submmited.bean.base.PageBean;
import com.giiso.submmited.bean.base.ResultBean;
import com.giiso.submmited.cache.CacheManager;
import com.giiso.submmited.http.ResultResponse;
import com.giiso.submmited.http.presenter.BaseObserver;
import com.giiso.submmited.ui.base.adapter.BaseGeneralRecyclerAdapter;
import com.giiso.submmited.ui.base.adapter.BaseRecyclerAdapter;
import com.giiso.submmited.ui.widget.EmptyLayout;
import com.giiso.submmited.ui.widget.swipetoloadlayout.OnLoadMoreListener;
import com.giiso.submmited.ui.widget.swipetoloadlayout.OnRefreshListener;
import com.giiso.submmited.ui.widget.swipetoloadlayout.SwipeToLoadLayout;
import com.giiso.submmited.utils.Log;
import com.giiso.submmited.utils.NetworkUtils;
import com.giiso.submmited.utils.TDevice;
import com.giiso.submmited.utils.ToastUtil;
import com.giiso.submmited.utils.gson.AppOperator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * 基本列表类，重写getLayoutId()自定义界面
 * Created by lyb on 2017-08-22.
 */
public abstract class BaseRecyclerViewFragment<T> extends BaseFragment implements
        BaseRecyclerAdapter.OnItemClickListener,
        View.OnClickListener,
        BaseGeneralRecyclerAdapter.Callback, OnLoadMoreListener, OnRefreshListener {

    private final String TAG = this.getClass().getSimpleName();
    protected BaseRecyclerAdapter<T> mAdapter;
    protected RecyclerView mRecyclerView;
    protected SwipeToLoadLayout swipeToLoadLayout;
    protected boolean isRefreshing;
    protected BaseObserver<ResultResponse> mCallback;
    protected PageBean<T> mBean;
    protected String CACHE_NAME = getClass().getName();
    protected String TOPIC_CACHE = Constants.TOPIC_CACHE;
    protected EmptyLayout mErrorLayout;
    protected boolean isFirst = true;
    private ScrollStateChangedListener scrollStateChangedListener;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_recycler_view;
    }

    @Override
    protected void initWidget(View root) {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.swipe_target);
        swipeToLoadLayout = (SwipeToLoadLayout) root.findViewById(R.id.swipeToLoadLayout);
        mErrorLayout = (EmptyLayout) root.findViewById(R.id.error_layout);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initData() {
        mAdapter = getRecyclerAdapter();
        mRecyclerView.setLayoutManager(getLayoutManager());
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, false);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mErrorLayout.setOnLayoutClickListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (scrollStateChangedListener != null) {
                    if (newState == SCROLL_STATE_IDLE) {
                        scrollStateChangedListener.onIdle();
                    } else {
                        scrollStateChangedListener.onScroll(newState);
                    }
                }
                if (RecyclerView.SCROLL_STATE_DRAGGING == newState && getActivity() != null
                        && getActivity().getCurrentFocus() != null) {
                    TDevice.hideSoftKeyboard(getActivity().getCurrentFocus());
                }
            }
        });
        mCallback = new BaseObserver<ResultResponse>(){

            @Override
            public void onSuccess(ResultResponse response) {
                Map map = (Map) response.getData();
                String json = AppOperator.getGson().toJson(map);
                PageBean<T> items = AppOperator.getGson().fromJson(json, getType());
                if (response.isSuccess() && items != null) {
                    setListDataBefore(items.getItems());
                    setListData(items.getItems());
                    onRequestSuccess();
                } else {
                    onRequestSuccessError(items.getItems(), response.getMsg());
                }
                onRequestFinish();
            }

            @Override
            public void onError(int code, String msg) {
                log("HttpResponseHandler:onFailure responseString:" + msg);
                onRequestFailure();
                onRequestFinish();

            }
        };
        boolean isNeedEmptyView = isNeedEmptyView();
        if (isNeedEmptyView) {
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
            swipeToLoadLayout.setVisibility(View.GONE);
        } else {
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        }
        mBean = new PageBean<>();
        List<T> items = isNeedCache()
                ? (List<T>) CacheManager.readListJson(getActivity(), CACHE_NAME, getCacheClass())
                : null;
        mBean.setItems(items);
        //if is the first loading
        if (items == null || items.size() <= 0) {
            mBean.setItems(new ArrayList<T>());
            if (!isNeedEmptyView) {
                swipeToLoadLayout.setVisibility(View.VISIBLE);
            }
            onRefresh();
        } else {
            isNeedBindSearch();
            mAdapter.addAll(mBean.getItems());
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            swipeToLoadLayout.setVisibility(View.VISIBLE);
            if (isNeedRefresh()) {
                mRoot.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoadLayout.setRefreshing(true);
                    }
                });
            }
        }
    }

    protected void isNeedBindSearch() {

    }


    @Override
    public void onClick(View v) {
        mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        onRefresh();
    }

    @Override
    public void onItemClick(int position, long itemId) {

    }

    public void onRequestSuccessError(List<T> resultBean, String msg) {
        if (mAdapter.getCount() <= 0) {
            log(resultBean != null ? msg : "");
            mAdapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        }
    }

    public void onRequestSuccessValidateError(List<T> resultBean, String msg) {
        if (mAdapter.getCount() <= 0) {
            log(resultBean != null ? msg : "");
            mAdapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        }
    }

    @Override
    public void onRefresh() {
        isRefreshing = true;
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, true);
        requestData();
    }

    @Override
    public void onLoadMore() {
        mAdapter.setState(isRefreshing ? BaseRecyclerAdapter.STATE_HIDE : BaseRecyclerAdapter.STATE_LOADING, true);
        isRefreshing = false;
        requestData();
    }

    protected void requestData() {
    }

    protected void setListDataBefore(List<T> lists) {
    }

    protected void onRequestSuccess() {
        if (mAdapter.getItems().size() > 0) {
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            swipeToLoadLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mErrorLayout.setErrorType(
                    isNeedEmptyView()
                            ? EmptyLayout.NODATA
                            : EmptyLayout.HIDE_LAYOUT);
        }
    }

    protected void onRequestFinish() {
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
        onComplete();
    }

    protected void onRequestError() {
        if (mAdapter.getItems().size() <= 0) {
            if (isNeedEmptyView()) mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        }
        if (!NetworkUtils.isConnected()) {
            mAdapter.setState(BaseRecyclerAdapter.STATE_INVALID_NETWORK, true);
        } else {
            mAdapter.setState(BaseRecyclerAdapter.STATE_LOAD_ERROR, true);
        }
    }

    protected void onRequestFailure() {
        if (mAdapter.getItems().size() <= 0) {
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_NO);
        }
        if (!NetworkUtils.isConnected()) {
            mAdapter.setState(BaseRecyclerAdapter.STATE_INVALID_NETWORK, true);
        } else {
            mAdapter.setState(BaseRecyclerAdapter.STATE_LOAD_ERROR, true);
        }
    }

    protected void onComplete() {
//        mRefreshLayout.onComplete();
        isRefreshing = false;
    }

    protected void setListData(List<T> resultBean) {
        if (isRefreshing && resultBean != null) {
            if (getRefreshSetDataMode() == REFRESH_SETDATA_MODE_CLEAR) {
                mBean.setItems(resultBean);
                mAdapter.clear();
                mAdapter.addAll(mBean.getItems());
                isFirst = false;
            } else {
                if (isFirst) {
                    mBean.getItems().clear();
                    isFirst = false;
                    mAdapter.clear();
                }
                mBean.getItems().addAll(0, resultBean);
                mAdapter.addToStart(mBean.getItems());
            }

            if (isNeedCache()) {
                addBannerCache(resultBean);
            }
        } else {
            if (isFirst) {//第一次进入清楚数据
                mAdapter.clear();
                isFirst = false;
            }
            mAdapter.addAll(resultBean);
        }
        if (resultBean == null
                || resultBean.size() < Constants.PAGE_SIZE) {
            mAdapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
            swipeToLoadLayout.setNoMore(true);
        } else {
            swipeToLoadLayout.setNoMore(false);
        }
//        mAdapter.setState(resultBean.getResult().getItems() == null
//                || resultBean.getResult().getItems().size() < 20
//                ? BaseRecyclerAdapter.STATE_NO_MORE
//                : BaseRecyclerAdapter.STATE_LOADING, true);
    }

    protected void addBannerCache(List<T> data) {
        CacheManager.saveToJson(getActivity(), CACHE_NAME, data);
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    protected abstract BaseRecyclerAdapter<T> getRecyclerAdapter();

    protected abstract Type getType();

    /**
     * 获取缓存bean的class
     */
    protected Class<T> getCacheClass() {
        return null;
    }

    @Override
    public Date getSystemTime() {
        return new Date();
    }

    /**
     * 需要缓存
     *
     * @return isNeedCache
     */
    protected boolean isNeedCache() {
        return true;
    }

    /**
     * 下拉刷新成功需要提示
     *
     * @return isNeedCache
     */
    protected boolean isNeedSuccessTip() {
        return false;
    }

    /**
     * 需要空的View
     *
     * @return isNeedEmptyView
     */
    protected boolean isNeedEmptyView() {
        return true;
    }

    /**
     * 是否每次进创建页面进入刷新
     */
    protected boolean isNeedRefresh() {
        return true;
    }


    //列表下拉操作时对缓存数据的处理方式
    protected final static int REFRESH_SETDATA_MODE_CLEAR = 0;//下拉返回新数据后会清除当前列表缓存数据
    protected final static int REFRESH_SETDATA_MODE_ADD = 1;//下拉返回新数据后累加在当前列表的顶部

    protected int getRefreshSetDataMode() {
        return REFRESH_SETDATA_MODE_CLEAR;
    }

    @SuppressWarnings("ConstantConditions")
    private void log(String msg) {
        if (false)
            Log.i(TAG, msg);
    }

    public void loading() {
//        mAdapter.clear();
        mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
    }

    public SwipeToLoadLayout getSwipeToLoadLayout() {
        return swipeToLoadLayout;
    }

    public EmptyLayout getmErrorLayout() {
        return mErrorLayout;
    }

    /**
     * 添加滑动监听器
     *
     * @param listener add the listener for SuperRefreshLayout
     */
    public void setOnScrollStateChangedListener(ScrollStateChangedListener listener) {
        this.scrollStateChangedListener = listener;
    }

    public ScrollStateChangedListener getScrollStateChangedListener() {
        return scrollStateChangedListener;
    }

    public interface ScrollStateChangedListener {
        void onIdle();

        void onScroll(int newState);

    }
}
