package com.giiso.submmited.ui.fragment.presenter;

import com.giiso.submmited.http.HttpContext;
import com.giiso.submmited.http.ResultResponse;
import com.giiso.submmited.http.presenter.BaseObserver;
import com.giiso.submmited.http.presenter.BasePresenter;

import retrofit2.http.Field;

/**
 * Created by Administrator on 2018/8/21.
 */

public class SubmmitedPresenter extends BasePresenter<SubmmitedView> {

    public SubmmitedPresenter(SubmmitedView view) {
        super(view);
    }

    public void getSubmmitedList(String startTime, String endTime, String name, String projectId, int pageNum, int pageSize, BaseObserver<ResultResponse> observer){
        addDisposable(apiServer.getSubmmitedList(startTime, endTime, name, projectId, pageNum, pageSize), observer);
    }
}
