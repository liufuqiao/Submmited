package com.giiso.submmited.http.presenter;

import com.giiso.submmited.http.ResultResponse;
import com.giiso.submmited.http.retrofit.RetrofitServiceManager;
import com.giiso.submmited.http.retrofit.SchedulersCompat;
import com.giiso.submmited.ui.api.SubmmitedApi;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LiuRiZhao on 2017/11/30.
 */

public class BasePresenter<T extends BaseView>
{
    protected T mView;
    //使用了CompositeSubscription来进行避免内存的泄漏
    private CompositeSubscription mCompositeSubscription;

    protected SubmmitedApi apiServer = RetrofitServiceManager.getInstance().create(SubmmitedApi.class);

    public BasePresenter(T view) {
        this.mView = view;
    }

    private void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscribe(Observable<ResultResponse> observable, BaseObserver observer) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        Subscription subscription = observable.compose(SchedulersCompat.<ResultResponse>applyIoSchedulers()).subscribe(observer);
        mCompositeSubscription.add(subscription);
    }

    public void detachView()
    {
        this.mView = null;
        unSubscribe();
    }

}
