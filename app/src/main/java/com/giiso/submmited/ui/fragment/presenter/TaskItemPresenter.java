package com.giiso.submmited.ui.fragment.presenter;

import com.giiso.submmited.http.ResultResponse;
import com.giiso.submmited.http.presenter.BaseObserver;
import com.giiso.submmited.http.presenter.BasePresenter;

/**
 * Created by Administrator on 2018/8/20.
 */

public class TaskItemPresenter extends BasePresenter<TaskItemView> {

    public TaskItemPresenter(TaskItemView view) {
        super(view);
    }

    public void getTaskList(String type, String typeStatus, String userName, String createId, String startTime, String endTime,String projectId, int pageNum, int pageSize, BaseObserver<ResultResponse> observer){
        addSubscribe(apiServer.getAllList(type, typeStatus, userName, createId, startTime, endTime, projectId, pageNum, pageSize), observer);
    }


    //启动暂停任务处理 status 0 启动 3 暂停
    public void startOrStopTask(int id, String status){
        mView.showLoading();
        addSubscribe(apiServer.startOrStopTask(id, status), new BaseObserver<ResultResponse>() {
            @Override
            public void onSuccess(ResultResponse response) {
                mView.closeLoading();
                if(response.isSuccess()){
                    mView.onSuccess();
                }
            }

            @Override
            public void onError(int code, String msg) {
                mView.closeLoading();
            }
        });
    }

    public void confirm(int id){
        mView.showLoading();
        addSubscribe(apiServer.confirmTask(id), new BaseObserver<ResultResponse>() {
            @Override
            public void onSuccess(ResultResponse response) {
                mView.closeLoading();
                if(response.isSuccess()){
                    mView.onSuccess();
                }
            }

            @Override
            public void onError(int code, String msg) {
                mView.closeLoading();
            }
        });
    }
}
