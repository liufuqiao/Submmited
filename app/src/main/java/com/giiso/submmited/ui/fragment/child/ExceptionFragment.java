package com.giiso.submmited.ui.fragment.child;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.giiso.submmited.base.Constants;
import com.giiso.submmited.bean.Submmited;
import com.giiso.submmited.bean.base.PageBean;
import com.giiso.submmited.http.presenter.BasePresenter;
import com.giiso.submmited.ui.base.adapter.BaseRecyclerAdapter;
import com.giiso.submmited.ui.base.fragment.BaseGeneralRecyclerFragment;
import com.giiso.submmited.ui.fragment.adapter.ExceptionAdapter;
import com.giiso.submmited.ui.fragment.presenter.ExceptionPresenter;
import com.giiso.submmited.ui.fragment.presenter.ExceptionView;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2018/8/21.
 */

public class ExceptionFragment extends BaseGeneralRecyclerFragment<Submmited> implements ExceptionView{


    private int pageNum = 0;
    private ExceptionPresenter mPresenter;
    public static ExceptionFragment newInstance() {
        return new ExceptionFragment();
    }

    @Override
    protected BaseRecyclerAdapter<Submmited> getRecyclerAdapter() {
        int mode = BaseRecyclerAdapter.NEITHER;
        return new ExceptionAdapter(mContext, mode);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return super.getLayoutManager();
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected Type getType() {
        return new TypeToken<PageBean<Submmited>>(){}.getType();
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mPresenter = new ExceptionPresenter(this);
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    protected void requestData() {
        super.requestData();
        if(isRefreshing){
            pageNum = 1;
            mPresenter.getExceptionList(pageNum, Constants.PAGE_SIZE, mCallback);
        } else {
            pageNum ++;
            mPresenter.getExceptionList(pageNum, Constants.PAGE_SIZE, mCallback);
        }
    }
}
