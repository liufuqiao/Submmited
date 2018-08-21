package com.giiso.submmited.ui.fragment.presenter;

import com.giiso.submmited.bean.Menus;
import com.giiso.submmited.bean.base.PageBean;
import com.giiso.submmited.http.ResultResponse;
import com.giiso.submmited.http.presenter.BaseObserver;
import com.giiso.submmited.http.presenter.BasePresenter;
import com.giiso.submmited.utils.gson.AppOperator;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/14.
 */

public class MenuPresenter<T> extends BasePresenter<MenuView>{

    public MenuPresenter(MenuView view) {
        super(view);
    }

    public void getMenuList(){
        mView.showLoading();
        addDisposable(apiServer.getMenuList(), new BaseObserver<ResultResponse>() {

            @Override
            public void onSuccess(ResultResponse response) {
                mView.closeLoading();
                if(response.isSuccess()){
                    Map  map = (Map) response.getData();
                    Object json =  map.get("menuList");
                    Type type = new TypeToken<List<Menus>>(){}.getType();
                    List<Menus> menus = AppOperator.getGson().fromJson(AppOperator.getGson().toJson(json), type);
                    mView.showResult(menus);
                }
            }

            @Override
            public void onError(int code, String msg) {
                mView.closeLoading();
            }
        });
    }
}
