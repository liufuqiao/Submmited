package com.giiso.submmited.http.presenter;

import com.giiso.submmited.http.retrofit.RetrofitServiceManager;
import com.giiso.submmited.http.retrofit.SchedulersCompat;
import com.giiso.submmited.ui.api.SubmmitedApi;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by LiuRiZhao on 2017/11/30.
 */

public class BasePresenter<T extends BaseView>
{
    protected T mView;
    //使用了CompositeDisposable来进行避免内存的泄漏
    private CompositeDisposable mCompositeDisposable;

    protected SubmmitedApi apiServer = RetrofitServiceManager.getInstance().create(SubmmitedApi.class);

    public BasePresenter(T view) {
        this.mView = view;
    }

    private void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    protected void addDisposable(Observable<?> observable, BaseObserver observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(observable.wrap(observable).compose(SchedulersCompat.toMain()).subscribeWith(observer));
    }

    public void detachView()
    {
        this.mView = null;
        unSubscribe();
    }

}
