package com.giiso.submmited.cache;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/3/16.
 */

public class RefreshTypeCache {
    HashMap<String, Boolean> map = new HashMap<>();
    static RefreshTypeCache refreshTypeCache;

    public static RefreshTypeCache getInstance() {
        if (refreshTypeCache == null)
            refreshTypeCache = new RefreshTypeCache();
        return refreshTypeCache;
    }

    public boolean getRefreshType(String name) {
        if (map.get(name) == null)
            return true;
        return map.get(name);
    }

    public void setRefreshType(String name, boolean type) {
        map.put(name, type);
    }

    public void clear() {
        map.clear();
    }
}
