package com.giiso.submmited.ui.presenter;

import com.giiso.submmited.http.presenter.BaseView;

/**
 * Created by jayce on 2018/8/2.
 */

public interface LoginView extends BaseView {
    void loginSuccess();
    void loginFailure();
}
