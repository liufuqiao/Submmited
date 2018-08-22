package com.giiso.submmited.ui.presenter;

import com.giiso.submmited.base.BaseApplication;
import com.giiso.submmited.base.Constants;
import com.giiso.submmited.bean.User;
import com.giiso.submmited.http.ResultResponse;
import com.giiso.submmited.http.presenter.BaseObserver;
import com.giiso.submmited.http.presenter.BasePresenter;
import com.giiso.submmited.utils.ToastUtil;
import com.giiso.submmited.utils.gson.AppOperator;

/**
 * Created by lrz on 2018/7/25.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    public LoginPresenter(LoginView view) {
        super(view);
    }
    public void login(final String username, final String pwd) {
        mView.showLoading();
        addSubscribe(apiServer.logins(username, pwd), new BaseObserver<ResultResponse>(mView) {

            @Override
            public void onSuccess(ResultResponse o) {
                mView.closeLoading();
                String json = AppOperator.getGson().toJson(o.getData());
                User user = AppOperator.getGson().fromJson(json, User.class);
                if(o.isSuccess() && user.isIsLogin()){
                    BaseApplication.set(Constants.USERNAME, username);
                    BaseApplication.set(Constants.PASSWORD, pwd);
                    BaseApplication.set(Constants.USER_ID, user.getUser().getId());
                    ToastUtil.showToast("登录成功");
                    mView.loginSuccess();
                } else {
                    mView.loginFailure();
                }
            }

            @Override
            public void onError(int code, String msg) {
                mView.closeLoading();
                mView.loginFailure();
            }

        });
    }

    /**
     * 登出
     */
    public void login_out(){
        mView.showLoading();
        addSubscribe(apiServer.logins_out(), new BaseObserver<ResultResponse>(mView) {
                    @Override
                    public void onSuccess(ResultResponse response) {
                        mView.closeLoading();
                        if(response.isSuccess()){

                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        mView.closeLoading();
                    }
                });
    }
}
