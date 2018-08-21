package com.giiso.submmited.bean.log;

/**
 * Created by Administrator on 2018/7/17.
 */

public class ErrorLog {
    long id;
    long time;
    String meaagse;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMeaagse() {
        return meaagse;
    }

    public void setMeaagse(String meaagse) {
        this.meaagse = meaagse;
    }
}
