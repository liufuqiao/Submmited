package com.giiso.submmited.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/17.
 */

public class ProjectBean implements Serializable {
    private String projectName;
    private int projectId;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
