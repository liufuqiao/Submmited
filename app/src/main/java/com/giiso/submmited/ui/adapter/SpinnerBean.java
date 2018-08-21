package com.giiso.submmited.ui.adapter;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/17.
 */

public class SpinnerBean implements Serializable{
    public SpinnerBean(int id, String value) {
        this.id = id;
        this.value = value;
    }

    private int id;
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
