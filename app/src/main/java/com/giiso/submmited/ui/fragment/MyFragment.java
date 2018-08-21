package com.giiso.submmited.ui.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giiso.submmited.R;
import com.giiso.submmited.base.BaseApplication;
import com.giiso.submmited.base.Constants;
import com.giiso.submmited.ui.LoginActivity;
import com.giiso.submmited.ui.base.fragment.BaseFragment;

import butterknife.OnClick;

/**
 * 个人中心
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends BaseFragment {;

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @OnClick(R.id.tv_login_out)
    public void onClick(View view){
//        BaseApplication.set(Constants.USERNAME, null);
//        BaseApplication.set(Constants.PASSWORD, null);
        LoginActivity.show(mContext);
        ((Activity)mContext).finish();
    }
}
