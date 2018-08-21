package com.giiso.submmited.ui.presenter;

import com.giiso.submmited.bean.EmployeeBean;
import com.giiso.submmited.bean.ProjectBean;
import com.giiso.submmited.http.HttpContext;
import com.giiso.submmited.http.ResultResponse;
import com.giiso.submmited.http.presenter.BaseObserver;
import com.giiso.submmited.http.presenter.BasePresenter;
import com.giiso.submmited.utils.gson.AppOperator;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.http.Field;

/**
 * Created by LiuRiZhao on 2018/8/17.
 */

public class TaskPresenter extends BasePresenter<TaskView> {

    public TaskPresenter(TaskView view) {
        super(view);
    }

    //获取用户个人项目列表
    public void getSingleProjectList() {
        mView.showLoading();
        addDisposable(apiServer.getSingleProjectList(), new BaseObserver<ResultResponse<ArrayList<ProjectBean>>>() {
            @Override
            public void onSuccess(ResultResponse<ArrayList<ProjectBean>> response) {
                mView.closeLoading();
                if (response.isSuccess()) {
                    Type type = new TypeToken<ArrayList<ProjectBean>>() {
                    }.getType();
                    String json = AppOperator.getGson().toJson(response.getData());
                    ArrayList<ProjectBean> projectBeans = AppOperator.getGson().fromJson(json, type);
                    mView.resultProject(projectBeans);
                }
            }

            @Override
            public void onError(int code, String msg) {
                mView.closeLoading();
            }

        });
    }

    /**
     * 获取指派人列表
     * @param projectId
     */
    public void getEmployeeList(int projectId) {
        addDisposable(apiServer.getEmployeeList(projectId), new BaseObserver<ResultResponse<ArrayList<EmployeeBean>>>() {
            @Override
            public void onSuccess(ResultResponse<ArrayList<EmployeeBean>> response) {
                if (response.isSuccess()) {
                    Type type = new TypeToken<ArrayList<EmployeeBean>>() {
                    }.getType();
                    String json = AppOperator.getGson().toJson(response.getData());
                    ArrayList<EmployeeBean> employeeBeans = AppOperator.getGson().fromJson(json, type);
                    mView.resultEmployee(employeeBeans);
                }
            }

            @Override
            public void onError(int code, String msg) {
                mView.closeLoading();
            }
        });
    }

    /**
     * 添加任务
     */
    public void addTask(String expectStartTime, String expectFinishTime, int projectId, String name, String type, int userId, String taskTime, String percentComplete) {
        mView.showLoading();
        addDisposable(apiServer.addTask(expectStartTime, expectFinishTime, projectId, name,
                type, userId, taskTime, percentComplete), new BaseObserver<ResultResponse>() {
            @Override
            public void onSuccess(ResultResponse response) {
                mView.closeLoading();
                if(response.isSuccess()){
                    mView.addTaskSuccess();
                }
            }

            @Override
            public void onError(int code, String msg) {
                mView.closeLoading();
            }
        });
    }

    /**
     * 修改任务
     */
    public void updateTask( int id, String name, String type, String expectStartTime, String expectFinishTime, int projectId, String percentComplete) {
        mView.showLoading();
        addDisposable(apiServer.updateTask(id, name, type, expectStartTime, expectFinishTime, projectId, percentComplete), new BaseObserver<ResultResponse>() {
            @Override
            public void onSuccess(ResultResponse response) {
                mView.closeLoading();
                if(response.isSuccess()){

                }
            }

            @Override
            public void onError(int code, String msg) {
                mView.closeLoading();
            }
        });
    }

    //暂停任务
    public void stop(){

    }

    //启动任务
    public void start(){

    }

    //确认任务
    public void confirm(){

    }
}
