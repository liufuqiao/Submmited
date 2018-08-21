package com.giiso.submmited.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/15.
 */

public class Submmited implements Serializable {
    /**
     * expectStartTime : 2018-08-09
     * description : null
     * percentComplete : 40
     * type : 1
     * userName : 刘日朝
     * userId : 36
     * comment_status : 1
     * endStatus : 0
     * taskTimeCount : 32.0
     * bgStatus : 1
     * expectFinishTime : 2018-08-17
     * createId : 36
     * name : APP报工系统
     * percentTime : null
     * delStatus : 0
     * comment : null
     * id : 476
     * projectName : 报工系统
     * projectId : 18
     * isDel : 0
     * createName : 刘日朝
     * status : 0
     * taskTime : 56
     */

    private String expectStartTime;
    private String description;
    private String percentComplete;
    private String type;
    private String userName;
    private int userId;
    private String comment_status;
    private int endStatus;
    private String taskTimeCount;
    private int bgStatus;
    private String expectFinishTime;
    private int createId;
    private String name;
    private String percentTime;
    private int delStatus;
    private String comment;
    private int id;
    private String projectName;
    private int projectId;
    private String isDel;
    private String createName;
    private int status;
    private String taskTime;
    private String realFinishTime;

    public String getExpectStartTime() {
        return expectStartTime;
    }

    public void setExpectStartTime(String expectStartTime) {
        this.expectStartTime = expectStartTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPercentComplete() {
        return percentComplete;
    }

    public void setPercentComplete(String percentComplete) {
        this.percentComplete = percentComplete;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment_status() {
        String str = null;
        if(!TextUtils.isEmpty(comment_status)){
            if(comment_status.equals("1")){
                str = "计划";
            } else {
                str = "临时任务";
            }
        }
        return str;
    }

    public void setComment_status(String comment_status) {
        this.comment_status = comment_status;
    }

    public int getEndStatus() {
        return endStatus;
    }

    public void setEndStatus(int endStatus) {
        this.endStatus = endStatus;
    }

    public String getTaskTimeCount() {
        return taskTimeCount;
    }

    public void setTaskTimeCount(String taskTimeCount) {
        this.taskTimeCount = taskTimeCount;
    }

    public int getBgStatus() {
        return bgStatus;
    }

    public void setBgStatus(int bgStatus) {
        this.bgStatus = bgStatus;
    }

    public String getExpectFinishTime() {
        return expectFinishTime;
    }

    public void setExpectFinishTime(String expectFinishTime) {
        this.expectFinishTime = expectFinishTime;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPercentTime() {
        return percentTime;
    }

    public void setPercentTime(String percentTime) {
        this.percentTime = percentTime;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getRealFinishTime() {
        return realFinishTime;
    }

    public void setRealFinishTime(String realFinishTime) {
        this.realFinishTime = realFinishTime;
    }

    public static final int IN_PROGRESS = 0;
    public static final int COMPLETION = 1;
    public static final int POSTPONE = 2;
    public static final int STOP = 3;
    public static final int NO_START = 4;
    public static final int CONFIRMED = 5;

    public String getTaskStatus() {
        String statusString = null;
        switch (status) {
            case IN_PROGRESS://进行中
                statusString = "进行中";
                break;
            case COMPLETION://已完成
                statusString = "已完成";
                break;
            case POSTPONE://延期
                statusString = "延期";
                break;
            case STOP://暂停
                statusString = "暂停";
                break;
            case NO_START://未启动
                statusString = "未启动";
                break;
            case CONFIRMED://已确认
                statusString = "已确认";
                break;
        }
        return statusString;
    }

}
