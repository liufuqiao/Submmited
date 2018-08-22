package com.giiso.submmited.ui.fragment.presenter;

import com.giiso.submmited.http.ResultResponse;
import com.giiso.submmited.http.presenter.BaseObserver;
import com.giiso.submmited.http.presenter.BasePresenter;

/**
 * Created by Administrator on 2018/8/21.
 */

public class SubmmitedPresenter extends BasePresenter<SubmmitedView> {

    public SubmmitedPresenter(SubmmitedView view) {
        super(view);
    }

    public void getSubmmitedList(String startTime, String endTime, String name, String projectId, int pageNum, int pageSize, BaseObserver<ResultResponse> observer){
        addSubscribe(apiServer.getSubmmitedList(startTime, endTime, name, projectId, pageNum, pageSize), observer);
    }

    public void deleteSubmmited(int id){
        mView.showLoading();
        addSubscribe(apiServer.deleteTask(id), new BaseObserver<ResultResponse>() {
            @Override
            public void onSuccess(ResultResponse response) {
                mView.closeLoading();
                if (response.isSuccess()) {
                    mView.deleteSuccess();
                }
            }

            @Override
            public void onError(int code, String msg) {
                mView.closeLoading();
            }
        });
    }
}
