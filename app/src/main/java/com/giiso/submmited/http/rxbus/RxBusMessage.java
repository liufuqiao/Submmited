package com.giiso.submmited.http.rxbus;

/**
 * Created by Administrator on 2018/8/16.
 */

public class RxBusMessage {
    public static final String MENU = "menu";
    public static final String TASK_LIST_REFRESH = "task_list_refresh";
    public static final String SUBMMITED_REFRESH = "submmited_refresh";

    private String message;
    private int type;

    public RxBusMessage() {
    }

    public RxBusMessage(int type, String message) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
