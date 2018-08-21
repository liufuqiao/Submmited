package com.giiso.submmited.ui.base.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.giiso.submmited.R;

import butterknife.BindView;


/**
 * Created by xwt on 2017/9/20.
 */
public abstract class BaseTitleActivity<T> extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_toolbar_title)
    protected TextView mTvTitle;

    @Override
    protected void initData() {
        super.initData();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.base_toolbar_layout;
    }

    public void setTitle(String string) {
        mTvTitle.setText(string);
    }

    public void setTitle(int id) {
        mTvTitle.setText(id);
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
