package com.giiso.submmited.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giiso.submmited.R;
import com.giiso.submmited.ui.base.fragment.BaseFragment;

/**
 * 项目管理
 * Created by LiuRiZhao on 2018/8/9.
 */

public class ItemsFragment extends BaseFragment {

    public static ItemsFragment newInstance() {
        return new ItemsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }
}
