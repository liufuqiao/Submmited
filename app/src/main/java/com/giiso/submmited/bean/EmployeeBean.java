package com.giiso.submmited.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/17.
 */

public class EmployeeBean implements Serializable{
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
