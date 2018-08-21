package com.giiso.submmited.ui.fragment.presenter;

import com.giiso.submmited.bean.Menus;
import com.giiso.submmited.http.presenter.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2018/8/14.
 */

public interface MenuView extends BaseView {
    void showResult(List<Menus> menus);
}
