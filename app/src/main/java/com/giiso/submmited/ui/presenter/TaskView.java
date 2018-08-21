package com.giiso.submmited.ui.presenter;

import com.giiso.submmited.bean.EmployeeBean;
import com.giiso.submmited.bean.ProjectBean;
import com.giiso.submmited.http.presenter.BaseView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/17.
 */

public interface TaskView extends BaseView {
    void resultProject(ArrayList<ProjectBean> list);
    void resultEmployee(ArrayList<EmployeeBean> list);
    void addTaskSuccess();
}
