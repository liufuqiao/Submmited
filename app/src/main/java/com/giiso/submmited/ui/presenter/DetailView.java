package com.giiso.submmited.ui.presenter;

import com.giiso.submmited.bean.Submmited;
import com.giiso.submmited.http.presenter.BaseView;

/**
 * Created by Administrator on 2018/8/22.
 */

public interface DetailView extends BaseView {
    void showDetail(Submmited submmited);
}
