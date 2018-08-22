package com.giiso.submmited.ui.presenter;

import android.text.TextUtils;

import com.giiso.submmited.http.ResultResponse;
import com.giiso.submmited.http.presenter.BaseObserver;
import com.giiso.submmited.http.presenter.BasePresenter;
import com.giiso.submmited.utils.ToastUtil;

/**
 * Created by Administrator on 2018/8/20.
 */

public class SubmmitedPresenter extends BasePresenter<SubmmitedView> {
    public SubmmitedPresenter(SubmmitedView view) {
        super(view);
    }

    //添加日报
    public void addSubmmited(int id, String realFinishTime, String taskTime, String status, String percentComplete, String description){
        mView.showLoading();
        addSubscribe(apiServer.addSubmmited(id, realFinishTime, taskTime, status, percentComplete, description), new BaseObserver<ResultResponse>() {
            @Override
            public void onSuccess(ResultResponse response) {
                mView.closeLoading();
                if(response.isSuccess()){
                    mView.addSubmmitedSuccess();
                } else {
                    if(!TextUtils.isEmpty(response.getMsg())){
                        ToastUtil.showToast(response.getMsg());
                    }
                }
            }

            @Override
            public void onError(int code, String msg) {
                mView.closeLoading();
            }
        });
    }

    //修改日报
    public void updateSubmmited(int id, String percentComplete, String taskTime, String description, String realFinishTime){
        mView.showLoading();
        addSubscribe(apiServer.updateSubmmited(id, percentComplete, taskTime, description, realFinishTime), new BaseObserver<ResultResponse>() {
            @Override
            public void onSuccess(ResultResponse response) {
                mView.closeLoading();
                if(response.isSuccess()){
                    mView.updateSuccess();
                }
            }

            @Override
            public void onError(int code, String msg) {
                mView.closeLoading();
            }
        });
    }
}
