package com.giiso.submmited.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giiso.submmited.R;
import com.giiso.submmited.ui.base.fragment.BaseFragment;

/**
 * 成员管理
 * Created by LiuRiZhao on 2018/8/9.
 */

public class MemberFragment extends BaseFragment{

    public static MemberFragment newInstance() {
        return new MemberFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


}
