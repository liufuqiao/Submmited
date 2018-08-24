package com.giiso.submmited.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.giiso.submmited.R;
import com.giiso.submmited.base.BaseApplication;
import com.giiso.submmited.base.Constants;
import com.giiso.submmited.ui.base.activity.BaseActivity;
import com.giiso.submmited.ui.presenter.LoginPresenter;
import com.giiso.submmited.ui.presenter.LoginView;
import com.giiso.submmited.utils.ActivityUtil;
import com.giiso.submmited.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A anonymousLogins screen that offers anonymousLogins via email/password.
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {

    @BindView(R.id.login_progress)
    ProgressBar mProgressView;
    @BindView(R.id.username)
    EditText mUserNameView;
    @BindView(R.id.password)
    EditText mPasswordView;
    @BindView(R.id.login_form)
    ScrollView mLoginFormView;

    public static void show(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.giiso_login;
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        String userName = BaseApplication.get(Constants.USERNAME, null);
        String password = BaseApplication.get(Constants.PASSWORD, null);
        mUserNameView.setText(userName);
        if(!TextUtils.isEmpty(userName)){
            mUserNameView.setSelection(userName.length());
        }
        mPasswordView.setText(password);
    }

    @OnClick(R.id.sign_in_button)
    public void onClick(View view){
        attemptLogin();
    }

    /**
     * Attempts to sign in or register the account specified by the anonymousLogins form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual anonymousLogins attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mUserNameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the anonymousLogins attempt.
        String userName = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            //mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(userName)) {
            // mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            //mUserNameView.setError(getString(R.string.error_invalid_email));
            focusView = mUserNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt anonymousLogins and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a icon_background task to
            // perform the user anonymousLogins attempt.
            mPresenter.login(userName, password);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public void loginSuccess() {
        //RxBus.getInstance().send(RxBusMessage.MENU);
        MainActivity.show(LoginActivity.this);
    }

    @Override
    public void loginFailure() {
        ToastUtil.showToast("登录失败，请重试");
    }
    ProgressDialog progressDialog;
    @Override
    public void showLoading() {
        super.showLoading();
        showDialog();
    }

    @Override
    public void closeLoading() {
        super.closeLoading();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtil.getInstance().AppExit(this);
    }
}

