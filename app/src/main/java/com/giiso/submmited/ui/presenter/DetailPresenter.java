package com.giiso.submmited.ui.presenter;

import com.giiso.submmited.bean.Submmited;
import com.giiso.submmited.http.ResultResponse;
import com.giiso.submmited.http.presenter.BaseObserver;
import com.giiso.submmited.http.presenter.BasePresenter;
import com.giiso.submmited.utils.gson.AppOperator;

/**
 * Created by Administrator on 2018/8/22.
 */

public class DetailPresenter extends BasePresenter<DetailView> {

    public DetailPresenter(DetailView view) {
        super(view);
    }

    public void getTaskDetail(int id){
        mView.showLoading();
        addSubscribe(apiServer.getTaskDetail(id), new BaseObserver<ResultResponse>() {
            @Override
            public void onSuccess(ResultResponse response) {
                mView.closeLoading();
                if(response.isSuccess()){
                    Submmited submmited = AppOperator.getGson().fromJson(AppOperator.getGson().toJson(response.getData()), Submmited.class);
                    mView.showDetail(submmited);
                }
            }

            @Override
            public void onError(int code, String msg) {
                mView.closeLoading();
            }
        });
    }
}
