package com.giiso.submmited.ui.fragment.presenter;

import com.giiso.submmited.http.ResultResponse;
import com.giiso.submmited.http.presenter.BaseObserver;
import com.giiso.submmited.http.presenter.BasePresenter;

/**
 * Created by Administrator on 2018/8/24.
 */

public class ExceptionPresenter extends BasePresenter<ExceptionView> {

    public ExceptionPresenter(ExceptionView view) {
        super(view);
    }

    /**
     * 获取日报异常列表
     * @param pageNum
     * @param pageSize
     * @param observer
     */
    public void getExceptionList(int pageNum, int pageSize, BaseObserver<ResultResponse> observer){
        addSubscribe(apiServer.getUnususalList(pageNum, pageSize), observer);
    }
}
