package com.giiso.submmited.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lrz on 2018/8/14.
 */

public class Menus implements Serializable{

    private int id;
    private String name;
    private String href;
    private int sort;
    private String isShow;
    private List<MenuItem> childMenus;

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

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public List<MenuItem> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<MenuItem> childMenus) {
        this.childMenus = childMenus;
    }

    public static class MenuItem implements Serializable{
        private int id;
        private String name;
        private String href;
        private int sort;
        private String isShow;


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

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getIsShow() {
            return isShow;
        }

        public void setIsShow(String isShow) {
            this.isShow = isShow;
        }
    }
}
